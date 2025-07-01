// DemoItinerary.java
import java.util.List;
import java.util.Scanner;

public class DemoItinerary {

    public static void main(String[] args) {
        // load planet data
        List<Planet> allPlanets = loadPlanets("data/planets_sample.csv");
        // handle empty list

        // read traveler info from console
        Scanner scanner = new Scanner(System.in);
        // prompt for name, gravity and temperature ranges

        // create Traveler and Itinerary
        // add planets to itinerary

        // print itinerary summary
    }

    private static List<Planet> loadPlanets(String filename) {
        // read CSV and build list of Planet
        return null;
    }
}

// Planet.java
public class Planet {
    private String name;
    private double gravity;
    private double temperature;

    public Planet(String name, double gravity, double temperature) {
        // constructor
    }

    // getters
}

// Traveler.java
public class Traveler {
    private String name;
    private double minGravity;
    private double maxGravity;
    private double minTemperature;
    private double maxTemperature;

    public Traveler(String name, double minG, double maxG, double minT, double maxT) {
        // constructor
    }

    // getters
}

// Itinerary.java
import java.util.List;

public class Itinerary {
    private Traveler traveler;
    private List<Planet> planets;

    public Itinerary(Traveler traveler) {
        // constructor
    }

    public void addPlanet(Planet p) {
        // add if within traveler's criteria
    }

    public String summary() {
        // return formatted summary
        return null;
    }
}

// PlanetFactory.java
import java.util.Optional;

public class PlanetFactory {

    public static Optional<Planet> fromCsv(String line, int lineNum) {
        // parse CSV line into Planet
        return Optional.empty();
    }
}