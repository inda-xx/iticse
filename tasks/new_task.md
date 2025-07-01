# Pit Stop Panic 🏁

## 📋 Overview
Imagine you’re the race engineer for a top Formula 1 team. Your driver is pushing hard, but their lap times start to drop—rubber is wearing thin, temperatures are soaring, and one wrong call could make a tire explode at 300 km/h. Modern F1 teams rely on real-time data and predictive models to decide the perfect moment to “box” (pit) for fresh tires. In this mini-project, you’ll build a simplified tire-management simulator that reads live-style telemetry from a file, instantiates Tire objects, and decides when to pit before disaster strikes.

You will design a set of classes that model tires, stints, and strategy logic. File I/O will feed your program lap-by-lap data such as compound type, temperature, and degradation. Your code must creatively balance safety and speed: pit too early and you lose track position; pit too late and sparks—and maybe tires—fly! By the end, you’ll have a console application that shouts “BOX, BOX, BOX!” at precisely the right moment, mirroring high-pressure decisions made on the world’s fastest tracks.

## 🎯 Learning Objectives
By completing this task, you will:
- Parse structured text files and use the data to instantiate and update Tire objects in Java.
- Apply solid class-design principles (responsibility, cohesion, and clear interfaces) to model real-world racing entities.
- Experiment creatively with algorithms that predict tire failure and suggest pit stops.
- Practice exception handling to keep your simulator running even when the telemetry feed has missing or corrupted lines.
- Produce readable console output that mimics live race-engineer radio calls.

## 📚 Prerequisites
Before starting this task, you should be familiar with:
- Basic Java syntax, including classes, objects, and methods.
- Java File I/O using java.nio or java.io packages.
- Exception handling with try-catch blocks.
- An IDE such as IntelliJ IDEA or Eclipse (or a text editor plus command-line JDK).

## 🚀 Getting Started
1. Download the starter repository (link provided by your instructor) which contains:
   • sampleTelemetry.txt – a small dataset of lap information  
   • Main.java – a scaffold with a `main` method and TODO markers  
   • empty classes Tire.java and StrategyEngine.java  
2. Import the project into your IDE and run Main to verify it compiles (it won’t do much yet).  
3. Read through sampleTelemetry.txt to understand the data format you’ll be parsing in later exercises.  

## Exercises

### Exercise 1: Reading the Track 📖
**⏱️ Estimated Time:** 20-30 minutes  

**🎯 Goal:** Build conceptual understanding of telemetry file formats and the data required to model tire wear.

**📝 Instructions:**
1. Open sampleTelemetry.txt and identify at least four distinct data fields recorded for each lap.  
2. For each field, answer:  
   a. What Java data type (int, double, String, etc.) best represents it?  
   b. How might this value influence tire degradation or pit-stop strategy?  
3. Describe in 2-3 sentences why CSV was chosen instead of JSON for this dataset. Consider parsing complexity and real-time logging.  
4. List two potential data-quality issues (e.g., missing lines, extra commas) and explain how your code should respond without crashing mid-race.

**💡 Hints:**
<details>
<summary>Click for hint</summary>

Think of telemetry like a heart monitor: if one beat is skipped, doctors don’t quit—they estimate or ignore that blip and keep treating the patient. Your program should do the same for occasional corrupt lines.

</details>

**✅ Success Criteria:**
- [ ] Correctly list four lap data fields with appropriate Java types.  
- [ ] Provide a clear rationale for using CSV over JSON.  
- [ ] Identify two data-quality issues with sensible handling strategies.  

### Exercise 2: Designing the Pit Crew 🔍
**⏱️ Estimated Time:** 30-40 minutes  

**🎯 Goal:** Bridge theory to practice by planning the class structure that will drive the simulator.

**📝 Instructions:**
1. Sketch (on paper or using a UML tool) a class diagram that includes at least: Tire, Stint, StrategyEngine, and Main. Indicate relationships (e.g., StrategyEngine “has-a” Tire).  
2. Define the key responsibilities and public methods for each class. Focus on:  
   • Tire – tracking compound, life left, temperature  
   • StrategyEngine – reading telemetry, updating tires, deciding when to pit  
3. Write pseudocode (5-10 lines) for StrategyEngine.decidePitStop(). Include the main conditional logic that would trigger a pit call.  
4. Paste or attach a photo of your diagram (if required by your instructor), and include your pseudocode in your submission.

**💡 Hints:**
<details>
<summary>Click for hint</summary>

Aim for single responsibility: a Tire shouldn’t know how to read files, and StrategyEngine shouldn’t micromanage individual temperature calculations. Delegate wisely!

```java
// Very high-level sketch
public boolean decidePitStop(LapData lap) {
    updateTireWear(lap);
    if(tire.getRemainingPercent() < CRITICAL || lap.getTemp() > OVERHEAT) {
        return true; // BOX!
    }
    return false;
}
```

