import os
import sys
from datetime import datetime
import pytz
from pytz import timezone
from utils import OpenAIClient, GitOperations, FileOperations, validate_environment, print_error_and_exit
from config import Config

def main(api_key):
    """Generate comprehensive task description with exercises."""
    validate_environment()
    
    # Initialize the OpenAI client
    client = OpenAIClient()

    # Extract theme and language from environment variables
    theme = os.getenv("TASK_THEME", "Create a basic Java application with the following requirements.")
    language = os.getenv("TASK_LANGUAGE", "English")

    # Function to make API calls with configurable token limit
    def generate_task_step(system_message, user_message, max_tokens=None):
        """Generate task step using OpenAI API."""
        try:
            return client.create_response(
                prompt=user_message,
                task_name='generate_task_description',
                system_message=system_message,
                max_tokens=max_tokens or Config.TASK_SETTINGS['default_tokens']
            )
        except Exception as e:
            print_error_and_exit(f"Error generating task step: {str(e)}")

    # Define learning goals
    learning_goals = """
    * Using Data from Files to Instantiate Objects
    * Designing Classes
    * Programming Creatively

        {
            "main_point": "Using Data from Files to Instantiate Objects",
            "sub_points": [
                "Description: This concept introduces the process of reading data from files and using it to create and initialize objects in Java. Mastery of this skill is essential for applications that require dynamic data handling and object creation based on external data sources.",
                "Subpoint 1: Understanding file formats and parsing techniques for data extraction.",
                "Subpoint 2: Using file I/O classes to read data and convert it into object attributes.",
                "Subpoint 3: Handling exceptions and ensuring data integrity during object instantiation."
            ]
        },
        {
            "main_point": "Designing Classes",
            "sub_points": [
                "Description: This concept focuses on the principles and practices of designing well-structured Java classes, which are fundamental to object-oriented programming. Effective class design is crucial for creating maintainable and scalable software systems.",
                "Subpoint 1: Identifying class responsibilities and defining clear interfaces.",
                "Subpoint 2: Implementing cohesive class structures with appropriate fields and methods.",
                "Subpoint 3: Leveraging design patterns to solve common problems and enhance class design."
            ]
        },
        {
            "main_point": "Programming Creatively",
            "sub_points": [
                "Description: This concept encourages students to apply creative thinking and problem-solving techniques in their programming projects. Creative programming is vital for developing innovative solutions and enhancing the functionality and user experience of software applications.",
                "Subpoint 1: Exploring different approaches to problem-solving and algorithm design.",
                "Subpoint 2: Encouraging experimentation and iteration to refine solutions.",
                "Subpoint 3: Integrating user feedback and testing to improve program effectiveness and usability."
            ]
        }
    """

    # System message for both calls
    system_message = (
        "You are an experienced programming instructor creating detailed tasks for university-level CS1 students. "
        "The students have basic programming capabilities and are learning Java. "
        f"The goal is to teach key programming concepts based on these learning goals:\n{learning_goals}\n"
        "The tasks should be solvable within one week. "
        "Code snippets should provide scaffolding without revealing solutions. "
        "Use a conversational but professional tone that engages students."
    )

    # CALL 1: Generate task overview and exercises 1-2
    user_message_call1 = (
        f"Create the beginning of a programming task in {language} with theme: {theme}\n\n"
        "Generate the task overview and first two exercises using this EXACT structure:\n\n"
        "# [Creative Task Title with Relevant Emoji]\n\n"
        "## 📋 Overview\n"
        "[Write 2-3 engaging paragraphs that introduce the task, explain what students will build, "
        "and why it's interesting/relevant. Include a real-world connection or story that motivates the task.]\n\n"
        "## 🎯 Learning Objectives\n"
        "By completing this task, you will:\n"
        "- [Specific objective related to file I/O and object instantiation]\n"
        "- [Specific objective related to class design]\n"
        "- [Specific objective related to creative programming]\n"
        "[Add 1-2 more specific objectives as needed]\n\n"
        "## 📚 Prerequisites\n"
        "Before starting this task, you should be familiar with:\n"
        "- [Specific Java concept needed]\n"
        "- [Another prerequisite]\n"
        "- [Tools/software needed]\n\n"
        "## 🚀 Getting Started\n"
        "[Brief setup instructions, any starter files to download, or initial steps to take. "
        "Keep this concise but helpful.]\n\n"
        "## Exercises\n\n"
        "### Exercise 1: [Descriptive Title] 📖\n"
        "**⏱️ Estimated Time:** 20-30 minutes\n\n"
        "**🎯 Goal:** [One sentence explaining what conceptual understanding students will gain]\n\n"
        "**📝 Instructions:**\n"
        "[Create a theoretical exercise that explores the concepts without heavy coding. "
        "Include 3-4 thought-provoking questions or analysis tasks. "
        "This should prepare students conceptually for the coding ahead.]\n\n"
        "**💡 Hints:**\n"
        "<details>\n"
        "<summary>Click for hint</summary>\n\n"
        "[Provide a helpful hint that guides without giving away the answer. "
        "Include a real-world analogy or example if applicable.]\n\n"
        "</details>\n\n"
        "**✅ Success Criteria:**\n"
        "- [ ] [Specific measurable criterion]\n"
        "- [ ] [Another specific criterion]\n"
        "- [ ] [Final criterion for this exercise]\n\n"
        "### Exercise 2: [Descriptive Title] 🔍\n"
        "**⏱️ Estimated Time:** 30-40 minutes\n\n"
        "**🎯 Goal:** [Explain how this exercise bridges theory to practice]\n\n"
        "**📝 Instructions:**\n"
        "[Create a design or analysis exercise. This might involve:\n"
        "- Sketching class diagrams\n"
        "- Analyzing code structure\n"
        "- Planning the solution approach\n"
        "- Writing pseudocode\n"
        "Include some starter code or examples if helpful.]\n\n"
        "**💡 Hints:**\n"
        "<details>\n"
        "<summary>Click for hint</summary>\n\n"
        "[Hint about design principles or common pitfalls to avoid]\n\n"
        "```java\n"
        "// Example structure or pattern (if applicable)\n"
        "```\n\n"
        "</details>\n\n"
        "**✅ Success Criteria:**\n"
        "- [ ] [Design-related criterion]\n"
        "- [ ] [Analysis criterion]\n"
        "- [ ] [Planning criterion]\n"
    )

    task_part1 = generate_task_step(system_message, user_message_call1, 3500)
    print("=== PART 1: OVERVIEW AND EXERCISES 1-2 ===")
    print(task_part1)

    # CALL 2: Generate exercises 3-6 based on the established context
    user_message_call2 = (
        f"Continue the task you started. Here's what you've written so far:\n\n{task_part1}\n\n"
        "Now create exercises 3-6 following this EXACT structure for each:\n\n"
        "### Exercise 3: [Descriptive Title] 🏗️\n"
        "**⏱️ Estimated Time:** 45-60 minutes\n\n"
        "**🎯 Goal:** [Explain the basic implementation goal that builds on exercises 1-2]\n\n"
        "**📝 Instructions:**\n"
        "[First hands-on coding exercise. Should be manageable but meaningful. "
        "Include:\n"
        "- Clear implementation steps\n"
        "- Starter code with TODO comments\n"
        "- Expected output example]\n\n"
        "```java\n"
        "// Starter code\n"
        "public class SomethingRelevant {\n"
        "    // TODO: Add fields\n"
        "    \n"
        "    // TODO: Implement constructor\n"
        "    \n"
        "    // TODO: Add methods\n"
        "}\n"
        "```\n\n"
        "**💡 Hints:**\n"
        "<details>\n"
        "<summary>Click for hint</summary>\n\n"
        "[Implementation hint]\n\n"
        "```java\n"
        "// Example of a key concept or pattern\n"
        "```\n\n"
        "</details>\n\n"
        "**✅ Success Criteria:**\n"
        "- [ ] [Implementation criterion]\n"
        "- [ ] [Functionality criterion]\n"
        "- [ ] [Code quality criterion]\n\n"
        "### Exercise 4: [Descriptive Title] 🔄\n"
        "**⏱️ Estimated Time:** 45-60 minutes\n\n"
        "**🎯 Goal:** [Explain how this builds on exercise 3 with integration/enhancement]\n\n"
        "**📝 Instructions:**\n"
        "[Build directly on exercise 3. Add new features or integrate components. "
        "This is where students start seeing their project come together.]\n\n"
        "**💡 Hints:**\n"
        "<details>\n"
        "<summary>Click for hint</summary>\n\n"
        "[Integration hints, possibly showing how components connect]\n\n"
        "</details>\n\n"
        "**✅ Success Criteria:**\n"
        "- [ ] [Integration criterion]\n"
        "- [ ] [Enhancement criterion]\n"
        "- [ ] [Testing criterion]\n\n"
        "### Exercise 5: [Descriptive Title] 🚀\n"
        "**⏱️ Estimated Time:** 60-90 minutes\n\n"
        "**🎯 Goal:** [Advanced feature involving file I/O and all learning objectives]\n\n"
        "**📝 Instructions:**\n"
        "[Challenging exercise that combines all concepts. Must include file I/O. "
        "Break into manageable sub-tasks. Provide architectural guidance.]\n\n"
        "**💡 Hints:**\n"
        "<details>\n"
        "<summary>Click for hint</summary>\n\n"
        "[File I/O example and error handling guidance]\n\n"
        "```java\n"
        "// File reading pattern\n"
        "try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {\n"
        "    // Process file\n"
        "} catch (IOException e) {\n"
        "    // Handle errors\n"
        "}\n"
        "```\n\n"
        "</details>\n\n"
        "**✅ Success Criteria:**\n"
        "- [ ] [File handling criterion]\n"
        "- [ ] [Advanced feature criterion]\n"
        "- [ ] [Error handling criterion]\n"
        "- [ ] [Documentation criterion]\n\n"
        "### Exercise 6: [Descriptive Title] 🌟\n"
        "**⏱️ Estimated Time:** 60-90 minutes\n\n"
        "**🎯 Goal:** [Creative extension that goes slightly beyond basic CS1]\n\n"
        "**📝 Instructions:**\n"
        "[Offer 2-3 different paths students can choose:\n\n"
        "**Option A: [Creative Path Name]**\n"
        "- [Description of this option]\n"
        "- [What makes it interesting]\n\n"
        "**Option B: [Different Creative Path]**\n"
        "- [Description of this option]\n"
        "- [What makes it interesting]\n\n"
        "**Option C: [Third Path]**\n"
        "- [Description of this option]\n"
        "- [What makes it interesting]\n\n"
        "All options should demonstrate creativity and proper documentation with Javadoc.]\n\n"
        "**💡 Hints:**\n"
        "<details>\n"
        "<summary>Click for hint</summary>\n\n"
        "[General guidance applicable to all paths]\n\n"
        "```java\n"
        "/**\n"
        " * Example Javadoc for your creative feature\n"
        " * @param paramName description\n"
        " * @return what it returns\n"
        " */\n"
        "public ReturnType yourCreativeMethod(ParamType paramName) {\n"
        "    // Implementation\n"
        "}\n"
        "```\n\n"
        "</details>\n\n"
        "**✅ Success Criteria:**\n"
        "- [ ] [Creativity criterion]\n"
        "- [ ] [Documentation criterion]\n"
        "- [ ] [Functionality criterion]\n"
        "- [ ] [Code quality criterion]\n\n"
        "## 🎉 Submission Checklist\n\n"
        "Before submitting, ensure you have:\n\n"
        "- [ ] Completed all 6 exercises\n"
        "- [ ] Tested your code thoroughly\n"
        "- [ ] Added appropriate error handling\n"
        "- [ ] Written clear Javadoc comments\n"
        "- [ ] Followed Java naming conventions\n"
        "- [ ] Created any required output files\n"
        "- [ ] [Add 3-5 more specific items based on the task]\n\n"
        "## 🤔 Reflection Questions\n\n"
        "1. [Thought-provoking question about the main concepts]\n"
        "2. [Question about design decisions they made]\n"
        "3. [Question connecting to real-world applications]\n\n"
        "## 📚 Additional Resources\n\n"
        "- [Relevant Java documentation or tutorial]\n"
        "- [Article or resource about the domain/theme]\n"
        "- [Advanced topic they might explore next]\n"
    )

    task_part2 = generate_task_step(system_message, user_message_call2, 4500)
    print("\n=== PART 2: EXERCISES 3-6 AND CONCLUSION ===")
    print(task_part2)

    # Combine both parts
    complete_task = f"{task_part1}\n\n{task_part2}"

    # Create a new branch with a unique name
    stockholm_tz = timezone('Europe/Stockholm')
    branch_name = f"task-{datetime.now(stockholm_tz).strftime('%Y%m%d%H%M')}"
    
    try:
        GitOperations.create_branch(branch_name)
        
        # Write the response content to a markdown file
        task_file_path = os.path.join(Config.get_path('tasks_dir'), "new_task.md")
        FileOperations.write_file(task_file_path, complete_task)
        
        # Commit and push changes
        GitOperations.commit_and_push(
            branch_name, 
            task_file_path, 
            f"Add new task description: {branch_name}"
        )
    except Exception as e:
        print_error_and_exit(f"Error creating branch and committing changes: {str(e)}")

    # Output the branch name for the next job
    print(f"::set-output name=branch_name::{branch_name}")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python generate_task_description.py <api_key>")
        sys.exit(1)

    api_key = sys.argv[1]
    main(api_key)