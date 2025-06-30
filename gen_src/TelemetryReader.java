import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class TelemetryReader {
    private BufferedReader reader;

    public TelemetryReader(String filename) throws IOException {
        // initialize reader
    }

    public Optional<TelemetryData> nextLap() throws IOException {
        // read and parse next telemetry line
        return Optional.empty();
    }

    public void close() throws IOException {
        // close reader
    }
}