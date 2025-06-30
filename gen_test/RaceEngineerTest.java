package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RaceEngineerTest {

    @Test
    public void shouldPitWhenRiskAboveThreshold() {
        StrategyConfig cfg = new StrategyConfig();           // default threshold 0.4
        RaceEngineer engineer = new RaceEngineer(cfg);

        Tire tire = new Tire(90.0);
        tire.incrementWear(80.0);                            // risk ≈ 0.512
        Car car = new Car(tire);

        assertTrue(engineer.shouldPit(car, 20));
    }

    @Test
    public void shouldNotPitWhenRiskIsLowAndTimeLostIsAcceptable() {
        StrategyConfig cfg = new StrategyConfig();
        RaceEngineer engineer = new RaceEngineer(cfg);

        Tire tire = new Tire(90.0);
        tire.incrementWear(10.0);                            // risk ≈ 0.001
        Car car = new Car(tire);

        assertFalse(engineer.shouldPit(car, 20));
    }

    @Test
    public void shouldPitWhenExpectedTimeLostBeatsPitLossEvenIfRiskLow() {
        StrategyConfig cfg = new StrategyConfig();
        cfg.set("basePitLossSeconds", 5.0);                  // unrealistic but forces condition
        RaceEngineer engineer = new RaceEngineer(cfg);

        Tire tire = new Tire(90.0);
        tire.incrementWear(30.0);                            // wear 30 % ⇒ risk ≈ 0.027
        Car car = new Car(tire);

        assertTrue(engineer.shouldPit(car, 20));
    }
}



// TelemetryReaderTest.java
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.util.Optional;

import static org.junit.Assert.*;

