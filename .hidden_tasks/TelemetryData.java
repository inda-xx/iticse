public class TelemetryData {
    private final int lapNumber;
    private final double tireTempCelsius;
    private final double trackTempCelsius;
    private final double lapTimeSeconds;

    public TelemetryData(int lapNumber, double tireTempCelsius, double trackTempCelsius, double lapTimeSeconds) {
        this.lapNumber = lapNumber;
        this.tireTempCelsius = tireTempCelsius;
        this.trackTempCelsius = trackTempCelsius;
        this.lapTimeSeconds = lapTimeSeconds;
    }

    public int getLapNumber() {
        return lapNumber;
    }

    public double getTireTempCelsius() {
        return tireTempCelsius;
    }

    public double getTrackTempCelsius() {
        return trackTempCelsius;
    }

    public double getLapTimeSeconds() {
        return lapTimeSeconds;
    }
}