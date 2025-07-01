import java.util.List;
import java.util.ArrayList;

public class StrategyEngine {
    private Tire currentTire;
    private final List<Stint> stints = new ArrayList<>();

    public List<LapData> readTelemetry(String fileName) throws IOException {
        List<LapData> laps = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));
             PrintWriter errorLog = new PrintWriter(new FileWriter("badLines.log"))) {
            String line;
            boolean skipHeader = true;
            while ((line = br.readLine()) != null) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }
                try {
                    LapData lap = LapData.parse(line);
                    laps.add(lap);
                } catch (CorruptLapException e) {
                    errorLog.println("Skipping: " + line);
                }
            }
        }
        return laps;
    }

    public void runRace(List<LapData> laps) throws IOException {
        Stint currentStint = null;

        for (LapData lap : laps) {
            if (currentTire == null) {
                currentTire = new Tire(lap.getCompound());
                currentStint = new Stint(lap.getLapNumber(), currentTire.getCompound());
            }

            currentTire.updateFromLap(lap);
            currentStint.update(lap, currentTire.getLifeLeftPercent());

            boolean pit = decidePitStop(currentTire, lap);

            System.out.printf("Lap %d | %.0f °C | %.0f %% life – %s%n",
                    lap.getLapNumber(),
                    currentTire.getTemperature(),
                    currentTire.getLifeLeftPercent(),
                    pit ? "BOX, BOX, BOX!" : "keep pushing!");

            if (pit) {
                stints.add(currentStint);
                currentTire = new Tire(lap.getCompound());
                currentTire.temperature = Math.max(80, currentTire.getTemperature() - 10);
                currentStint = new Stint(lap.getLapNumber(), currentTire.getCompound());
            }
        }

        stints.add(currentStint); // Add the final stint
        printRaceSummary();
    }

    private boolean decidePitStop(Tire tire, LapData lap) {
        return tire.getLifeLeftPercent() < 25 || tire.getTemperature() > 105;
    }

    private void printRaceSummary() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter("raceSummary.txt"))) {
            String header = String.format("%-6s | %-4s | %-8s | %-9s | %-9s", "Stint", "Laps", "Compound", "Avg Temp", "Avg Wear");
            String divider = "-----------------------------------------------";
            System.out.println("\n" + header);
            System.out.println(divider);
            writer.println(header);
            writer.println(divider);

            int stintNumber = 1;
            for (Stint stint : stints) {
                String row = String.format("%-6d | %-4d | %-8s | %-9.0f | %-9.0f",
                        stintNumber++,
                        stint.getLapsRun(),
                        stint.getCompound(),
                        stint.getAverageTemperature(),
                        stint.getAverageWear());
                System.out.println(row);
                writer.println(row);
            }
        }
    }
}