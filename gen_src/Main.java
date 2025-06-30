// Main.java
public class Main {
    public static void main(String[] args) {
        // entry point: set up reader, config, engineer, tires, car, simulator
    }
}

// TelemetryReader.java
public class TelemetryReader {
    public TelemetryReader(String filePath) {
        // initialize reader with file path
    }
    // methods to read telemetry data
}

// StrategyLoader.java
public class StrategyLoader {
    public static StrategyConfig loadConfig(String filePath) {
        // load and return strategy configuration
        return null;
    }
}

// StrategyConfig.java
public class StrategyConfig {
    // strategy configuration fields and constructors
}

// RaceEngineer.java
public class RaceEngineer {
    public RaceEngineer(StrategyConfig config) {
        // initialize engineer with strategy
    }
    // methods to decide pit stops and tire changes
}

// Tire.java
public class Tire {
    public Tire(double initialTemperature) {
        // set initial tire state
    }
    // tire properties and methods
}

// Car.java
public class Car {
    public Car(Tire startingTire) {
        // initialize car with starting tire
    }
    // methods to update car state during simulation
}

// RaceSimulator.java
public class RaceSimulator {
    public RaceSimulator(TelemetryReader reader, Car car, RaceEngineer engineer) {
        // initialize simulator components
    }
    public void run() {
        // execute the simulation loop
    }
}