package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StrategyLoaderTest {

    @Test
    public void loadsCustomValuesFromFile() throws IOException {
        Path tempFile = Files.createTempFile("strategy", ".csv");
        Files.write(tempFile, Arrays.asList(
                "basePitLossSeconds,30.0",
                "blowoutRiskThreshold,0.75"
        ));

        StrategyConfig config = StrategyLoader.loadConfig(tempFile.toString());
        assertEquals(30.0, config.get("basePitLossSeconds"), 1e-10);
        assertEquals(0.75, config.get("blowoutRiskThreshold"), 1e-10);

        Files.deleteIfExists(tempFile);
    }

    @Test
    public void usesDefaultsWhenFileMissing() {
        String missingFile = "non_existent_strategy_file.csv";
        StrategyConfig config = StrategyLoader.loadConfig(missingFile);
        // default values as per loadDefaults()
        assertEquals(22.5, config.get("basePitLossSeconds"), 1e-10);
        assertEquals(0.4, config.get("blowoutRiskThreshold"), 1e-10);
        assertEquals(1.25, config.get("softTyreWearRate"), 1e-10);
        assertEquals(0.85, config.get("hardTyreWearRate"), 1e-10);
    }
}

/**
 * Tests for RaceEngineer
 */
