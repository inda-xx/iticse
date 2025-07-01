package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RaceEngineerTest {

    @Test
    public void shouldPitWhenRiskAboveThreshold() {
        StrategyConfig cfg = new StrategyConfig();
        cfg.set("blowoutRiskThreshold", 0.4);

        Tire tire = new Tire(100.0);
        tire.incrementWear(90.0); // wear = 90, risk ~0.729
        Car car = new Car(tire);

        RaceEngineer engineer = new RaceEngineer(cfg);
        assertTrue(engineer.shouldPit(car, 10));
    }

    @Test
    public void shouldNotPitWhenRiskLowAndTimeLostLow() {
        StrategyConfig cfg = new StrategyConfig();
        cfg.set("blowoutRiskThreshold", 0.9);
        cfg.set("basePitLossSeconds", 60.0);

        Tire tire = new Tire(85.0);
        tire.incrementWear(10.0); // risk tiny
        Car car = new Car(tire);

        RaceEngineer engineer = new RaceEngineer(cfg);
        assertFalse(engineer.shouldPit(car, 30));
    }
}

/**
 * Tests for TelemetryReader
 */
