public class LapData {
    private final int lapNumber;
    private final String compound;
    private final double temperature;
    private final double degradation;
    private final int stintId;

    public LapData(int lapNumber, String compound, double temperature, double degradation, int stintId) {
        this.lapNumber = lapNumber;
        this.compound = compound;
        this.temperature = temperature;
        this.degradation = degradation;
        this.stintId = stintId;
    }

    public int getLapNumber() {
        return lapNumber;
    }

    public String getCompound() {
        return compound;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getDegradation() {
        return degradation;
    }

    public int getStintId() {
        return stintId;
    }

    public static LapData parse(String csvLine) throws CorruptLapException {
        String[] parts = csvLine.trim().split(",");
        if (parts.length < 5) {
            throw new CorruptLapException("Missing data fields.");
        }

        try {
            int lapNumber = Integer.parseInt(parts[0].trim());
            String compound = parts[1].trim();
            double temperature = Double.parseDouble(parts[2].trim());
            double degradation = Double.parseDouble(parts[3].trim());
            int stintId = Integer.parseInt(parts[4].trim());

            if (temperature < 0 || degradation < 0 || degradation > 100) {
                throw new CorruptLapException("Invalid values in lap data.");
            }

            return new LapData(lapNumber, compound, temperature, degradation, stintId);
        } catch (NumberFormatException e) {
            throw new CorruptLapException("Invalid number format: " + csvLine);
        }
    }
}