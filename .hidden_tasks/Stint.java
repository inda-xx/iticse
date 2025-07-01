public class Stint {
    private final int startLap;
    private int endLap;
    private final String compound;
    private int stintedLaps;
    private double totalTemperature;
    private double totalWear;

    public Stint(int startLap, String compound) {
        this.startLap = startLap;
        this.compound = compound;
        this.endLap = startLap;
        this.stintedLaps = 0;
        this.totalTemperature = 0.0;
        this.totalWear = 0.0;
    }

    public void update(LapData lap, double currentLifeLeft) {
        this.endLap = lap.getLapNumber();
        this.totalTemperature += lap.getTemperature();
        this.totalWear += (100 - currentLifeLeft);
        this.stintedLaps++;
    }

    public int getStartLap() {
        return startLap;
    }

    public int getEndLap() {
        return endLap;
    }

    public String getCompound() {
        return compound;
    }

    public int getLapsRun() {
        return stintedLaps;
    }

    public double getAverageTemperature() {
        return stintedLaps == 0 ? 0 : totalTemperature / stintedLaps;
    }

    public double getAverageWear() {
        return stintedLaps == 0 ? 0 : totalWear / stintedLaps;
    }
}