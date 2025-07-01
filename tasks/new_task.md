# Galactic Travel Log 🪐🗺️

## 📋 Overview
Imagine you are part of the Interstellar Tourism Board, curating travel packages for adventurous space-farers. Each day, probes send back files packed with data about newly discovered planets—temperature ranges, atmospheric makeup, gravity, and the number of moons. Your job is to turn those raw text files into rich Planet objects, then craft a Java program that helps travelers choose their perfect cosmic getaway.

In this multi-part task you’ll read CSV data describing planets, automatically instantiate Planet objects, and design supporting classes such as Traveler and Itinerary. You’ll finish by adding your own creative twist: maybe a quirky recommendation engine, an ASCII star-map, or a mini-game that awards badges for visiting exotic worlds. Besides being fun, this mirrors real-world systems that transform external data (think JSON from web APIs or sensor logs) into live software objects.

## 🎯 Learning Objectives
By completing this task, you will:
- Read structured data from files and safely instantiate Java objects from that data.
- Design cohesive, well-encapsulated classes (e.g., Planet, Traveler, Itinerary) with clear interfaces.
- Apply creative problem-solving to add an original feature to your program.
- Handle common file I/O exceptions and validate incoming data for integrity.
- Practice incremental planning: from conceptual model to class diagram to working code.

## 📚 Prerequisites
Before starting this task, you should be familiar with:
- Basic Java syntax (variables, methods, loops, conditionals).
- Fundamentals of object-oriented programming (classes, objects, constructors).
- Simple file I/O using java.io or java.nio (e.g., File, Scanner, Files).
- An IDE of your choice (IntelliJ, Eclipse, VS Code) and Git for version control (recommended).

## 🚀 Getting Started
1. Clone the starter repository (link in your course LMS).  
   It contains:
   • /data/planets_sample.csv – a tiny dataset to experiment with  
   • /src/template/ – empty class files with TODO markers  
2. Open the project in your IDE and run `PlanetReaderTest` to verify your environment.  
3. Skim `planets_sample.csv` so you understand the columns (name, distanceFromSunAU, gravity, avgTempC, moonCount).  
4. Read the Exercises below before writing any code—you’ll plan first.

## Exercises

### Exercise 1: Reading the Stars on Paper 📖
**⏱️ Estimated Time:** 20-30 minutes

**🎯 Goal:** Build conceptual understanding of how raw file data maps to object attributes and why validation matters.

**📝 Instructions:**
1. Open `planets_sample.csv` in a text editor. Describe in your own words what each column represents and the data type you would assign to it in Java.  
2. List at least three potential issues that could arise when reading this file (e.g., missing values, corrupted lines). For each, note a strategy to detect or handle it in code.  
3. Explain the difference between storing planet data in parallel `ArrayList`s (one list per attribute) versus creating a `Planet` class. Discuss pros and cons.  
4. Think about scalability: If the file grows to 1 million lines, what memory or performance considerations might influence your design choices?

**💡 Hints:**
<details>
<summary>Click for hint</summary>

Real-world CSV files are rarely perfect—astronomical instruments can glitch just like a cash register printing a funky receipt. Consider using `try { … } catch (NumberFormatException e)` blocks and default values to keep your program from crashing when it encounters an “oops” in space data.

</details>

**✅ Success Criteria:**
- [ ] Correctly identify Java data types for every CSV column.  
- [ ] Provide at least three realistic file-reading issues and mitigation strategies.  
- [ ] Clearly articulate benefits of object-oriented storage over parallel collections.  

### Exercise 2: Sketching the Cosmic Blueprint 🔍
**⏱️ Estimated Time:** 30-40 minutes

**🎯 Goal:** Translate conceptual insights into a concrete class design that will later guide your implementation.

**📝 Instructions:**
1. Draw (on paper or with a UML tool) a class diagram that includes at minimum `Planet`, `Traveler`, and `Itinerary`. Show fields, key methods, and relationships (e.g., “Itinerary has-a List<Planet>”).  
2. Annotate your diagram with visibility (+/-), data types, and any important method signatures.  
3. Write a short paragraph explaining how your classes respect the Single Responsibility Principle.  
4. Draft pseudocode (no more than 15 lines) for a `readPlanetsFromFile(String filename)` method that populates a `List<Planet>` from the CSV. Focus on structure—details can come later.

**💡 Hints:**
<details>
<summary>Click for hint</summary>

When in doubt, start simple: think of `Planet` as a data container, `Traveler` as a user profile, and `Itinerary` as a coordinator. Avoid circular dependencies. If two classes must talk frequently, consider an interface or helper class.

```java
public List<Planet> readPlanetsFromFile(String filename) throws IOException {
    // 1. Create empty list
    // 2. Open file with try-with-resources
    // 3. Loop through lines, skip header
    // 4. Parse and validate
    // 5. Instantiate Planet, add to list
    // 6. Return list
}
```

</details>

**✅ Success Criteria:**
- [ ] Diagram includes required classes with clear relationships.  
- [ ] Explanatory paragraph cites at least one design principle (e.g., SRP, Encapsulation).  
- [ ] Pseudocode captures all major steps of file reading without diving into full implementation.

