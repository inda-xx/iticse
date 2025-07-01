package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlanetFactoryTest {

    @Test
    public void validCsvParsesCorrectly() {
        String line = "Mars , 1.52 , 3.71 , -63 , 2";
        Optional<Planet> opt = PlanetFactory.fromCsv(line, 1);
        assertTrue(opt.isPresent());
        Planet mars = opt.get();
        assertEquals("Mars", mars.getName());
        assertEquals(1.52, mars.getDistanceFromSunAU(), 1e-10);
        assertEquals(3.71, mars.getGravity(), 1e-10);
        assertEquals(-63.0, mars.getAvgTempC(), 1e-10);
        assertEquals(2, mars.getMoonCount());
    }

    @Test
    public void incorrectFieldCountReturnsEmpty() {
        String bad = "Mercury,0.39,3.7,167"; // only 4 fields
        assertFalse(PlanetFactory.fromCsv(bad, 2).isPresent());
    }

    @Test
    public void invalidNumericReturnsEmpty() {
        String bad = "Venus,0.72,not-a-number,464,0";
        assertFalse(PlanetFactory.fromCsv(bad, 3).isPresent());
    }
}

/**
 * Tests for Tire
 */
