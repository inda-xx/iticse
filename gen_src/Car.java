public class Car {
    private Tire tire;
    private boolean blown;

    public Car(Tire tire) {
        this.tire = tire;
        this.blown = false;
    }

    public Tire getTire() {
        return tire;
    }

    public void updateWithLap(TelemetryData data) {
        tire.updateTemperature(data.getTireTempCelsius());

        double temp = data.getTireTempCelsius();
        double lapTime = data.getLapTimeSeconds();

        double wearDelta = (temp - 80.0) * 0.05;
        if (wearDelta < 0) wearDelta = 0;
        tire.incrementWear(wearDelta);

        blown = tire.isBlown();
    }

    public boolean hasBlownTire() {
        return blown;
    }
}