Below is the continuation you requested—Exercises 3-6 plus the remaining sections, written in exactly the same format you started. Copy-and-paste straight into your hand-out or LMS.

---

### Exercise 3: From CSV to Object Factory 🏗️  
**⏱️ Estimated Time:** 45-60 minutes  

**🎯 Goal:** Implement the core `Planet` class and a `PlanetFactory` that turns validated CSV rows into fully-formed `Planet` objects. This is your first hands-on coding step after planning in Exercises 1-2.  

**📝 Instructions:**  
1. Open `/src/template/Planet.java`. Complete the TODOs so the class is immutable (all fields `private final`) and well-encapsulated.  
2. Create `/src/template/PlanetFactory.java` and implement a single public static method:

   ```java
   public static Optional<Planet> fromCsv(String csvLine, int lineNumber)
   ```
   The method should:  
   • Split the line by commas (`,`).  
   • Validate that you have exactly 5 columns.  
   • Convert each column to the correct Java type.  
   • If anything fails, log a warning with the line number and return `Optional.empty()`.  
   • On success, return `Optional.of(new Planet(...))`.  

3. Write a tiny driver in `PlanetReaderTest` that loops through `planets_sample.csv`, calls `PlanetFactory.fromCsv`, and prints each resulting object’s `toString()`.  

Expected console output snippet (values will vary):

```
Reading 10 records…
✓ Line 1   ➜ Planet{name='Mercury', distanceFromSunAU=0.39, gravity=3.7, avgTempC=167, moonCount=0}
✓ Line 2   ➜ Planet{name='Venus',   distanceFromSunAU=0.72, gravity=8.8, avgTempC=464, moonCount=0}
…
```

```java
// Starter code
public class Planet {
    // TODO: Declare private final fields

    // TODO: Public constructor with all fields

    // TODO: Getters only (no setters)

    @Override
    public String toString() {
        // TODO: Return nicely formatted summary
        return "";
    }
}
```

**💡 Hints:**  
<details>
<summary>Click for hint</summary>

• Use `Double.parseDouble` and `Integer.parseInt`.  
• `Optional<Planet>` is cleaner than returning `null`.  
• Inside `PlanetFactory`, don’t swallow exceptions—catch, log, then continue.  

```java
try {
    double gravity = Double.parseDouble(tokens[2]);
} catch (NumberFormatException nfe) {
    System.err.println("Line " + lineNumber + ": bad gravity → “" + tokens[2] + "”");
    return Optional.empty();
}
```

</details>

**✅ Success Criteria:**  
- [ ] `Planet` is immutable and passes provided unit tests.  
- [ ] `PlanetFactory.fromCsv` builds objects for all valid lines and skips/flags bad ones.  
- [ ] No uncaught exceptions when reading the sample file.  
- [ ] Code is cleanly formatted and documented.

---

### Exercise 4: Building the Traveler’s Itinerary 🔄  
**⏱️ Estimated Time:** 45-60 minutes  

**🎯 Goal:** Integrate newly created `Planet` objects with `Traveler` and `Itinerary` classes so a user can assemble a personalized travel plan.  

**📝 Instructions:**  
1. Complete `/src/template/Traveler.java` with fields `name`, `preferredGravityRange`, and `preferredTempRange`. Provide constructors, getters, and a method `boolean likes(Planet p)`.  
2. Finish `/src/template/Itinerary.java` so it stores a `Traveler` and a `List<Planet>`. Add:  
   • `void addPlanet(Planet p)` – only adds if `traveler.likes(p)` returns true.  
   • `double averageGravity()` – computes average gravity across selected planets.  
   • `String summary()` – returns a multi-line itinerary description.  
3. Update `PlanetReaderTest` or create a new class `DemoItinerary` that:  
   • Reads all planets with `PlanetFactory`.  
   • Prompts the user for their name, min/max gravity, and min/max temperature (use `Scanner`).  
   • Recommends planets that match and builds an `Itinerary`.  
   • Prints `itinerary.summary()` at the end.  

**💡 Hints:**  
<details>
<summary>Click for hint</summary>

• To avoid too much I/O code, hard-code Traveler preferences while testing, then swap in interactive `Scanner` input later.  
• Use `java.util.stream.Collectors` to filter planets quickly:

```java
List<Planet> matches = allPlanets.stream()
                                 .filter(traveler::likes)
                                 .collect(Collectors.toList());
```
</details>

**✅ Success Criteria:**  
- [ ] `Traveler` and `Itinerary` compile and meet unit tests.  
- [ ] Only planets fitting user preferences are added.  
- [ ] `summary()` displays at least planet names and overall averages.  
- [ ] Program handles the case “no matching planets” gracefully.

---

### Exercise 5: Catalog Commander & Robust File I/O 🚀  
**⏱️ Estimated Time:** 60-90 minutes  

**🎯 Goal:** Create a `CatalogCommander` class that loads planets from file at start-up, lets the user interactively save favorite itineraries to disk, and demonstrates advanced exception handling.  

