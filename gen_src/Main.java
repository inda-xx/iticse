// File: Main.java
import java.util.List;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        StrategyEngine strategyEngine = new StrategyEngine();

        // STEP 1 – quick parser test
        try {
            List<LapData> laps = strategyEngine.readTelemetry("sampleTelemetry.txt");
            if (!laps.isEmpty()) {
                LapData lap = laps.get(0);
                Tire tire = new Tire(lap.getCompound());
                tire.updateFromLap(lap);
                System.out.printf("%s tire @ %.0f %% life, %.0f °C after Lap %d%n",
                                  tire.getCompound(),
                                  tire.getLifeLeftPercent(),
                                  tire.getTemperature(),
                                  lap.getLapNumber());
            }
        } catch (IOException e) {
            System.err.println("Error reading telemetry file: " + e.getMessage());
        }

        // STEP 2 – full race sim
        try {
            List<LapData> laps = strategyEngine.readTelemetry("sampleTelemetry.txt");
            strategyEngine.runRace(laps);
        } catch (IOException e) {
            System.err.println("Runtime error during race: " + e.getMessage());
        }
    }
}

// File: StrategyEngine.java
import java.util.List;
import java.io.IOException;

public class StrategyEngine {
    public List<LapData> readTelemetry(String filename) throws IOException {
        // TODO: parse telemetry file into list of LapData
        return null;
    }

    public void runRace(List<LapData> laps) {
        // TODO: simulate race using lap data
    }
}

// File: LapData.java
public class LapData {
    public int getLapNumber() {
        // TODO: return lap number
        return 0;
    }

    public String getCompound() {
        // TODO: return tire compound used this lap
        return null;
    }
}

// File: Tire.java
public class Tire {
    public Tire(String compound) {
        // TODO: initialize tire with given compound
    }

    public void updateFromLap(LapData lapData) {
        // TODO: update tire state based on lap data
    }

    public double getLifeLeftPercent() {
        // TODO: compute and return remaining life percentage
        return 0.0;
    }

    public double getTemperature() {
        // TODO: return current tire temperature
        return 0.0;
    }

    public String getCompound() {
        // TODO: return this tire's compound
        return null;
    }
}