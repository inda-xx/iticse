// File: LapData.java
public class LapData {
    private final int lapNumber;
    private final String compound;
    private final double temperature;
    private final double degradation;
    private final int stintId;

    public LapData(int lapNumber, String compound, double temperature, double degradation, int stintId) {
        // initialize fields
    }

    public int getLapNumber() {
        return 0;
    }

    public String getCompound() {
        return null;
    }

    public double getTemperature() {
        return 0.0;
    }

    public double getDegradation() {
        return 0.0;
    }

    public int getStintId() {
        return 0;
    }

    public static LapData parse(String csvLine) throws CorruptLapException {
        // parsing logic
        return null;
    }
}

// File: CorruptLapException.java
public class CorruptLapException extends Exception {
    public CorruptLapException(String message) {
        super(message);
    }
}