**📝 Instructions:**  
1. Implement `/src/template/CatalogCommander.java` with a `main` method.  
2. On start-up, attempt to read planets from `planets.csv` **OR** (if the file is missing) from a remote URL supplied in the starter file. Store the resulting list.  
3. Present a text menu:  
   1. List all planets (paginated 10 at a time).  
   2. Create new traveler & itinerary (reuse Exercise 4 code).  
   3. Save current itinerary to `itineraries/NAME_itinerary.txt`.  
   4. Quit.  
4. Ensure every file operation (read or write) is wrapped in try-with-resources and all checked exceptions are handled or declared.  
5. Document key methods with Javadoc.  

Breakdown (recommended order):  
a. `loadCatalog()` – handles local file or fallback URL.  
b. `showMenu()` – simple loop with `switch`.  
c. `saveItinerary(Itinerary i)` – validates the output folder exists or creates it.  

**💡 Hints:**  
<details>
<summary>Click for hint</summary>

• To read from a URL:

```java
try (BufferedReader in = new BufferedReader(
        new InputStreamReader(new URL(urlString).openStream()))) {
    // read lines
}
```  

• For pagination, store an `int index` and print slices of 10.  
• Surround risky code:

```java
try {
    Files.createDirectories(Path.of("itineraries"));
} catch (IOException ioe) {
    System.err.println("🚨 Cannot create output folder: " + ioe.getMessage());
}
```
</details>

**✅ Success Criteria:**  
- [ ] Program recovers if local file missing and downloads from URL.  
- [ ] All file and network resources are properly closed.  
- [ ] User can save itineraries; files show correct content.  
- [ ] Javadoc present for every public class/method added.  

---

### Exercise 6: Choose-Your-Own-Galactic-Adventure 🌟  
**⏱️ Estimated Time:** 60-90 minutes  

**🎯 Goal:** Add an original, creative feature that extends your project beyond the spec while showcasing clean design and documentation. Pick ONE of the options below or propose your own (check with your instructor).  

**📝 Instructions:**  

Option A: ASCII Star-Map Renderer  
- Display a simple ASCII grid where each discovered planet is plotted by polar coordinates (distance vs. temperature).  
- Allow users to “zoom” by changing scale factors.  

Option B: Achievement Badge System  
- Award badges (e.g., “Moon Collector,” “Low-G Lover”) based on patterns in a traveler’s itinerary.  
- Store earned badges in a JSON or text file so they persist between runs.  

Option C: Smart Recommendation Engine  
- Implement a rule-based or simple weighted-score algorithm that ranks planets for a traveler instead of boolean likes/dislikes.  
- Display the top-5 suggestions with explanations of the scoring.  

All options must include at least:  
• One new class (or interface) designed by you.  
• Javadoc for all public members.  
• Unit tests (use JUnit) covering the new feature.  

**💡 Hints:**  
<details>
<summary>Click for hint</summary>

• Keep it small but polished—depth over breadth.  
• Isolate the creative code in its own package (`adventure`, `badges`, or `recommendation`).  
• Reuse data already parsed; do NOT re-read the CSV file.  

```java
/**
 * Returns a List of Badge objects the traveler just earned.
 * @param itinerary the completed itinerary
 */
public List<Badge> evaluate(Itinerary itinerary) {
    // Your creative logic here
}
```
</details>

**✅ Success Criteria:**  
- [ ] Feature is functional and demo-ready.  
- [ ] Clear, correct Javadoc and meaningful unit tests.  
- [ ] Code follows Java naming and style conventions.  
- [ ] Reflects thoughtful class design (SRP, low coupling).  

---

## 🎉 Submission Checklist  

Before submitting, ensure you have:  

- [ ] Completed all 6 exercises.  
- [ ] All unit tests pass (`mvn test` or IDE equivalent).  
- [ ] No TODO markers remain in source code.  
- [ ] Used `try-with-resources` for every file/stream.  
- [ ] Added Javadoc to every public class, method, and field.  
- [ ] Included a UML or class diagram (`/docs/diagram.png` or `.uml`).  
- [ ] Pushed final commit and tagged it `v1.0` in Git.  
- [ ] Provided a short `README.md` with build/run instructions.  
- [ ] Verified that `java -jar GalacticTravelLog.jar` launches without errors.  

---

## 🤔 Reflection Questions  

1. How did using `Optional` in the factory pattern influence the robustness of your file-reading code compared to returning `null`?  
2. Describe one design decision you changed after unit testing began. What triggered the change, and how did it improve your code?  
3. Imagine scaling this program for a commercial booking site with real-time data feeds. Which part of your current architecture would need the most refactoring, and why?  

---

## 📚 Additional Resources  

• Oracle Java I/O Tutorial – https://docs.oracle.com/javase/tutorial/essential/io/  
• Fowler, “Clean Code Applied to Java” (blog post) – great read on SRP and small classes.  
• NASA Exoplanet Archive – https://exoplanetarchive.ipac.caltech.edu/  
• ASCII Art & Plotting Basics – https://gist.github.com/unsplash/ ASCII cheat-sheet.  
• JUnit 5 User Guide – https://junit.org/junit5/docs/current/user-guide/  

Happy coding, and may your itineraries be ever adventurous! 🚀