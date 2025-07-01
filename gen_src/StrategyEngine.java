import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class StrategyEngine {
    private Tire currentTire;
    private final List<Stint> stints = new ArrayList<>();

    public List<LapData> readTelemetry(String fileName) throws IOException {
        // TODO: read and parse telemetry data
        return null;
    }

    public void runRace(List<LapData> laps) throws IOException {
        // TODO: process laps and manage stints
    }

    private boolean decidePitStop(Tire tire, LapData lap) {
        // TODO: determine if a pit stop is needed
        return false;
    }

    private void printRaceSummary() throws IOException {
        // TODO: output race summary
    }
}