package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CarTest {

    @Test
    public void wearOnlyAccumulatesWhenHot() {
        Tire tire = new Tire(70.0); // below threshold
        Car car = new Car(tire);

        TelemetryData coolLap = new TelemetryData(1, 75.0, 30.0, 90.0);
        car.updateWithLap(coolLap);
        assertEquals(0.0, tire.getWearPercentage(), 0.0001);

        TelemetryData hotLap = new TelemetryData(2, 100.0, 32.0, 88.0);
        car.updateWithLap(hotLap);
        double expectedWear = (100.0 - 80.0) * 0.05; // 1.0
        assertEquals(expectedWear, tire.getWearPercentage(), 1e-10);
    }

    @Test
    public void blownTireIsDetected() {
        Tire tire = new Tire(120.0);
        Car car = new Car(tire);

        // each lap at 120C adds (120-80)*0.05 = 2.0 wear
        for (int i = 1; i <= 51; i++) {
            car.updateWithLap(new TelemetryData(i, 120.0, 35.0, 85.0));
        }
        assertTrue(tire.getWearPercentage() >= 100.0);
        assertTrue(car.hasBlownTire());
    }
}

/**
 * Tests for Traveler & Itinerary
 */
