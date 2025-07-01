package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TireTest {

    @Test
    public void initialValuesAreSet() {
        Tire tire = new Tire(85.0);
        assertEquals(85.0, tire.getTemperature(), 0.0);
        assertEquals(0.0, tire.getWearPercentage(), 0.0);
        assertFalse(tire.isBlown());
    }

    @Test
    public void incrementWearCapsAtHundred() {
        Tire tire = new Tire(90.0);
        tire.incrementWear(60.0);
        tire.incrementWear(50.0); // total 110
        assertEquals(100.0, tire.getWearPercentage(), 0.0);
        assertTrue(tire.isBlown());
    }

    @Test
    public void blowoutRiskRespectsFormula() {
        Tire tire = new Tire(90.0);
        tire.incrementWear(50.0); // wear = 50
        double expected = Math.pow(0.5, 3); // 0.125
        assertEquals(expected, tire.blowoutRisk(), 1e-6);

        tire.updateTemperature(120.0); // > 110 adds 0.1
        assertEquals(Math.min(expected + 0.1, 1.0), tire.blowoutRisk(), 1e-6);

        tire.incrementWear(50.0); // wear = 100
        assertEquals(1.0, tire.blowoutRisk(), 0.0);
    }
}

/**
 * Tests for Car
 */
