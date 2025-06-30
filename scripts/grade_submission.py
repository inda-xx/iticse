import os
import sys
import requests
from utils import OpenAIClient, validate_environment, print_error_and_exit
from config import Config

def main(api_key, pull_request_number):
    """Grade student submission and provide feedback."""
    validate_environment()

    # Read the student's code from the task template location
    try:
        with open("src/template_code.java", "r", encoding='utf-8') as file:
            student_code = file.read()
    except FileNotFoundError:
        print_error_and_exit("template_code.java file not found")
    except Exception as e:
        print_error_and_exit(f"Error reading student code: {str(e)}")

    # Read the solution code
    try:
        with open("src/.hidden_tasks/new_task_solution.java", "r", encoding='utf-8') as file:
            solution_code = file.read()
    except FileNotFoundError:
        print_error_and_exit("new_task_solution.java file not found")
    except Exception as e:
        print_error_and_exit(f"Error reading solution code: {str(e)}")

    # Create prompt for evaluating the code
    prompt = (
        "Evaluate the following student's code based on the solution provided. "
        "Give feedback on the correctness, efficiency, and coding style. "
        "Point out any errors and suggest improvements.\n\n"
        f"### Student's Code\n```java\n{student_code}\n```\n\n"
        f"### Solution Code\n```java\n{solution_code}\n```\n"
    )
    
    system_message = (
        "You are an experienced Java programming instructor. "
        "Provide constructive feedback that helps students improve their code quality and understanding."
    )

    # Call OpenAI API to evaluate the student's code
    try:
        client = OpenAIClient()
        feedback = client.create_response(
            prompt=prompt,
            task_name='grade_submission',
            system_message=system_message,
            max_tokens=Config.TASK_SETTINGS['feedback_tokens']
        )
    except Exception as e:
        print_error_and_exit(f"Error generating feedback: {str(e)}")

    # Post the feedback as a comment on the pull request
    post_github_comment(pull_request_number, feedback)

def post_github_comment(pr_number, feedback):
    """Post feedback as a comment on GitHub PR."""
    repo_name = os.getenv('GITHUB_REPOSITORY')
    github_token = os.getenv('GITHUB_TOKEN')
    
    if not repo_name:
        print_error_and_exit("GITHUB_REPOSITORY environment variable is required")
    if not github_token:
        print_error_and_exit("GITHUB_TOKEN environment variable is required")
    
    headers = {
        "Authorization": f"token {github_token}",
        "Accept": "application/vnd.github.v3+json"
    }
    comment_url = f"https://api.github.com/repos/{repo_name}/issues/{pr_number}/comments"
    comment_body = {"body": feedback}
    
    try:
        response = requests.post(comment_url, json=comment_body, headers=headers, timeout=30)
        if response.status_code != 201:
            print_error_and_exit(f"Error posting comment: {response.status_code} {response.text}")
        else:
            print("Feedback posted successfully.")
    except requests.RequestException as e:
        print_error_and_exit(f"Error posting GitHub comment: {str(e)}")


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python grade_submission.py <api_key> <pull_request_number>")
        sys.exit(1)

    api_key = sys.argv[1]
    pull_request_number = sys.argv[2]

    main(api_key, pull_request_number)
