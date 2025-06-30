// File: RaceEngineer.java
public class RaceEngineer {
    private StrategyConfig config;

    public RaceEngineer(StrategyConfig config) {
        this.config = config;
    }

    public boolean shouldPit(Car car, int lapsRemaining) {
        // TODO: decide whether to pit based on strategy configuration and car state
        return false;
    }
}