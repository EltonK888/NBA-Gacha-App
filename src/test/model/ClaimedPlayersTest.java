
package model;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClaimedPlayersTest {
    ClaimedPlayers claimedPlayers;
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
        claimedPlayers = new ClaimedPlayers();
    }

    @Test
    public void testSwitchToActiveTeamNotFull() {
        // set up the claimed player list
        claimedPlayers.addPlayer(p2);
        claimedPlayers.addPlayer(p3);

        // check the initial status of the claimed player list and team
        assertEquals(0, testTeam.size());
        assertEquals(2, claimedPlayers.size());
        assertFalse(testTeam.hasPlayer("Spencer Dinwiddie"));
        assertTrue(claimedPlayers.hasPlayer("Spencer Dinwiddie"));

        claimedPlayers.switchToActiveTeam(testTeam, "Spencer Dinwiddie");

        // test to make sure the player was switched from the claimed players to the team
        assertEquals(1, testTeam.size());
        assertEquals(1, claimedPlayers.size());
        assertTrue(testTeam.hasPlayer("Spencer Dinwiddie"));
        assertFalse(claimedPlayers.hasPlayer("Spencer Dinwiddie"));
    }

    @Test
    public void testSwitchToActiveTeamFull() {
        // set up the claimed player list
        for (int i = 0; i < Team.MAX_TEAM_SIZE; i++) {
            claimedPlayers.addPlayer(p2);
        }
        claimedPlayers.addPlayer(p3);

        // fill up the team
        for (int i = 0; i < Team.MAX_TEAM_SIZE; i++) {
            claimedPlayers.switchToActiveTeam(testTeam, "Spencer Dinwiddie");
        }

        // check the initial status of the claimed player list and team
        assertEquals(15, testTeam.size());
        assertEquals(1, claimedPlayers.size());
        assertFalse(testTeam.hasPlayer("Kevin Huerter"));
        assertFalse(claimedPlayers.hasPlayer("Spencer Dinwiddie"));

        claimedPlayers.switchToActiveTeam(testTeam, "Kevin Huerter", "Spencer Dinwiddie");

        // test to make sure the player was switched from the claimed players to the team
        assertEquals(15, testTeam.size());
        assertEquals(1, claimedPlayers.size());
        assertTrue(testTeam.hasPlayer("Kevin Huerter"));
        assertTrue(claimedPlayers.hasPlayer("Spencer Dinwiddie"));

    }

    @Test
    public void testAddPlayer() {
        assertEquals(0, claimedPlayers.size());
        claimedPlayers.addPlayer(p3);
        assertEquals(1, claimedPlayers.size());
        assertTrue(claimedPlayers.hasPlayer("Kevin Huerter"));
    }

    @Test
    public void testSize() {
        assertEquals(0, claimedPlayers.size());
        claimedPlayers.addPlayer(p2);
        assertEquals(1, claimedPlayers.size());
    }

    @Test
    public void testGetPlayerListNoPlayers() {
        List<Player> expected = new ArrayList<>();
        assertEquals(expected, claimedPlayers.getPlayerList());
    }

    @Test
    public void testGetPlayerListSomePlayers() {
        List<Player> expected = new ArrayList<>();

        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p2);
        claimedPlayers.addPlayer(p3);

        expected.add(p1);
        expected.add(p2);
        expected.add(p3);

        assertEquals(expected, claimedPlayers.getPlayerList());
    }

    @Test
    public void testHasPlayerDoesNotHavePlayer() {
        assertFalse(claimedPlayers.hasPlayer("Kevin Huerter"));
    }

    @Test
    public void testHasPlayerPlayerOnClaimedRoster() {
        claimedPlayers.addPlayer(p2);
        assertTrue(claimedPlayers.hasPlayer("Spencer Dinwiddie"));
    }

    @Test
    public void testIsEmptyNoClaimedPlayers() {
        assertTrue(claimedPlayers.isEmpty());
    }

    @Test
    public void testIsEmptySomeClaimedPlayers() {
        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p2);
        claimedPlayers.addPlayer(p3);
        assertFalse(claimedPlayers.isEmpty());
    }

    @Test
    public void testPrintPlayersSomePlayers() {
        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p3);
        assertEquals("Name: Luka Doncic Stars: 5 Position: PG Team: DAL\n" +
                        "Name: Kevin Huerter Stars: 3 Position: SG Team: ATL\n", claimedPlayers.printPlayers());
    }

    @Test
    public void testPrintPlayersNoPlayers() {
        assertEquals("There are no players!\n", claimedPlayers.printPlayers());
    }

    @Test
    public void testGetPlayerByName() {
        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p2);
        assertEquals(p2, claimedPlayers.getPlayerByName("Spencer Dinwiddie"));
        assertEquals(p1, claimedPlayers.getPlayerByName("Luka Doncic"));
    }

    @Test
    public void testRemovePlayer() {
        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p3);

        // check the initial status of the claimed roster
        assertEquals(2, claimedPlayers.size());
        assertTrue(claimedPlayers.hasPlayer("Kevin Huerter"));

        // ensure the player was removed
        assertEquals(p3, claimedPlayers.removePlayer("Kevin Huerter"));
        assertEquals(1, claimedPlayers.size());
        assertFalse(claimedPlayers.hasPlayer("Kevin Huerter"));
    }

    @Test
    public void testRosterToJsonNotEmpty() {
        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p2);
        claimedPlayers.addPlayer(p3);
        JSONArray testClaimedPlayersAsJsonArray = claimedPlayers.rosterToJson();

        assertFalse(testClaimedPlayersAsJsonArray.isEmpty());
        assertEquals("Luka Doncic", testClaimedPlayersAsJsonArray.getJSONObject(0).get(Player.JSON_NAME_KEY));
        assertEquals(6, testClaimedPlayersAsJsonArray.getJSONObject(0).get(Player.JSON_PLAYERID_KEY));
        assertEquals("Spencer Dinwiddie",
                testClaimedPlayersAsJsonArray.getJSONObject(1).get(Player.JSON_NAME_KEY));
        assertEquals(28, testClaimedPlayersAsJsonArray.getJSONObject(1).get(Player.JSON_PLAYERID_KEY));
        assertEquals("Kevin Huerter", testClaimedPlayersAsJsonArray.getJSONObject(2).get(Player.JSON_NAME_KEY));
        assertEquals(100, testClaimedPlayersAsJsonArray.getJSONObject(2).get(Player.JSON_PLAYERID_KEY));

    }

    @Test
    public void testRosterToJsonEmpty() {
        JSONArray testClaimedPlayersAsJsonArray = claimedPlayers.rosterToJson();

        assertTrue(testClaimedPlayersAsJsonArray.isEmpty());
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
