public class Planet {
    private final String name;
    private final double distanceFromSunAU;
    private final double gravity;
    private final double avgTempC;
    private final int moonCount;

    /**
     * Constructs a Planet object with the specified properties.
     *
     * @param name               Planet name
     * @param distanceFromSunAU  Distance from the sun in AU (astronomical units)
     * @param gravity            Surface gravity in m/s^2
     * @param avgTempC           Average temperature in Celsius
     * @param moonCount          Number of moons
     */
    public Planet(String name, double distanceFromSunAU, double gravity, double avgTempC, int moonCount) {
        this.name = name;
        this.distanceFromSunAU = distanceFromSunAU;
        this.gravity = gravity;
        this.avgTempC = avgTempC;
        this.moonCount = moonCount;
    }

    public String getName() {
        return name;
    }

    public double getDistanceFromSunAU() {
        return distanceFromSunAU;
    }

    public double getGravity() {
        return gravity;
    }

    public double getAvgTempC() {
        return avgTempC;
    }

    public int getMoonCount() {
        return moonCount;
    }

    @Override
    public String toString() {
        return String.format("Planet{name='%s', distanceFromSunAU=%.2f, gravity=%.2f, avgTempC=%.1f, moonCount=%d}",
                name, distanceFromSunAU, gravity, avgTempC, moonCount);
    }
}