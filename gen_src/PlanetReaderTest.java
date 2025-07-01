import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class PlanetReaderTest {

    public static void main(String[] args) {
        final String filename = "data/planets_sample.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                if (/* header line */) {
                    continue;
                }

                Optional<Planet> optPlanet = PlanetFactory.fromCsv(line, lineNum);
                if (optPlanet.isPresent()) {
                    // handle valid planet
                    count++;
                }
            }

            // output total count
        } catch (IOException e) {
            // handle I/O error
        }
    }
}