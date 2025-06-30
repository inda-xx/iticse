import os
import re
import sys
from utils import OpenAIClient, GitOperations, FileOperations, validate_environment, print_error_and_exit
from config import Config

def main(api_key, branch_name):
    """Generate unit tests based on solution code."""
    validate_environment()
    
    if not branch_name:
        print_error_and_exit("Branch name is missing")

    client = OpenAIClient()

    # Ensure we are on the correct branch
    try:
        GitOperations.checkout_branch(branch_name)
    except Exception as e:
        print_error_and_exit(f"Error checking out branch {branch_name}: {str(e)}")

    # Read the solution code from the .hidden_tasks directory
    solution_files = []
    hidden_tasks_dir = Config.get_path('hidden_tasks_dir')
    
    try:
        for filename in os.listdir(hidden_tasks_dir):
            if filename.endswith(".java"):
                file_path = os.path.join(hidden_tasks_dir, filename)
                content = FileOperations.read_file(file_path)
                solution_files.append(content)
    except FileNotFoundError:
        print_error_and_exit("Solution files not found in .hidden_tasks directory")
    except Exception as e:
        print_error_and_exit(f"Error reading solution files: {str(e)}")

    if not solution_files:
        print_error_and_exit("No Java solution files found in .hidden_tasks")

    solution = "\n\n".join(solution_files)

    # Example tests to inspire the model (not to be directly copied)
    example_tests = """
    import org.junit.Test;

    import static org.junit.Assert.assertArrayEquals;
    import static org.junit.Assert.assertEquals;

    /**
    * Test cases for the Arrays class
    * NOTE: We do not require the students to handle edge cases such as
    * empty arrays, so these cases are not tested.
    */
    public class ArraysTest {
        private final int[] intArrayWithNegativeNumbers = new int[] {0, -1, -2, -3, -4, -5};
        private final int[] intArrayWithPositiveNumbers = new int[] {1, 2, 3, 4, 5, 0};
        private final double[] doubleArrayWithNegativeNumbers = new double[] {0, -1, -2, -3, -4, -5};
        private final double[] doubleArrayWithPositiveNumbers = new double[] {0, 1, 2, 3, 4, 5};


        @Test
        public void intAveragePositiveNumbersGivesExpectedResult() {
            int expected = java.util.Arrays.stream(intArrayWithPositiveNumbers).sum() /
                        intArrayWithPositiveNumbers.length;
            assertEquals(expected, Arrays.average(intArrayWithPositiveNumbers));
        }

        @Test
        public void intAverageNegativeNumbersGivesExpectedResult() {
            int expected = java.util.Arrays.stream(intArrayWithNegativeNumbers).sum() /
                        intArrayWithNegativeNumbers.length;
            assertEquals(expected, Arrays.average(intArrayWithNegativeNumbers));
        }

        @Test
        public void doubleAveragePositiveNumbersGivesExpectedResult() {
            double expected = java.util.Arrays.stream(doubleArrayWithPositiveNumbers).sum() /
                            doubleArrayWithPositiveNumbers.length;
            assertEquals(expected, Arrays.average(doubleArrayWithPositiveNumbers), 0);
        }

        @Test
        public void doubleAverageNegativeNumbersGivesExpectedResult() {
            double expected = java.util.Arrays.stream(doubleArrayWithNegativeNumbers).sum() /
                            doubleArrayWithNegativeNumbers.length;
            assertEquals(expected, Arrays.average(doubleArrayWithNegativeNumbers), 0);
        }

        @Test
        public void smallestElementFindsSmallestInPositiveNumbers() {
            int expected = java.util.Arrays.stream(intArrayWithPositiveNumbers).min().orElse(0);
            assertEquals(expected, Arrays.smallestElement(intArrayWithPositiveNumbers));
        }

        @Test
        public void smallestElementFindsSmallestInNegativeNumbers() {
            int expected = java.util.Arrays.stream(intArrayWithNegativeNumbers).min().orElse(0);
            assertEquals(expected, Arrays.smallestElement(intArrayWithNegativeNumbers));
        }

        @Test
        public void reverseCorrectlyCreatesReversedCopy() {
            int[] reversed = Arrays.reverse(intArrayWithPositiveNumbers);
            assertEquals(intArrayWithPositiveNumbers.length, reversed.length);
            for (int i = 0; i < reversed.length; i++)
                assertEquals(intArrayWithPositiveNumbers[i], reversed[reversed.length - i - 1]);
        }

        @Test
        public void reverseDoesNotModifyOriginalArray() {
            int[] original = java.util.Arrays.copyOf(intArrayWithPositiveNumbers,
                                                    intArrayWithPositiveNumbers.length);
            Arrays.reverse(intArrayWithPositiveNumbers);
            assertArrayEquals(original, intArrayWithPositiveNumbers);
        }

        @Test
        public void evenNumbersGivesCorrectResultForPositiveNumbers() {
            int[] expected = java.util.Arrays.stream(intArrayWithPositiveNumbers)
                                            .filter(i -> i % 2 == 0)
                                            .toArray();
            assertArrayEquals(expected, Arrays.evenNumbers(intArrayWithPositiveNumbers));
        }

        @Test
        public void evenNumbersGivesCorrectResultForNegativeNumbers() {
            int[] expected = java.util.Arrays.stream(intArrayWithNegativeNumbers)
                                            .filter(i -> i % 2 == 0)
                                            .toArray();
            assertArrayEquals(expected, Arrays.evenNumbers(intArrayWithNegativeNumbers));
        }

        @Test
        public void evenNumbersDoesNotModifyOriginalArray() {
            int[] original = java.util.Arrays.copyOf(intArrayWithPositiveNumbers,
                                                    intArrayWithPositiveNumbers.length);
            Arrays.evenNumbers(intArrayWithPositiveNumbers);
            assertArrayEquals(original, intArrayWithPositiveNumbers);
        }
    }



    import org.junit.Test;

    import static org.junit.Assert.assertEquals;

    import java.util.*;
    import java.util.stream.IntStream;

    /**
    * Test for SetTheory
    */
    public class SetTheoryTest {

        private static final int MIN = 0;
        private static final int MAX = 100;
        private static final List<Integer> UNIVERSE = IntStream.range(MIN, MAX).boxed().toList();

        @Test
        public void generateSetCorrectlyCreatesUniverse() {
            List<Integer> expected = IntStream.range(MIN, MAX).boxed().toList();
            List<Integer> actual = SetTheory.generateSet(MIN, MAX);
            assertEquals(expected, actual);
        }

        @Test
        public void generateSetCorrectlyCreatesInterval() {
            List<Integer> expected = IntStream.range(67, 89).boxed().toList();
            List<Integer> actual = SetTheory.generateSet(67, 89);
            assertEquals(expected, actual);
        }
        @Test
        public void generateSetReturnsEmptySetWhenMinIsGreaterThanMax() {
            List<Integer> actual = SetTheory.generateSet(2, 1);
            assertEquals(Collections.emptyList(), actual);
        }

        @Test
        public void generateSetReturnsEmptySetWhenMinIsEqualToMax() {
            List<Integer> actual = SetTheory.generateSet(1, 1);
            assertEquals(Collections.emptyList(), actual);
        }

        @Test
        public void generateSetReturnsExpectedResultWhenMaxIsGreaterThan100() {
            List<Integer> expected = IntStream.range(50, MAX).boxed().toList();
            List<Integer> actual = SetTheory.generateSet(50, 101);
            assertEquals(expected, actual);
        }

        @Test
        public void generateSetReturnsExpectedResultWhenMinIsLessThan0() {
            List<Integer> expected = IntStream.range(MIN, 50).boxed().toList();
            List<Integer> actual = SetTheory.generateSet(-1, 50);
            assertEquals(expected, actual);
        }

        @Test
        public void unionReturnsExpectedResultWhenSetsOverlap() {
            List<Integer> a = IntStream.range(10, 55).boxed().toList();
            List<Integer> b = IntStream.range(50, 90).boxed().toList();

            Set<Integer> expected =  new HashSet<>(a);
            expected.addAll(b);
            List<Integer> actual = SetTheory.union(new ArrayList<>(a), new ArrayList<>(b));

            assertEquals(expected.stream().toList(), actual);
        }

        @Test
        public void unionReturnsExpectedResultWhenSetsAreDisjoint() {
            List<Integer> a = IntStream.range(10, 50).boxed().toList();
            List<Integer> b = IntStream.range(55, 90).boxed().toList();

            Set<Integer> expected =  new HashSet<>(a);
            expected.addAll(b);
            List<Integer> actual = SetTheory.union(new ArrayList<>(a), new ArrayList<>(b));

            assertEquals(expected.stream().toList(), actual);
        }

        @Test
        public void intersectionReturnsExpectedResultWhenSetsOverlap() {
            List<Integer> a = IntStream.range(10, 55).boxed().toList();
            List<Integer> b = IntStream.range(50, 90).boxed().toList();

            Set<Integer> expected =  new HashSet<>(a);
            expected.retainAll(b);
            List<Integer> actual = SetTheory.intersection(new ArrayList<>(a), new ArrayList<>(b));

            assertEquals(expected.stream().toList(), actual);
        }

        @Test
        public void intersectionReturnsEmptyListWhenSetsAreDisjoint() {
            List<Integer> a = IntStream.range(10, 50).boxed().toList();
            List<Integer> b = IntStream.range(55, 90).boxed().toList();
            List<Integer> actual = SetTheory.intersection(new ArrayList<>(a), new ArrayList<>(b));

            assertEquals(Collections.emptyList(), actual);
        }

        @Test
        public void complementReturnsEmptySetWhenInputIsUniverse() {
            assertEquals(Collections.emptyList(), SetTheory.complement(new ArrayList<>(UNIVERSE)));
        }

        @Test
        public void complementReturnsExpectedResultForInterval() {
            List<Integer> set = IntStream.range(45, 67).boxed().toList();
            var expected = new HashSet<>(UNIVERSE);
            expected.removeAll(set);
            assertEquals(expected.stream().toList(), SetTheory.complement(new ArrayList<>(set)));
        }

        @Test
        public void cardinalityReturnsCorrectValueForUniverse() {
            assertEquals(UNIVERSE.size(), SetTheory.cardinality(new ArrayList<>(UNIVERSE)));
        }

        @Test
        public void cardinalityReturnsCorrectValueForEmptySet() {
            assertEquals(0, SetTheory.cardinality(new ArrayList<>()));
        }

        @Test
        public void cardinalityOfUnionReturnsCorrectValueForOverlappingSets() {
            int actual = SetTheory.cardinalityOfUnion(new ArrayList<>(UNIVERSE), new ArrayList<>(UNIVERSE));
            assertEquals(UNIVERSE.size(), actual);
        }

        @Test
        public void cardinalityOfUnionReturnsCorrectValueForDisjointSets() {
            List<Integer> a = IntStream.range(MIN, 21).boxed().toList();
            List<Integer> b = IntStream.range(50, 67).boxed().toList();
            int actual = SetTheory.cardinalityOfUnion(new ArrayList<>(a), new ArrayList<>(b));
            assertEquals(a.size() + b.size(), actual);
        }

        @Test
        public void cardinalityOfUnionReturnsCorrectValueWhenBothSetsAreEmpty() {
            assertEquals(0, SetTheory.cardinalityOfUnion(new ArrayList<>(), new ArrayList<>()));
        }
    }


    """

    # Combine the solution into a single prompt for test generation
    prompt = (
        "You are part of a task generation system to create weekly tasks for CS1 studnets"
        "Your specific duty is to create tests based on the solution files so the studnets can run them to ensure they have fullfilled the tasks goals."
        f"Given the following Java solution, generate a set of high-quality unit tests. "
        f"Ensure the tests are thorough, robust, and cover all edge cases, including invalid inputs, boundary conditions, and performance considerations. "
        f"Ensure the tests use the correct imports and that each class is placed in the correct file as per Java naming conventions.\n\n"
        f"### Solution\n{solution}\n\n"
        f"### Example Tests (for inspiration only)\n{example_tests}\n\n"
        "IMPORTANT: The response must be plain Java code with no markdown formatting or ```java blocks. Ensure that the response is ready to be saved directly as a .java file."
        "Make sure all the right imports are always included."
    )

    try:
        response_content = client.create_response(
            prompt=prompt,
            task_name='generate_tests',
            system_message="You are a helpful assistant creating comprehensive unit tests for Java programming assignments.",
            max_tokens=3000
        )
    except Exception as e:
        print_error_and_exit(f"Failed to generate tests: {str(e)}")

    # Write the generated tests to appropriate Java files in the gen_test directory
    gen_test_dir = Config.get_path('gen_test_dir')
    
    try:
        write_generated_tests_to_files(gen_test_dir, response_content)

        # Commit and push changes
        GitOperations.commit_and_push(
            branch_name,
            gen_test_dir,
            "Add generated tests"
        )
    except Exception as e:
        print_error_and_exit(f"Error processing tests: {str(e)}")


