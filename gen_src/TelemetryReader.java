import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class TelemetryReader {
    private BufferedReader reader;

    public TelemetryReader(String filename) throws IOException {
        // setup reader
    }

    public Optional<TelemetryData> nextLap() throws IOException {
        // read next line and parse into TelemetryData
        return Optional.empty();
    }

    public void close() throws IOException {
        // close resources
    }
}