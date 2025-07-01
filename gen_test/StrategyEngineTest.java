package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StrategyEngineTest {

    private Path telemetryFile;
    private Path workingDir;

    @Before
    public void setUp() throws IOException {
        workingDir = Files.createTempDirectory("strategyTest");
        telemetryFile = workingDir.resolve("telemetry.csv");
    }

    @After
    public void tearDown() throws IOException {
        Files.walk(workingDir)
             .map(Path::toFile)
             .forEach(File::delete);
        new File("badLines.log").delete();
        new File("raceSummary.txt").delete();
    }

    @Test
    public void readTelemetryParsesValidLinesAndSkipsCorrupt() throws Exception {
        String header = "lap,compound,temperature,degradation,stintId\n";
        String good1 = "1,medium,95,5,0\n";
        String good2 = "2,medium,96,5,0\n";
        String bad = "bad,line,that,is,broken\n";

        Files.write(telemetryFile, (header + good1 + bad + good2).getBytes());

        StrategyEngine engine = new StrategyEngine();
        List<LapData> laps = engine.readTelemetry(telemetryFile.toString());

        assertEquals(2, laps.size());
        assertEquals(1, laps.get(0).getLapNumber());
        assertEquals(2, laps.get(1).getLapNumber());

        File log = new File("badLines.log");
        assertTrue(log.exists());
        String logContent = new String(Files.readAllBytes(log.toPath()));
        assertTrue(logContent.contains("Skipping: " + bad.trim()));
    }

    @Test
    public void runRaceCreatesExpectedNumberOfStints() throws Exception {
        List<LapData> laps = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            laps.add(new LapData(i, "medium", 100, 10, 0));
        }

        StrategyEngine engine = new StrategyEngine();
        engine.runRace(laps);

        Field stintsField = StrategyEngine.class.getDeclaredField("stints");
        stintsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Stint> stints = (List<Stint>) stintsField.get(engine);

        assertEquals(2, stints.size());

        Stint first = stints.get(0);
        Stint second = stints.get(1);

        assertEquals(8, first.getLapsRun());
        assertEquals(2, second.getLapsRun());

        File summary = new File("raceSummary.txt");
        assertTrue(summary.exists());
    }
}