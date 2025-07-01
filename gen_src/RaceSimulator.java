// File: RaceSimulator.java
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
        // TODO: implement the main race loop using reader, car, and engineer
    }
}