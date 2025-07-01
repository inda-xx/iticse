import java.util.List;
import java.util.ArrayList;

public class Itinerary {
    private final Traveler traveler;
    private final List<Planet> planets;

    /**
     * Constructs a new Itinerary for the specified traveler.
     *
     * @param traveler the traveler
     */
    public Itinerary(Traveler traveler) {
        this.traveler = traveler;
        this.planets = new ArrayList<>();
    }

    /**
     * Adds a planet to the itinerary if the traveler likes it.
     *
     * @param planet the planet to add
     */
    public void addPlanet(Planet planet) {
        if (traveler.likes(planet)) {
            planets.add(planet);
        }
    }

    /**
     * Computes the average gravity of all planets in the itinerary.
     *
     * @return average gravity or 0 if no planets
     */
    public double averageGravity() {
        if (planets.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Planet p : planets) {
            sum += p.getGravity();
        }
        return sum / planets.size();
    }

    /**
     * Returns a summary of the itinerary.
     *
     * @return summary string
     */
    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Itinerary for ").append(traveler.getName()).append(":\n");
        if (planets.isEmpty()) {
            sb.append("No matching planets found.\n");
            return sb.toString();
        }
        for (Planet p : planets) {
            sb.append("• ").append(p).append("\n");
        }
        sb.append(String.format("Average Gravity: %.2f m/s²\n", averageGravity()));
        return sb.toString();
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public Traveler getTraveler() {
        return traveler;
    }
}