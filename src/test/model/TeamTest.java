package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    Team testTeam;
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
        p3Data = new ArrayList<>();;
        createTestData();

        try {
            p1 = new Player(6);
            p2 = new Player(28);
            p3 = new Player(100);
        } catch (IOException e) {
            fail("Error generating test players");
        }

        testTeam = new Team();
    }

    @Test
    public void testAddPlayerNotFullTeam() {
        assertEquals(0, testTeam.size());
        testTeam.addPlayer(p3);
        assertEquals(1, testTeam.size());
        assertTrue(testTeam.hasPlayer("Kevin Huerter"));
    }

    @Test public void testAddPlayerFullTeam() {
        for (int i = 0; i < Team.MAX_TEAM_SIZE; i++) {
            testTeam.addPlayer(p1);
        }

        assertEquals(Team.MAX_TEAM_SIZE, testTeam.size());
        assertFalse(testTeam.hasPlayer("Spencer Dinwiddie"));

        testTeam.addPlayer(p2);

        assertEquals(Team.MAX_TEAM_SIZE, testTeam.size());
        assertFalse(testTeam.hasPlayer("Spencer Dinwiddie"));

    }

    @Test
    public void testSize() {
        assertEquals(0, testTeam.size());
        testTeam.addPlayer(p2);
        assertEquals(1, testTeam.size());
    }

    @Test
    public void testGetPlayerListNoPlayers() {
        List<Player> expected = new ArrayList<>();
        assertEquals(expected, testTeam.getPlayerList());
    }

    @Test
    public void testGetPlayerListSomePlayers() {
        List<Player> expected = new ArrayList<>();

        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);
        testTeam.addPlayer(p3);

        expected.add(p1);
        expected.add(p2);
        expected.add(p3);

        assertEquals(expected, testTeam.getPlayerList());
    }

    @Test
    public void testHasPlayerPlayerNotOntTeam() {
        assertFalse(testTeam.hasPlayer("Kevin Huerter"));
    }

    @Test
    public void testHasPlayerPlayerOnTeam() {
        testTeam.addPlayer(p2);
        assertTrue(testTeam.hasPlayer("Spencer Dinwiddie"));
    }

    @Test
    public void testIsEmptyNoPlayersOnTeam() {
        assertTrue(testTeam.isEmpty());
    }

    @Test
    public void testIsEmptySomePlayersOnTeam() {
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);
        testTeam.addPlayer(p3);
        assertFalse(testTeam.isEmpty());
    }

    @Test
    public void testPrintPlayersSomePlayers() {
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p3);
        assertEquals("Name: Luka Doncic Stars: 5 Position: PG Team: DAL\n" +
                        "Name: Kevin Huerter Stars: 3 Position: SG Team: ATL\n", testTeam.printPlayers());
    }

    @Test
    public void testPrintPlayersNoPlayers() {
        assertEquals("There are no players!\n", testTeam.printPlayers());
    }

    @Test
    public void testGetPlayerByName() {
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);
        assertEquals(p2, testTeam.getPlayerByName("Spencer Dinwiddie"));
        assertEquals(p1, testTeam.getPlayerByName("Luka Doncic"));
    }

    // this test is just to satisfy autobot, it is covered in my requires clause that the player must be on the roster
    // for this method to work
    @Test
    public void testGetPlayerByNameNotOnTeam() {
        assertNull(testTeam.getPlayerByName("Player not in team"));
    }

    @Test
    public void testRemovePlayer() {
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p3);

        // check the initial status of the claimed roster
        assertEquals(2, testTeam.size());
        assertTrue(testTeam.hasPlayer("Kevin Huerter"));

        // ensure the player was removed
        assertEquals(p3, testTeam.removePlayer("Kevin Huerter"));
        assertEquals(1, testTeam.size());
        assertFalse(testTeam.hasPlayer("Kevin Huerter"));
    }

    @Test
    public void testIsFullTeamNotFull() {
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);
        assertFalse(testTeam.isFull());
    }

    @Test
    public void testIsFullTeamFull() {
        for (int i = 0; i < Team.MAX_TEAM_SIZE; i++) {
            testTeam.addPlayer(p3);
        }
        assertTrue(testTeam.isFull());
    }

    @Test
    public void testRosterToJsonNotEmpty() {
        testTeam.addPlayer(p1);
        testTeam.addPlayer(p2);
        testTeam.addPlayer(p3);
        JSONArray testTeamAsJsonArray = testTeam.rosterToJson();

        assertFalse(testTeamAsJsonArray.isEmpty());
        assertEquals("Luka Doncic", testTeamAsJsonArray.getJSONObject(0).get(Player.JSON_NAME_KEY));
        assertEquals(6, testTeamAsJsonArray.getJSONObject(0).get(Player.JSON_PLAYERID_KEY));
        assertEquals("Spencer Dinwiddie",
                testTeamAsJsonArray.getJSONObject(1).get(Player.JSON_NAME_KEY));
        assertEquals(28, testTeamAsJsonArray.getJSONObject(1).get(Player.JSON_PLAYERID_KEY));
        assertEquals("Kevin Huerter", testTeamAsJsonArray.getJSONObject(2).get(Player.JSON_NAME_KEY));
        assertEquals(100, testTeamAsJsonArray.getJSONObject(2).get(Player.JSON_PLAYERID_KEY));

    }

    @Test
    public void testRosterToJsonEmpty() {
        JSONArray testTeamAsJsonArray = testTeam.rosterToJson();

        assertTrue(testTeamAsJsonArray.isEmpty());
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
