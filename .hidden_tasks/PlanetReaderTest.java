public class PlanetReaderTest {

    public static void main(String[] args) {
        final String filename = "data/planets_sample.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            int count = 0;

            System.out.println("Reading records...");
            while ((line = reader.readLine()) != null) {
                lineNum++;
                // Skip header
                if (lineNum == 1 && line.toLowerCase().contains("name")) continue;

                Optional<Planet> optPlanet = PlanetFactory.fromCsv(line, lineNum);
                if (optPlanet.isPresent()) {
                    System.out.printf("✓ Line %d   ➜ %s%n", lineNum, optPlanet.get());
                    count++;
                }
            }
            System.out.println("Total valid planets read: " + count);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}