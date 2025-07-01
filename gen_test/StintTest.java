package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StintTest {

    @Test
    public void averagesReturnZeroWhenNoLapsRun() {
        Stint stint = new Stint(1, "medium");
        assertEquals(0.0, stint.getAverageTemperature(), 0.0001);
        assertEquals(0.0, stint.getAverageWear(), 0.0001);
        assertEquals(0, stint.getLapsRun());
    }

    @Test
    public void updateAccumulatesTemperatureAndWearCorrectly() {
        Stint stint = new Stint(1, "medium");

        LapData lap1 = new LapData(1, "medium", 100, 10, 0);
        stint.update(lap1, 90); // wear 10

        LapData lap2 = new LapData(2, "medium", 98, 10, 0);
        stint.update(lap2, 85); // wear 15

        LapData lap3 = new LapData(3, "medium", 96, 10, 0);
        stint.update(lap3, 80); // wear 20

        assertEquals(3, stint.getLapsRun());
        assertEquals(1, stint.getStartLap());
        assertEquals(3, stint.getEndLap());

        double expectedAvgTemp = (100 + 98 + 96) / 3.0;
        double expectedAvgWear = (10 + 15 + 20) / 3.0;

        assertEquals(expectedAvgTemp, stint.getAverageTemperature(), 0.0001);
        assertEquals(expectedAvgWear, stint.getAverageWear(), 0.0001);
    }
}


// StrategyEngineTest.java
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

