package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RaceSimulatorIntegrationTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Test
    public void simulatorRunsThroughAllLapsAndPrintsStatus() throws Exception {
        // Prepare a tiny telemetry file (3 laps)
        File file = tmp.newFile();
        try (FileWriter w = new FileWriter(file)) {
            w.write("1,90.0,25.0,90.0\n");
            w.write("2,110.0,25.0,90.0\n");
            w.write("3,120.0,25.0,90.0\n");
        }

        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            TelemetryReader reader = new TelemetryReader(file.getAbsolutePath());
            StrategyConfig cfg = new StrategyConfig();           // defaults are fine
            RaceEngineer engineer = new RaceEngineer(cfg);
            Car car = new Car(new Tire(90.0));

            RaceSimulator sim = new RaceSimulator(reader, car, engineer);
            sim.run();                                           // should terminate normally
        } finally {
            System.setOut(original);
        }

        String output = outContent.toString();
        assertTrue(output.contains("Lap 1"));
        assertTrue(output.contains("Lap 3"));
        assertTrue(output.contains("Race Ended"));
    }
}