</details>

**✅ Success Criteria:**
- [ ] Class diagram shows correct relationships and at least four classes.  
- [ ] Method responsibilities are well-described and respect single-responsibility principle.  
- [ ] Pseudocode presents logical, readable steps for pit-stop decision making.

### Exercise 3: Building the Tire & LapData Core 🏗️  
⏱️ Estimated Time: 45-60 minutes  

🎯 Goal: Implement the foundational classes that will store lap information and track tire wear in real-time. By the end of this exercise you will 1) parse one line of telemetry, 2) create a Tire object, and 3) update its wear and temperature every lap.

📝 Instructions:  
1. Inside Tire.java, add private fields you identified in Exercise 2 (compound, lifeLeftPercent, temperature, lapsCompleted, etc.).  
2. Create an immutable helper class LapData that represents ONE row of the CSV. Give it appropriate fields (lapNumber, rawTemp, rawWear, …) and only getters.  
3. In Tire.java, write:  
   • A constructor that takes a compound String (e.g., "Soft") and starting life (100 %).  
   • updateFromLap(LapData lap) – reduce lifeLeftPercent and adjust temperature based on lap data. (Exact formula is up to you; keep it simple for now.)  
4. Open Main.java. Locate the TODO block labelled “STEP 1 – quick parser test”. Add code that:  
   • Reads the first line of sampleTelemetry.txt (skip header).  
   • Splits by comma and constructs a LapData object.  
   • Instantiates a Tire with compound = lap.getCompound().  
   • Calls tire.updateFromLap(lap) and prints something like:  
     Soft tire @ 97 % life, 94 °C after Lap 3  
5. Run Main. You should see a single-line summary with realistic numbers.

```java
// Tire.java (scaffold)
public class Tire {
    // TODO: Fields: compound, lifeLeftPercent, temperature, lapsCompleted
    
    // TODO: Constructor
    
    // TODO: public void updateFromLap(LapData lap) { /* logic here */ }
    
    // TODO: Getters such as getRemainingPercent()
}
```

💡 Hints:  
<details>  
<summary>Click for hint</summary>  

CSV order in sampleTelemetry.txt is:  
lap,compound,temp,degradation,stintId  
Temperature usually drifts ±2 °C per lap; degradation might be 0.8–1.2 % for Softs and ~0.5 % for Hards—feel free to embed simple multipliers.

```java
String[] parts = line.split(",");
int lapNo = Integer.parseInt(parts[0]);
double temp = Double.parseDouble(parts[2]);
double wear = Double.parseDouble(parts[3]);
```

</details>

✅ Success Criteria:  
- [ ] Tire.java compiles with clearly-named fields and methods.  
- [ ] LapData objects correctly encapsulate one CSV row (no setters!).  
- [ ] Main prints a human-readable summary after processing exactly one lap.  
- [ ] No unchecked exceptions when parsing the sample file.



### Exercise 4: Wiring Up the Strategy Engine 🔄  
⏱️ Estimated Time: 45-60 minutes  

🎯 Goal: Connect file parsing, Tire updates, and pit-stop logic inside StrategyEngine so the simulator runs from lights to flag.

📝 Instructions:  
1. In StrategyEngine.java add:  
   • readTelemetry(String fileName) – loops through the whole CSV, returns a List<LapData>.  
   • runRace(List<LapData> laps) – iterates over laps, updates the Tire, and decides whether to pit on each lap.  
2. Implement decidePitStop(Tire tire, LapData lap) placeholder from Exercise 2. At minimum, return true when:  
   • tire.getRemainingPercent() < 25 OR  
   • tire.getTemperature() > 105 °C  
3. Update Main.java “STEP 2 – full race sim” block:  
   • StrategyEngine se = new StrategyEngine();  
   • List<LapData> laps = se.readTelemetry("sampleTelemetry.txt");  
   • se.runRace(laps);  
4. During runRace, print lap-by-lap updates:  
   Lap 12 | 84 °C | 72 % life – keep pushing!  
   Lap 18 | 107 °C | 24 % life – BOX, BOX, BOX!  
5. After pitting, reset the Tire to 100 % life and drop temperature slightly (e.g., −10 °C).

```java
// StrategyEngine.java (snippet)
public class StrategyEngine {
    private Tire currentTire;
    
    public List<LapData> readTelemetry(String fileName) throws IOException {
        // TODO: Parse entire file and fill List<LapData>
    }
    
    public void runRace(List<LapData> laps) {
        // TODO: Loop through laps, update tire, call decidePitStop()
    }

    private boolean decidePitStop(Tire tire, LapData lap) {
        // TODO: Implement logic outlined in instructions
        return false;
    }
}
```

💡 Hints:  
<details>  
<summary>Click for hint</summary>  

