package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StrategyLoaderTest {

    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Test
    public void missingFileReturnsDefaultConfig() {
        String improbableName = "definitely_not_existing_" + System.nanoTime() + ".csv";
        StrategyConfig cfg = StrategyLoader.loadConfig(improbableName);

        // default values must be present
        assertEquals(1.25, cfg.get("softTyreWearRate"), 0.0001);
        assertEquals(0.85, cfg.get("hardTyreWearRate"), 0.0001);
        assertEquals(22.5, cfg.get("basePitLossSeconds"), 0.0001);
        assertEquals(0.4, cfg.get("blowoutRiskThreshold"), 0.0001);
    }

    @Test
    public void fileValuesOverrideDefaults() throws Exception {
        File file = tmp.newFile();
        try (FileWriter w = new FileWriter(file)) {
            w.write("basePitLossSeconds,30.0\n");
            w.write("blowoutRiskThreshold,0.1\n");
        }

        StrategyConfig cfg = StrategyLoader.loadConfig(file.getAbsolutePath());
        assertEquals(30.0, cfg.get("basePitLossSeconds"), 0.0001);
        assertEquals(0.1, cfg.get("blowoutRiskThreshold"), 0.0001);

        // unchanged defaults remain
        assertEquals(1.25, cfg.get("softTyreWearRate"), 0.0001);
    }
}



// RaceSimulatorIntegrationTest.java
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import static org.junit.Assert.*;

