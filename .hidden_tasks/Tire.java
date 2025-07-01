public class Tire {
    private String compound;
    private double lifeLeftPercent;
    private double temperature;
    private int lapsCompleted;

    public Tire(String compound) {
        this.compound = compound;
        this.lifeLeftPercent = 100.0;
        this.temperature = 90.0;
        this.lapsCompleted = 0;
    }

    public void updateFromLap(LapData lap) {
        double degradationMultiplier = 1.0;
        switch (compound.toLowerCase()) {
            case "soft":
                degradationMultiplier = 1.2;
                break;
            case "medium":
                degradationMultiplier = 1.0;
                break;
            case "hard":
                degradationMultiplier = 0.8;
                break;
        }

        double wearThisLap = lap.getDegradation() * degradationMultiplier;
        lifeLeftPercent = Math.max(0, lifeLeftPercent - wearThisLap);

        double tempDrift = lap.getTemperature() - temperature;
        this.temperature += tempDrift * 0.5;
        this.lapsCompleted++;
    }

    public String getCompound() {
        return compound;
    }

    public double getLifeLeftPercent() {
        return lifeLeftPercent;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getLapsCompleted() {
        return lapsCompleted;
    }
}