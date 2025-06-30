public class Tire {
    private double temperature;
    private double wearPercentage;

    public Tire(double startingTemperature) {
        this.temperature = startingTemperature;
        this.wearPercentage = 0.0;
    }

    public Tire(TelemetryData data) {
        this.temperature = data.getTireTempCelsius();
        this.wearPercentage = 0.0;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWearPercentage() {
        return wearPercentage;
    }

    public void updateTemperature(double newTemp) {
        this.temperature = newTemp;
    }

    public void incrementWear(double delta) {
        this.wearPercentage += delta;
        if (this.wearPercentage > 100.0) {
            this.wearPercentage = 100.0;
        }
    }

    public boolean isBlown() {
        return this.wearPercentage >= 100.0;
    }

    public double blowoutRisk() {
        double risk = Math.pow(this.wearPercentage / 100.0, 3);
        if (this.temperature > 110.0) {
            risk += 0.1;
        }
        return Math.min(risk, 1.0);
    }

    @Override
    public String toString() {
        return String.format("Tire{temp=%.1f °C, wear=%.2f %%}", temperature, wearPercentage);
    }
}