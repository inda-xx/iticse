public class Traveler {
    private final String name;
    private final double minGravity;
    private final double maxGravity;
    private final double minTemp;
    private final double maxTemp;

    /**
     * Constructs a Traveler with the given name and preferences.
     *
     * @param name       Traveler's name
     * @param minGravity Minimum gravity the traveler prefers
     * @param maxGravity Maximum gravity the traveler prefers
     * @param minTemp    Minimum average temperature preferred
     * @param maxTemp    Maximum average temperature preferred
     */
    public Traveler(String name, double minGravity, double maxGravity, double minTemp, double maxTemp) {
        this.name = name;
        this.minGravity = minGravity;
        this.maxGravity = maxGravity;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public String getName() {
        return name;
    }

    public boolean likes(Planet p) {
        return p.getGravity() >= minGravity && p.getGravity() <= maxGravity &&
                p.getAvgTempC() >= minTemp && p.getAvgTempC() <= maxTemp;
    }
}