package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private List<String> p1Data;
    private List<String> p2Data;
    private List<String> p3Data;
    private Player p1;
    private Player p2;
    private Player p3;

    @BeforeEach
    public void runBefore() {
        p1Data = new ArrayList<>();
        p2Data = new ArrayList<>();
        p3Data = new ArrayList<>();
        createTestData();
        try {
            p1 = new Player(6);
            p2 = new Player(28);
            p3 = new Player(100);
        } catch (IOException e) {
            fail("Something went wrong creating test players");
        }
    }

    @Test
    public void testGetStars() {
        assertEquals(5, p1.getStars());
        assertEquals(4, p2.getStars());
        assertEquals(3, p3.getStars());
    }

    @Test
    public void testGetName() {
        assertEquals("Luka Doncic", p1.getName());
        assertEquals("Spencer Dinwiddie", p2.getName());
        assertEquals("Kevin Huerter", p3.getName());
    }

    @Test
    public void testGetPosition() {
        assertEquals("PG", p1.getPosition());
        assertEquals("SG", p2.getPosition());
        assertEquals("SG", p3.getPosition());
    }

    @Test
    public void testPlayerID() {
        assertEquals(6, p1.getPlayerID());
        assertEquals(28, p2.getPlayerID());
        assertEquals(100, p3.getPlayerID());
    }

    @Test
    public void testGetTeam() {
        assertEquals("DAL", p1.getTeam());
        assertEquals("BRK", p2.getTeam());
        assertEquals("ATL", p3.getTeam());
    }

    @Test
    public void testSetPlayerStats() {
        List<String> data = new ArrayList<>(Arrays.asList("102", "test name", "C", "10", "My Team", "20.1", "90.2",
                "29.0", "0.245", "1.2", "9.8", "0.49", "0.99", "100.3", "5"));
        HashMap<String, Double> stats = new HashMap<>();
        stats.put("minutes", 20.1);
        stats.put("rebounds", 1.2);
        stats.put("assists", 9.8);
        stats.put("blocks", 0.99);
        stats.put("points", 100.3);

        p1.setPlayerStats(data);
        assertEquals(stats, p1.getStats());
    }

    @Test
    public void testToString() {
        assertEquals("Name: Luka Doncic Stars: 5 Position: PG Team: DAL", p1.toString());
        assertEquals("Name: Spencer Dinwiddie Stars: 4 Position: SG Team: BRK", p2.toString());
        assertEquals("Name: Kevin Huerter Stars: 3 Position: SG Team: ATL", p3.toString());
    }

    @Test
    public void testGetStats() {
        HashMap<String, Double> p2Expected = new HashMap<>();
        HashMap<String, Double> p3Expected = new HashMap<>();

        p2Expected.put("minutes", 31.2);
        p2Expected.put("rebounds", 3.5);
        p2Expected.put("assists", 6.8);
        p2Expected.put("blocks", 0.3);
        p2Expected.put("points", 20.6);

        p3Expected.put("minutes", 31.4);
        p3Expected.put("rebounds", 4.1);
        p3Expected.put("assists", 3.8);
        p3Expected.put("blocks", 0.5);
        p3Expected.put("points", 12.2);

        assertEquals(p2Expected, p2.getStats());
        assertEquals(p3Expected, p3.getStats());
    }

    public void createTestData() {
        p1Data.addAll(Arrays.asList("12", "p1", "SG", "20", "TOR", "31.2", "10.8", "16.2", "0.522", "11.2", "1.8",
                "0.2", "0.1", "26.3", "5"));
        p2Data.addAll(Arrays.asList("10", "p2", "PG", "29", "MIL", "8.7", "1.8", "6.9", "0.239", "2.9", "0.4",
                "1.2", "0.7", "6.8", "4"));
        p3Data.addAll(Arrays.asList("1", "p3", "PF", "32", "LAL", "18.9", "3.7", "2.2", "0.389", "3.7", "0.1",
                "2.2", "3.1", "22.7", "3"));
    }
}