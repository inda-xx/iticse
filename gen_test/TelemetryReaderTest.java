package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TelemetryReaderTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    private File createFile(String contents) throws Exception {
        File f = tmp.newFile();
        try (FileWriter w = new FileWriter(f)) {
            w.write(contents);
        }
        return f;
    }

    @Test
    public void nextLapReturnsCorrectTelemetryData() throws Exception {
        File file = createFile("1,100.0,25.0,90.5\n");
        TelemetryReader reader = new TelemetryReader(file.getAbsolutePath());

        Optional<TelemetryData> opt = reader.nextLap();
        assertTrue(opt.isPresent());

        TelemetryData d = opt.get();
        assertEquals(1, d.getLapNumber());
        assertEquals(100.0, d.getTireTempCelsius(), 0.0001);
        assertEquals(25.0, d.getTrackTempCelsius(), 0.0001);
        assertEquals(90.5, d.getLapTimeSeconds(), 0.0001);

        assertTrue(reader.nextLap().isEmpty());              // EOF reached
        reader.close();
    }

    @Test(expected = java.io.IOException.class)
    public void malformedLineThrowsIOException() throws Exception {
        File file = createFile("bad,line,only,three\n");     // requires 4 numeric fields
        TelemetryReader reader = new TelemetryReader(file.getAbsolutePath());
        reader.nextLap();                                    // should throw
    }
}



// StrategyLoaderTest.java
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;

import static org.junit.Assert.*;

