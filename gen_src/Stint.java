// File: Stint.java
public class Stint {
    private final int startLap;
    private int endLap;
    private final String compound;
    private int stintedLaps;
    private double totalTemperature;
    private double totalWear;

    public Stint(int startLap, String compound) {
        // constructor
    }

    public void update(LapData lap, double currentLifeLeft) {
        // update stint data
    }

    public int getStartLap() {
        return 0;
    }

    public int getEndLap() {
        return 0;
    }

    public String getCompound() {
        return null;
    }

    public int getLapsRun() {
        return 0;
    }

    public double getAverageTemperature() {
        return 0.0;
    }

    public double getAverageWear() {
        return 0.0;
    }
}