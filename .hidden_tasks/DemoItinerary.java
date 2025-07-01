import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class DemoItinerary {

    public static void main(String[] args) {
        List<Planet> allPlanets = loadPlanets("data/planets_sample.csv");
        if (allPlanets.isEmpty()) {
            System.out.println("No valid planets found to build itinerary.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Traveler Name: ");
        String name = scanner.nextLine();

        System.out.print("Min Gravity: ");
        double minG = scanner.nextDouble();
        System.out.print("Max Gravity: ");
        double maxG = scanner.nextDouble();

        System.out.print("Min Temperature (°C): ");
        double minT = scanner.nextDouble();
        System.out.print("Max Temperature (°C): ");
        double maxT = scanner.nextDouble();

        Traveler traveler = new Traveler(name, minG, maxG, minT, maxT);
        Itinerary itinerary = new Itinerary(traveler);

        for (Planet p : allPlanets) {
            itinerary.addPlanet(p);
        }

        System.out.println();
        System.out.println(itinerary.summary());
    }

    private static List<Planet> loadPlanets(String filename) {
        List<Planet> planets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                if (lineNum == 1 && line.toLowerCase().contains("name")) continue;
                Optional<Planet> opt = PlanetFactory.fromCsv(line, lineNum);
                opt.ifPresent(planets::add);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
        return planets;
    }
}