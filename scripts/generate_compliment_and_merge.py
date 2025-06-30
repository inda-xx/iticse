import sys
import os
import requests
from utils import OpenAIClient, validate_environment, print_error_and_exit
from config import Config  


def main(pr_number, test_results_file):
    """Generate congratulatory message for successful submissions."""
    validate_environment()
    
    # Prepare the prompt
    prompt = (
        "A student has submitted their solution for a programming assignment, and their code passed all the tests.\n\n"
        "Provide a congratulatory message to the student, do not make it too rosy or too long but simple, "
        "and suggest some further readings or topics they can explore to deepen their understanding."
    )
    
    system_message = "You are a helpful instructor providing positive feedback to a student."

    # Generate congratulatory message
    try:
        client = OpenAIClient()
        message = client.create_response(
            prompt=prompt,
            task_name='generate_compliment_and_merge',
            system_message=system_message,
            max_tokens=Config.TASK_SETTINGS['compliment_tokens']
        )
    except Exception as e:
        print_error_and_exit(f"Error generating message: {str(e)}")

    # Post the message as a comment on the PR
    post_github_comment(pr_number, message)

    # Optionally, merge the PR
    # Note: Automatic merging should be used with caution
    # Uncomment the following code if you want to automatically merge the PR
    # merge_url = f"https://api.github.com/repos/{repo}/pulls/{pr_number}/merge"
    # merge_response = requests.put(merge_url, headers=headers)
    # if merge_response.status_code == 200:
    #     print('Pull request merged successfully.')
    # else:
    #     print(f'Failed to merge pull request: {merge_response.status_code} {merge_response.text}')
    #     sys.exit(1)

def post_github_comment(pr_number, message):
    """Post congratulatory message as a comment on GitHub PR."""
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
    data = {'body': message}

    try:
        r = requests.post(comment_url, json=data, headers=headers, timeout=30)
        if r.status_code == 201:
            print('Message posted successfully.')
        else:
            print_error_and_exit(f'Failed to post message: {r.status_code} {r.text}')
    except requests.RequestException as e:
        print_error_and_exit(f'Error posting GitHub comment: {str(e)}')


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python generate_compliment_and_merge.py <pr_number> <test_results_file>")
        sys.exit(1)
    pr_number = sys.argv[1]
    test_results_file = sys.argv[2]
    main(pr_number, test_results_file)
