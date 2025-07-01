import java.util.List;

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