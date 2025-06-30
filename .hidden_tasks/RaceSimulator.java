public class RaceSimulator {
    private TelemetryReader reader;
    private Car car;
    private RaceEngineer engineer;

    private int totalLaps = 50;

    public RaceSimulator(TelemetryReader reader, Car car, RaceEngineer engineer) {
        this.reader = reader;
        this.car = car;
        this.engineer = engineer;
    }

    public void run() {
        try {
            while (true) {
                Optional<TelemetryData> dataOpt = reader.nextLap();
                if (dataOpt.isEmpty()) break;

                TelemetryData data = dataOpt.get();
                car.updateWithLap(data);

                double temp = car.getTire().getTemperature();
                double wear = car.getTire().getWearPercentage();
                double risk = car.getTire().blowoutRisk();
                boolean pit = engineer.shouldPit(car, totalLaps - data.getLapNumber());

                String status = car.hasBlownTire() ? "BLOWN!" : (pit ? "BOX!" : "SAFE");
                System.out.printf("Lap %d | Temp %.1f °C | Wear %.1f %% | Blow-out Risk %.2f | %s\n",
                        data.getLapNumber(), temp, wear, risk, status);

                if (car.hasBlownTire() || data.getLapNumber() >= totalLaps) {
                    System.out.println("Race Ended");
                    break;
                }
            }

            reader.close();

        } catch (IOException e) {
            System.err.println("Error during race: " + e.getMessage());
        }
    }
}