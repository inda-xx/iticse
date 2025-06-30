package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CarTest {

    @Test
    public void updateWithLapCorrectlyIncreasesWear() {
        Tire tire = new Tire(90.0);
        Car car = new Car(tire);

        // Temp 100 °C   ⇒  (100 – 80) * 0.05 = 1.0 % wear
        TelemetryData data = new TelemetryData(1, 100.0, 30.0, 90.0);
        car.updateWithLap(data);

        assertEquals(1.0, tire.getWearPercentage(), 0.0001);
        assertFalse(car.hasBlownTire());
    }

    @Test
    public void updateWithLapDoesNotDecreaseWearWhenTemperatureIsLow() {
        Tire tire = new Tire(90.0);
        Car car = new Car(tire);

        TelemetryData hotLap = new TelemetryData(1, 100.0, 30.0, 90.0);
        car.updateWithLap(hotLap);               // +1 % wear

        TelemetryData coldLap = new TelemetryData(2, 70.0, 30.0, 90.0);
        car.updateWithLap(coldLap);              // should add 0 % wear

        assertEquals(1.0, tire.getWearPercentage(), 0.0001);
    }

    @Test
    public void blownTireFlagIsRaisedWhenWearReachesHundred() {
        Tire tire = new Tire(90.0);
        tire.incrementWear(99.0);                // very worn
        Car car = new Car(tire);

        // A very hot lap that adds ≥ 1 % wear
        TelemetryData data = new TelemetryData(50, 120.0, 40.0, 90.0);
        car.updateWithLap(data);

        assertTrue(car.hasBlownTire());
        assertEquals(100.0, tire.getWearPercentage(), 0.0001);
    }
}



// RaceEngineerTest.java
import org.junit.Test;
import static org.junit.Assert.*;