def write_generated_tests_to_files(directory, code_content):
    """
    Write generated Java tests to separate files based on class names.
    Ensures that import statements and public class declarations are correctly handled.
    """
    # Ensure directory exists
    FileOperations.ensure_directory(directory)
    
    # Split the code into blocks starting with 'public class' or similar
    file_blocks = re.split(r'(?=public\s+class\s+\w+\s*{)', code_content)

    for block in file_blocks:
        if not block.strip():
            continue  # Skip empty blocks

        # Extract class name
        class_name_match = re.search(r'public\s+class\s+([A-Za-z_]\w*)\s*{', block)
        if class_name_match:
            class_name = class_name_match.group(1)
        else:
            print(f"Skipping block due to missing class name in block: {block[:50]}")
            continue

        # Ensure the block has matching braces
        if block.count('{') != block.count('}'):
            print(f"Skipping block due to unmatched braces in class {class_name}.")
            continue

        # Construct the file content
        package_declaration = "package test;\n\n"
        imports = (
            "import org.junit.Before;\n"
            "import org.junit.Test;\n"
            "import static org.junit.Assert.*;\n\n"
        )
        file_content = package_declaration + imports + block

        file_name = f"{class_name}.java"
        file_path = os.path.join(directory, file_name)

        try:
            FileOperations.write_file(file_path, file_content)
            print(f"Successfully wrote {file_name}")
        except Exception as e:
            print(f"Error writing file {file_name}: {e}")


if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python generate_tests.py <api_key> <branch_name>")
        sys.exit(1)

    api_key = sys.argv[1]
    branch_name = sys.argv[2]

    main(api_key, branch_name)
