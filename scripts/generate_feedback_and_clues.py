import sys
import os
import requests
from utils import OpenAIClient, validate_environment, print_error_and_exit
from config import Config

def main(pr_number, test_results_file):
    """Generate feedback and clues for failed tests."""
    validate_environment()
    
    try:
        # Read test results
        with open(test_results_file, 'r') as f:
            test_results = f.read()
    except FileNotFoundError:
        print_error_and_exit(f"Test results file not found: {test_results_file}")
    except Exception as e:
        print_error_and_exit(f"Error reading test results: {str(e)}")
    
    # Prepare the prompt
    prompt = (
        f"The student's code failed the following tests:\n\n{test_results}\n\n"
        "Provide constructive and concise feedback to help the student understand what went wrong and how to fix it."
    )
    
    # System message (fixed duplicate content bug)
    system_message = (
        "You are a helpful instructor providing constructive feedback to a student. "
        "You are instructive but to the point and concise with your help and do not reveal "
        "the whole answer to the students but provide hints so that they can get there."
    )

    # Initialize OpenAI client and generate feedback
    try:
        client = OpenAIClient()
        feedback = client.create_response(
            prompt=prompt,
            task_name='generate_feedback_and_clues',
            system_message=system_message,
            max_tokens=Config.TASK_SETTINGS['feedback_tokens']
        )
    except Exception as e:
        print_error_and_exit(f"Error generating feedback: {str(e)}")

    # Post the feedback as a comment on the PR
    post_github_comment(pr_number, feedback)

def post_github_comment(pr_number, feedback):
    """Post feedback as a comment on GitHub PR."""
    gh_token = os.getenv('GH_TOKEN') or os.getenv('GITHUB_TOKEN')
    if not gh_token:
        print_error_and_exit("GH_TOKEN or GITHUB_TOKEN environment variable is required")

    repo = os.getenv('GITHUB_REPOSITORY')
    if not repo:
        print_error_and_exit("GITHUB_REPOSITORY environment variable is required")

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
    if len(sys.argv) != 3:
        print("Usage: python generate_feedback_and_clues.py <pr_number> <test_results_file>")
        sys.exit(1)
    pr_number = sys.argv[1]
    test_results_file = sys.argv[2]
    main(pr_number, test_results_file)
