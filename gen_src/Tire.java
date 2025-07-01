// File: Tire.java
public class Tire {
    private String compound;
    private double lifeLeftPercent;
    private double temperature;
    private int lapsCompleted;

    public Tire(String compound) {
        // TODO: initialize fields
    }

    public void updateFromLap(LapData lap) {
        // TODO: update tire state based on lap data
    }

    public String getCompound() {
        // TODO: return compound
        return null;
    }

    public double getLifeLeftPercent() {
        // TODO: return remaining life percentage
        return 0;
    }

    public double getTemperature() {
        // TODO: return current temperature
        return 0;
    }

    public int getLapsCompleted() {
        // TODO: return laps completed
        return 0;
    }
}