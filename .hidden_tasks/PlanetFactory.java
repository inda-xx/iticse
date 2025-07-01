public class PlanetFactory {

    /**
     * Parses a line of CSV data into a Planet object.
     *
     * @param csvLine    the CSV string, expected to contain 5 comma-separated values
     * @param lineNumber the line number for error reporting
     * @return Optional.of(Planet) if successful, Optional.empty() otherwise
     */
    public static Optional<Planet> fromCsv(String csvLine, int lineNumber) {
        String[] tokens = csvLine.split(",");
        if (tokens.length != 5) {
            System.err.println("Line " + lineNumber + ": Incorrect field count. Expected 5 but got " + tokens.length);
            return Optional.empty();
        }

        try {
            String name = tokens[0].trim();
            double distanceFromSun = Double.parseDouble(tokens[1].trim());
            double gravity = Double.parseDouble(tokens[2].trim());
            double avgTemp = Double.parseDouble(tokens[3].trim());
            int moons = Integer.parseInt(tokens[4].trim());

            Planet planet = new Planet(name, distanceFromSun, gravity, avgTemp, moons);
            return Optional.of(planet);

        } catch (NumberFormatException e) {
            System.err.println("Line " + lineNumber + ": Invalid numeric value. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Line " + lineNumber + ": Unexpected error. " + e.getMessage());
        }
        return Optional.empty();
    }
}