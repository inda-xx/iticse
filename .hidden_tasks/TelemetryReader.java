public class TelemetryReader {
    private BufferedReader reader;

    public TelemetryReader(String filename) throws IOException {
        this.reader = new BufferedReader(new FileReader(filename));
    }

    public Optional<TelemetryData> nextLap() throws IOException {
        String line = reader.readLine();
        if (line == null) return Optional.empty();

        String[] parts = line.split(",");
        if (parts.length < 4) {
            throw new IOException("Malformed telemetry line: " + Arrays.toString(parts));
        }

        int lapNumber = Integer.parseInt(parts[0].trim());
        double tireTemp = Double.parseDouble(parts[1].trim());
        double trackTemp = Double.parseDouble(parts[2].trim());
        double lapTime = Double.parseDouble(parts[3].trim());

        return Optional.of(new TelemetryData(lapNumber, tireTemp, trackTemp, lapTime));
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}