package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TelemetryReaderTest {

    @Test
    public void readsTelemetryLinesCorrectly() throws Exception {
        Path tmp = Files.createTempFile("telemetry", ".csv");
        List<String> lines = Arrays.asList(
                "1,90.0,30.0,85.5",
                "2,95.0,32.0,85.0"
        );
        Files.write(tmp, lines);

        TelemetryReader reader = new TelemetryReader(tmp.toString());

        Optional<TelemetryData> d1 = reader.nextLap();
        assertTrue(d1.isPresent());
        assertEquals(1, d1.get().getLapNumber());
        assertEquals(90.0, d1.get().getTireTempCelsius(), 1e-10);

        Optional<TelemetryData> d2 = reader.nextLap();
        assertTrue(d2.isPresent());
        assertEquals(2, d2.get().getLapNumber());

        Optional<TelemetryData> d3 = reader.nextLap();
        assertFalse(d3.isPresent());

        reader.close();
        Files.deleteIfExists(tmp);
    }

    @Test(expected = IOException.class)
    public void throwsIOExceptionOnMalformedLine() throws Exception {
        Path tmp = Files.createTempFile("telemetry_bad", ".csv");
        List<String> lines = Arrays.asList(
                "1,90.0,30.0,85.5",
                "bad,data,line"
        );
        Files.write(tmp, lines);

        TelemetryReader reader = new TelemetryReader(tmp.toString());
        reader.nextLap(); // first ok
        reader.nextLap(); // malformed triggers IOException
    }
}