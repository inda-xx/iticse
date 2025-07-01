package test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TravelerItineraryTest {

    @Test
    public void addPlanetAddsOnlyLikedPlanets() {
        Planet earth = new Planet("Earth", 1.0, 9.81, 15.0, 1);
        Planet jupiter = new Planet("Jupiter", 5.2, 24.8, -108.0, 79);

        Traveler traveler = new Traveler("Alice", 8.0, 15.0, -20.0, 40.0);
        Itinerary itinerary = new Itinerary(traveler);

        itinerary.addPlanet(earth);   // should be liked
        itinerary.addPlanet(jupiter); // gravity too high

        List<Planet> planets = itinerary.getPlanets();
        assertEquals(1, planets.size());
        assertEquals("Earth", planets.get(0).getName());

        double avgG = itinerary.averageGravity();
        assertEquals(9.81, avgG, 1e-10);

        String summary = itinerary.summary();
        assertTrue(summary.contains("Alice"));
        assertTrue(summary.contains("•"));
    }

    @Test
    public void averageGravityIsZeroWhenNoPlanets() {
        Traveler t = new Traveler("Bob", 0, 100, -500, 500);
        Itinerary itinerary = new Itinerary(t);
        assertEquals(0.0, itinerary.averageGravity(), 0.0);
        assertTrue(itinerary.summary().contains("No matching planets"));
    }
}

/**
 * Tests for StrategyLoader & StrategyConfig
 */
