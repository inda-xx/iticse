public class Main {
    public static void main(String[] args) {
        String telemetryFile = "data/sample_telemetry.csv";
        String strategyFile = "config/strategy.csv";

        try {
            TelemetryReader reader = new TelemetryReader(telemetryFile);
            StrategyConfig config = StrategyLoader.loadConfig(strategyFile);
            RaceEngineer engineer = new RaceEngineer(config);

            Tire startingTire = new Tire(90.0);
            Car car = new Car(startingTire);

            RaceSimulator simulator = new RaceSimulator(reader, car, engineer);
            simulator.run();
        } catch (Exception e) {
            System.err.println("Failed to start simulation: " + e.getMessage());
        }
    }
}