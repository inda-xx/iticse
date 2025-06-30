// File: Tire.java

public class Tire {
    private double temperature;
    private double wearPercentage;

    public Tire(double startingTemperature) {
        // TODO: initialize fields
    }

    public Tire(TelemetryData data) {
        // TODO: initialize fields from TelemetryData
    }

    public double getTemperature() {
        // TODO: return current temperature
        return 0.0;
    }

    public double getWearPercentage() {
        // TODO: return current wear percentage
        return 0.0;
    }

    public void updateTemperature(double newTemp) {
        // TODO: update temperature
    }

    public void incrementWear(double delta) {
        // TODO: increase wear, cap at maximum
    }

    public boolean isBlown() {
        // TODO: determine if tire is blown
        return false;
    }

    public double blowoutRisk() {
        // TODO: calculate risk of blowout
        return 0.0;
    }

    @Override
    public String toString() {
        // TODO: return descriptive string
        return null;
    }
}