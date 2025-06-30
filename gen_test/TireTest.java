package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TireTest {

    @Test
    public void temperatureUpdateChangesTemperature() {
        Tire tire = new Tire(90.0);
        tire.updateTemperature(95.0);
        assertEquals(95.0, tire.getTemperature(), 0.0001);
    }

    @Test
    public void incrementWearDoesNotExceedHundred() {
        Tire tire = new Tire(80.0);
        tire.incrementWear(150.0);               // deliberately excessive wear
        assertEquals(100.0, tire.getWearPercentage(), 0.0001);
    }

    @Test
    public void blowoutRiskIsCappedAtOneEvenInExtremeConditions() {
        Tire tire = new Tire(150.0);             // extreme temperature
        tire.incrementWear(200.0);               // ensures wear is 100 %
        double risk = tire.blowoutRisk();
        assertEquals(1.0, risk, 0.0001);
    }

    @Test
    public void blowoutDetectedWhenWearHitsHundred() {
        Tire tire = new Tire(90.0);
        tire.incrementWear(100.0);
        assertTrue(tire.isBlown());
    }
}



// CarTest.java
import org.junit.Test;
import static org.junit.Assert.*;

