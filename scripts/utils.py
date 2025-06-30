"""
Utility functions for the task generation system.
Provides consistent error handling, API calls, and common operations.
"""

import time
import subprocess
import os
import sys
import base64
from typing import Any, Dict, List, Optional, Union
from openai import OpenAI
from config import Config, ModelSelector


class APIError(Exception):
    """Custom exception for API-related errors."""
    pass


class GitError(Exception):
    """Custom exception for Git-related errors."""
    pass


class OpenAIClient:
    """Wrapper for OpenAI API with consistent error handling and retry logic."""
    
    def __init__(self, api_key: Optional[str] = None):
        """Initialize OpenAI client with API key."""
        self.api_key = api_key or Config.get_api_key()
        self.client = OpenAI(api_key=self.api_key)
    
    def create_response(
        self, 
        prompt: str, 
        task_name: str = 'general',
        system_message: Optional[str] = None,
        **kwargs
    ) -> str:
        """
        Create response using new OpenAI responses API with retry logic.
        
        Args:
            prompt: User prompt
            task_name: Task type for model selection
            system_message: Optional system message
            **kwargs: Additional parameters for API call
        
        Returns:
            Response text from the API
        
        Raises:
            APIError: If API call fails after retries
        """
        model = ModelSelector.get_model_for_task(task_name)
        settings = Config.get_openai_settings(**kwargs)
        
        # Prepare input messages
        input_messages = []
        if system_message:
            input_messages.append({"role": "system", "content": system_message})
        input_messages.append({"role": "user", "content": prompt})
        
        # Prepare API parameters
        api_params = {
            "model": model,
            "input": input_messages
        }
        
        # Add reasoning effort for o3 models
        if model == 'o3':
            api_params["reasoning"] = {"effort": kwargs.get("reasoning_effort", "medium")}
        
        return self._call_with_retry(api_params, settings['max_retries'], settings['retry_delay'])
    
    def generate_image(
        self,
        prompt: str,
        **kwargs
    ) -> str:
        """
        Generate image using new image generation API.
        
        Args:
            prompt: Image description prompt
            **kwargs: Additional parameters
        
        Returns:
            Base64 encoded image data
        
        Raises:
            APIError: If image generation fails
        """
        settings = Config.get_openai_settings(**kwargs)
        
        api_params = {
            "model": Config.get_model('image'),
            "input": prompt,
            "tools": [{"type": "image_generation"}]
        }
        
        try:
            response = self.client.responses.create(**api_params)
            
            # Extract image data
            image_data = [
                output.result
                for output in response.output
                if output.type == "image_generation_call"
            ]
            
            if not image_data:
                raise APIError("No image data in response")
            
            return image_data[0]
            
        except Exception as e:
            raise APIError(f"Image generation failed: {str(e)}")
    
    def _call_with_retry(self, api_params: Dict[str, Any], max_retries: int, retry_delay: float) -> str:
        """Execute API call with retry logic."""
        last_error = None
        
        for attempt in range(max_retries):
            try:
                response = self.client.responses.create(**api_params)
                return response.output_text
                
            except Exception as e:
                last_error = e
                if attempt < max_retries - 1:
                    print(f"API call failed (attempt {attempt + 1}): {str(e)}")
                    print(f"Retrying in {retry_delay} seconds...")
                    time.sleep(retry_delay)
                    retry_delay *= 2  # Exponential backoff
        
        raise APIError(f"API call failed after {max_retries} attempts: {str(last_error)}")


class GitOperations:
    """Helper class for Git operations with error handling."""
    
    @staticmethod
    def create_branch(branch_name: str) -> None:
        """Create and push a new Git branch."""
        try:
            subprocess.run(["git", "checkout", "-b", branch_name], check=True)
            subprocess.run(
                ["git", "push", "-u", "origin", branch_name],
                check=True,
                env=dict(
                    os.environ, 
                    GIT_ASKPASS='echo', 
                    GIT_USERNAME='x-access-token', 
                    GIT_PASSWORD=os.getenv('GITHUB_TOKEN', '')
                )
            )
        except subprocess.CalledProcessError as e:
            raise GitError(f"Error creating branch {branch_name}: {e}")
    
    @staticmethod
    def commit_and_push(branch_name: str, files: Union[str, List[str]], commit_message: str) -> None:
        """Commit and push changes to Git repository."""
        try:
            # Configure Git user
            subprocess.run(["git", "config", "--global", "user.email", "actions@github.com"], check=True)
            subprocess.run(["git", "config", "--global", "user.name", "github-actions"], check=True)
            
            # Add files
            if isinstance(files, str):
                files = [files]
            
            for file_path in files:
                subprocess.run(["git", "add", file_path], check=True)
            
            # Commit and push
            subprocess.run(["git", "commit", "-m", commit_message], check=True)
            subprocess.run(
                ["git", "push", "--set-upstream", "origin", branch_name],
                check=True,
                env=dict(
                    os.environ, 
                    GIT_ASKPASS='echo', 
                    GIT_USERNAME='x-access-token', 
                    GIT_PASSWORD=os.getenv('GITHUB_TOKEN', '')
                )
            )
        except subprocess.CalledProcessError as e:
            raise GitError(f"Error committing and pushing changes: {e}")
    
    @staticmethod
    def checkout_branch(branch_name: str) -> None:
        """Checkout existing Git branch."""
        try:
            subprocess.run(["git", "checkout", branch_name], check=True)
        except subprocess.CalledProcessError as e:
            raise GitError(f"Error checking out branch {branch_name}: {e}")


class FileOperations:
    """Helper class for file operations with error handling."""
    
    @staticmethod
    def ensure_directory(directory: str) -> None:
        """Ensure directory exists, create if it doesn't."""
        os.makedirs(directory, exist_ok=True)
    
    @staticmethod
    def read_file(file_path: str) -> str:
        """Read file content with error handling."""
        try:
            with open(file_path, 'r', encoding='utf-8') as file:
                return file.read()
        except FileNotFoundError:
            raise FileNotFoundError(f"File not found: {file_path}")
        except Exception as e:
            raise IOError(f"Error reading file {file_path}: {str(e)}")
    
    @staticmethod
    def write_file(file_path: str, content: str) -> None:
        """Write content to file with error handling."""
        try:
            # Ensure directory exists
            directory = os.path.dirname(file_path)
            if directory:
                FileOperations.ensure_directory(directory)
            
            with open(file_path, 'w', encoding='utf-8') as file:
                file.write(content)
        except Exception as e:
            raise IOError(f"Error writing file {file_path}: {str(e)}")
    
    @staticmethod
    def save_image(file_path: str, base64_data: str) -> None:
        """Save base64 image data to file."""
        try:
            directory = os.path.dirname(file_path)
            if directory:
                FileOperations.ensure_directory(directory)
            
            with open(file_path, "wb") as f:
                f.write(base64.b64decode(base64_data))
        except Exception as e:
            raise IOError(f"Error saving image {file_path}: {str(e)}")


def validate_environment() -> None:
    """Validate that required environment variables are set."""
    required_vars = ['OPENAI_API_KEY', 'GITHUB_TOKEN']
    missing_vars = [var for var in required_vars if not os.getenv(var)]
    
    if missing_vars:
        print(f"Error: Missing required environment variables: {', '.join(missing_vars)}")
        sys.exit(1)


def print_error_and_exit(message: str, exit_code: int = 1) -> None:
    """Print error message and exit with specified code."""
    print(f"Error: {message}")
    sys.exit(exit_code)