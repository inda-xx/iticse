public class RaceEngineer {
    private StrategyConfig config;

    public RaceEngineer(StrategyConfig config) {
        this.config = config;
    }

    public boolean shouldPit(Car car, int lapsRemaining) {
        Tire tire = car.getTire();
        double risk = tire.blowoutRisk();
        double timeLost = risk * 60.0;
        double expectedPitLoss = config.get("basePitLossSeconds");

        return risk > config.get("blowoutRiskThreshold") || timeLost > expectedPitLoss;
    }
}