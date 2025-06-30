import os
import sys
import requests
from utils import OpenAIClient, validate_environment, print_error_and_exit, FileOperations
from config import Config


def main():
    """Review student submission and provide feedback."""
    validate_environment()
    
    # Environment variables
    gh_token = os.getenv('GH_TOKEN') or os.getenv('GITHUB_TOKEN')
    repo = os.getenv('GITHUB_REPOSITORY')
    pr_number = os.getenv('GITHUB_PR_NUMBER')

    if not all([gh_token, repo, pr_number]):
        print_error_and_exit("Missing required environment variables: GH_TOKEN, GITHUB_REPOSITORY, GITHUB_PR_NUMBER")

    # Initialize OpenAI client
    client = OpenAIClient()

    # Read the task description from tasks/new_task.md
    try:
        task_description = FileOperations.read_file('tasks/new_task.md')
    except FileNotFoundError:
        print_error_and_exit("Task description file 'tasks/new_task.md' not found")
    except Exception as e:
        print_error_and_exit(f"Error reading task description: {str(e)}")

    # Collect student's submission code from gen_src directory
    submission_code = ""
    gen_src_dir = Config.get_path('gen_src_dir')
    
    try:
        for root, dirs, files in os.walk(gen_src_dir):
            for file in files:
                if file.endswith('.java'):
                    file_path = os.path.join(root, file)
                    code_content = FileOperations.read_file(file_path)
                    submission_code += f"// File: {file_path}\n{code_content}\n\n"
    except Exception as e:
        print_error_and_exit(f"Error reading submission files: {str(e)}")

    if not submission_code:
        print_error_and_exit(f"No Java files found in '{gen_src_dir}' directory")

    # Prepare the prompt
    prompt = (
        "You are a Java programming instructor. A student has submitted code for the following assignment:\n\n"
        f"{task_description}\n\n"
        "Here is the student's submission:\n\n"
        f"{submission_code}\n\n"
        "Please review the student's code for correctness, code quality, and adherence to the assignment requirements. "
        "Check if the code compiles and meets the task objectives. "
        "Provide constructive feedback, pointing out any issues and suggesting improvements. Your suggestions should be subtle hints to help the student with their submission rather than revealing the answer. "
        "If the code is sound and correct, provide positive feedback and perhaps suggest advanced topics for further learning."
        "Keep your output to a point and be concise and effective in your answers."
    )

    # Generate feedback using OpenAI API
    system_message = "You are a helpful and thorough Java programming instructor."
    
    try:
        feedback = client.create_response(
            prompt=prompt,
            task_name='review_submission',
            system_message=system_message,
            max_tokens=1000
        )
    except Exception as e:
        print_error_and_exit(f"Error generating feedback: {str(e)}")

    # Post the feedback as a comment on the PR
    post_github_comment(pr_number, feedback, gh_token, repo)

def post_github_comment(pr_number, feedback, gh_token, repo):
    """Post feedback as a comment on GitHub PR."""
    comment_url = f"https://api.github.com/repos/{repo}/issues/{pr_number}/comments"
    headers = {
        'Authorization': f'token {gh_token}',
        'Accept': 'application/vnd.github.v3+json'
    }
    data = {'body': feedback}

    try:
        r = requests.post(comment_url, json=data, headers=headers, timeout=30)
        if r.status_code == 201:
            print('Feedback posted successfully.')
        else:
            print_error_and_exit(f'Failed to post feedback: {r.status_code} {r.text}')
    except requests.RequestException as e:
        print_error_and_exit(f'Error posting GitHub comment: {str(e)}')


if __name__ == "__main__":
    main()
