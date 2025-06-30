import os
from datetime import datetime
from utils import OpenAIClient, FileOperations, validate_environment, print_error_and_exit
from config import Config

# Path configuration
task_file_path = os.path.join(Config.get_path('tasks_dir'), "new_task.md")
images_dir = Config.get_path('images_dir')

def generate_image_from_description(description, client):
    """Generate image based on task description using new image generation API."""
    # Build a more descriptive prompt for image generation
    prompt = (
        f"Create an illustration for a programming task based on the following theme:\n\n"
        f"Theme: {description[:200]}...\n\n"  # Include a snippet of the description as a theme
        "The illustration should capture the key elements of this task, which involves programming concepts. "
        "Make it visually engaging and clear, with an emphasis on learning and creativity.\n\n"
        "Make sure the image is clear, modern, and professional, suitable as an educational feature in a programming context."
    )

    try:
        # Generate image using new API
        image_base64 = client.generate_image(prompt)
        return image_base64
    except Exception as e:
        raise Exception(f"Error generating image: {str(e)}")

def save_image(image_base64):
    """Save base64 image data to file."""
    # Generate unique filename using timestamp
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    image_filename = f"task_image_{timestamp}.png"
    image_path = os.path.join(images_dir, image_filename)
    
    try:
        # Save the image using utility function
        FileOperations.save_image(image_path, image_base64)
        return os.path.join("images", image_filename)  # Return relative path
    except Exception as e:
        raise Exception(f"Error saving image: {str(e)}")

def insert_image_into_markdown(image_path, markdown_path):
    """Insert image reference into markdown file."""
    try:
        # Read the existing task description and add the image URL at the top
        markdown_content = FileOperations.read_file(markdown_path)
        image_markdown = f"![Task Image]({image_path})\n\n"
        new_markdown_content = image_markdown + markdown_content

        # Write the updated content back to the markdown file
        FileOperations.write_file(markdown_path, new_markdown_content)
    except Exception as e:
        raise Exception(f"Error updating markdown file: {str(e)}")

def main(api_key):
    """Generate and insert task image into markdown file."""
    validate_environment()
    
    client = OpenAIClient()

    try:
        # Read the task description from the markdown file
        task_description = FileOperations.read_file(task_file_path)

        # Generate the image and add it to the markdown file
        image_base64 = generate_image_from_description(task_description, client)
        image_path = save_image(image_base64)
        
        insert_image_into_markdown(image_path, task_file_path)
        print("Image generated and added to the task file.")
        
    except Exception as e:
        print_error_and_exit(f"Error in main process: {str(e)}")

if __name__ == "__main__":
    # Ensure the OpenAI API key is provided as an environment variable
    api_key = os.getenv("OPENAI_API_KEY")
    if not api_key:
        print_error_and_exit("OpenAI API key not found")
    else:
        main(api_key)
