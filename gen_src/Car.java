// File: Car.java
public class Car {
    private Tire tire;
    private boolean blown;

    public Car(Tire tire) {
        // initialize fields
    }

    public Tire getTire() {
        // return the current tire
        return tire;
    }

    public void updateWithLap(TelemetryData data) {
        // update tire state based on telemetry
    }

    public boolean hasBlownTire() {
        // indicate if the tire has blown
        return blown;
    }
}