Reuse the quick-parse logic from Exercise 3. Remember to skip the CSV header once and to trim() each field. Consider java.nio.file.Files.lines for a compact stream-based reader.

</details>

✅ Success Criteria:  
- [ ] StrategyEngine reads every line of the file into LapData objects.  
- [ ] runRace prints clear console updates for each lap.  
- [ ] decidePitStop triggers a pit exactly when the critical thresholds are crossed.  
- [ ] After pitting, tire stats correctly reset and continue updating.



### Exercise 5: Fail-Safe File I/O & Race Summary 🚀  
⏱️ Estimated Time: 60-90 minutes  

🎯 Goal: Harden your simulator with robust error handling, logging, and an end-of-race report that summarises stints, total pit stops, and average tire life.

📝 Instructions:  
1. Wrap readTelemetry in try-with-resources; catch malformed lines and log them to a file badLines.log without aborting the race.  
2. Add a custom checked exception: CorruptLapException. Throw it when a line is missing mandatory fields or has impossible values (e.g., temp < 0).  
3. Enhance StrategyEngine to store a List<Stint>. Each Stint records: startingLap, endingLap, compound, lapsRun, avgTemp, avgWear.  
4. At the end of runRace, output a table like:  

   Stint | Laps | Compound | Avg Temp | Avg Wear  
   ------------------------------------------------  
   1     | 18   | Soft     | 96 °C    | 38 %  
   2     | 19   | Medium   | 94 °C    | 41 %  

5. Write these results to raceSummary.txt as well as the console.  
6. Verify the program still runs if you intentionally add a blank line or “@@@” into sampleTelemetry.txt.

```java
// Example file-reading pattern with logging
try (BufferedReader br = new BufferedReader(new FileReader(fileName));
     PrintWriter errorLog = new PrintWriter(new FileWriter("badLines.log"))) {
    String line;
    while ((line = br.readLine()) != null) {
        try {
            LapData lap = LapData.parse(line); // static factory you add
            laps.add(lap);
        } catch (CorruptLapException e) {
            errorLog.println("Skipping: " + line);
        }
    }
}
```

💡 Hints:  
<details>  
<summary>Click for hint</summary>  

Make LapData.parse(String csvLine) return an Optional<LapData> if you prefer avoiding checked exceptions. For the summary, String.format or printf will keep your columns tidy.

</details>

✅ Success Criteria:  
- [ ] Simulator continues despite corrupt or missing lines.  
- [ ] badLines.log contains every ignored line with no duplicates.  
- [ ] raceSummary.txt lists each stint with accurate aggregated metrics.  
- [ ] Code uses try-with-resources and follows Java naming conventions.



### Exercise 6: Beyond the Checkered Flag 🌟  
⏱️ Estimated Time: 60-90 minutes  

🎯 Goal: Add a creative feature of your choice, demonstrating mastery of class design, file I/O, and inventive problem-solving.

📝 Instructions: Choose ONE of the following paths (or propose your own with instructor approval).

Option A: “Weather Wizard”  
- Introduce a Weather class that reads weatherForecast.txt (lap, airTemp, rainChance).  
- Modify StrategyEngine to soften pit thresholds when rainChance > 50 %.  
- Print “Inter weather may be coming—extend this stint!” when appropriate.

Option B: “GUI Pit Wall”  
- Use Java Swing to display a live table of lap data and a blinking “BOX” indicator.  
- Update the table every time runRace processes a lap (SwingUtilities.invokeLater).  
- Add a “Pause Simulation” button that sleeps the race thread.

Option C: “Tyre-Wear Predictor”  
- Train (very simply) on the first 10 laps to fit a linear regression wearRate = m*temp + b.  
- Predict remaining life after each lap and print “Projected life: 7 laps”.  
- Save the model parameters m and b to model.txt after the race.

All options must include Javadoc for every public class and method.

💡 Hints:  
<details>  
<summary>Click for hint</summary>  

Keep scope reasonable—focus on ONE fun feature rather than half-implementing several. Remember to separate concerns: GUI in its own package, machine-learning logic in Predictor.java, etc.

```java
/**
 * Predicts remaining tyre life using a simple linear model.
 * y = m*x + b where x is current temperature.
 */
public class WearPredictor {
    // TODO: fields m and b
    // TODO: train(List<LapData> trainingLaps)
    // TODO: double estimateRemainingLife(Tire tire)
}
```

</details>

✅ Success Criteria:  
- [ ] Delivers fully working Option A, B, C, or approved custom idea.  
- [ ] Includes complete Javadoc and inline comments for complex sections.  
- [ ] Demonstrates thoughtful class design (SRP, low coupling).  
- [ ] Code compiles, runs, and integrates with earlier exercises without breaking tests.



(You can now continue with the Submission Checklist, Reflection Questions, and Additional Resources sections already outlined.)