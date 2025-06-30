"""
Centralized configuration for the task generation system.
Provides model configurations, retry settings, and API parameters.
"""

import os
from typing import Dict, Any


class Config:
    """Configuration class for task generation system."""
    
    # Model configurations
    MODELS = {
        'reasoning': 'o3',  # For complex reasoning tasks
        'general': 'o4-mini',  # For general tasks
        'legacy': 'gpt-4.1-mini',  # For compatibility
        'image': 'gpt-4.1-mini'  # For image generation
    }
    
    # API settings
    OPENAI_SETTINGS = {
        'temperature': 0.7,
        'max_tokens': 1500,
        'timeout': 60,
        'max_retries': 3,
        'retry_delay': 1.0
    }
    
    # Task generation settings
    TASK_SETTINGS = {
        'exercises_1_2_tokens': 900,
        'default_tokens': 1500,
        'feedback_tokens': 500,
        'compliment_tokens': 300
    }
    
    # Image generation settings
    IMAGE_SETTINGS = {
        'size': '1792x1024',
        'quality': 'hd',
        'style': 'natural'
    }
    
    # File paths
    PATHS = {
        'tasks_dir': 'tasks',
        'hidden_tasks_dir': '.hidden_tasks',
        'gen_src_dir': 'gen_src',
        'gen_test_dir': 'gen_test',
        'images_dir': 'tasks/images'
    }
    
    @classmethod
    def get_model(cls, model_type: str) -> str:
        """Get model name for specific task type."""
        return cls.MODELS.get(model_type, cls.MODELS['general'])
    
    @classmethod
    def get_openai_settings(cls, **overrides) -> Dict[str, Any]:
        """Get OpenAI API settings with optional overrides."""
        settings = cls.OPENAI_SETTINGS.copy()
        settings.update(overrides)
        return settings
    
    @classmethod
    def get_path(cls, path_type: str) -> str:
        """Get configured path for specific directory type."""
        return cls.PATHS.get(path_type, '')
    
    @classmethod
    def get_api_key(cls) -> str:
        """Get OpenAI API key from environment."""
        api_key = os.getenv('OPENAI_API_KEY')
        if not api_key:
            raise ValueError("OPENAI_API_KEY environment variable is required")
        return api_key


class ModelSelector:
    """Helper class to select appropriate models for different tasks."""
    
    REASONING_TASKS = [
        'generate_task_description',
        'generate_solution', 
        'generate_tests'
    ]
    
    GENERAL_TASKS = [
        'generate_template_code',
        'generate_feedback_and_clues',
        'generate_compliment_and_merge',
        'review_submission'
    ]
    
    @classmethod
    def get_model_for_task(cls, task_name: str) -> str:
        """Select appropriate model based on task complexity."""
        if task_name in cls.REASONING_TASKS:
            return Config.get_model('reasoning')
        elif task_name in cls.GENERAL_TASKS:
            return Config.get_model('general')
        else:
            return Config.get_model('general')