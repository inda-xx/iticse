package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TireTest {

    @Test
    public void updateSoftTireAppliesCorrectWearMultiplier() {
        Tire tire = new Tire("soft");
        LapData lap = new LapData(1, "soft", 100, 10, 0);
        tire.updateFromLap(lap);
        assertEquals(88.0, tire.getLifeLeftPercent(), 0.0001); // 100 - (10*1.2)
    }

    @Test
    public void updateMediumTireAppliesCorrectWearMultiplier() {
        Tire tire = new Tire("medium");
        LapData lap = new LapData(1, "medium", 100, 10, 0);
        tire.updateFromLap(lap);
        assertEquals(90.0, tire.getLifeLeftPercent(), 0.0001); // 100 - (10*1.0)
    }

    @Test
    public void updateHardTireAppliesCorrectWearMultiplier() {
        Tire tire = new Tire("hard");
        LapData lap = new LapData(1, "hard", 100, 10, 0);
        tire.updateFromLap(lap);
        assertEquals(92.0, tire.getLifeLeftPercent(), 0.0001); // 100 - (10*0.8)
    }

    @Test
    public void lifeLeftDoesNotGoBelowZero() {
        Tire tire = new Tire("soft");
        LapData lap = new LapData(1, "soft", 100, 200, 0); // way more than remaining life
        tire.updateFromLap(lap);
        assertEquals(0.0, tire.getLifeLeftPercent(), 0.0001);
    }

    @Test
    public void temperatureIncreasesTowardLapTemperature() {
        Tire tire = new Tire("medium"); // initial temp 90
        LapData lap = new LapData(1, "medium", 110, 0, 0);
        tire.updateFromLap(lap);
        assertEquals(100.0, tire.getTemperature(), 0.0001); // 90 + (110-90)*0.5
    }

    @Test
    public void temperatureDecreasesTowardLapTemperature() {
        Tire tire = new Tire("medium"); // initial temp 90
        LapData lap = new LapData(1, "medium", 70, 0, 0);
        tire.updateFromLap(lap);
        assertEquals(80.0, tire.getTemperature(), 0.0001); // 90 + (70-90)*0.5
    }

    @Test
    public void lapsCompletedIncrementsEachUpdate() {
        Tire tire = new Tire("hard");
        for (int i = 1; i <= 5; i++) {
            tire.updateFromLap(new LapData(i, "hard", 100, 5, 0));
        }
        assertEquals(5, tire.getLapsCompleted());
    }
}


// StintTest.java
import org.junit.Test;

import static org.junit.Assert.*;

