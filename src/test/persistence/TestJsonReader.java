package persistence;

import model.PlayerRoster;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader {
    private JsonReader jsonReader;

    @Test
    public void testSetRostersFileNotFound() {
        jsonReader = new JsonReader("./data/notAFile.json");

        try {
            jsonReader.setRosters();
            fail("Exception should have been thrown");
        } catch (IOException e) {
            // expected
        }

        assertEquals(0, jsonReader.getSavedTeam().size());
        assertEquals(0, jsonReader.getSavedClaimedPlayers().size());
    }

    @Test
    public void testSetRostersEmptyFile() {
        jsonReader = new JsonReader("./data/testReaderEmptyData.json");

        try {
            jsonReader.setRosters();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

        assertEquals(0, jsonReader.getSavedTeam().size());
        assertEquals(0, jsonReader.getSavedClaimedPlayers().size());
    }

    @Test
    public void testSetRostersGeneralFile() {
        jsonReader = new JsonReader("./data/testReaderGeneralSavedData.json");

        try {
            jsonReader.setRosters();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

        PlayerRoster loadedClaimedPlayers = jsonReader.getSavedClaimedPlayers();
        PlayerRoster loadedTeamPlayers = jsonReader.getSavedTeam();;
        assertEquals(5, loadedClaimedPlayers.size());
        assertEquals(3, loadedTeamPlayers.size());

        assertTrue(loadedClaimedPlayers.hasPlayer("Damian Lillard"));
        assertTrue(loadedClaimedPlayers.hasPlayer("Tyler Herro"));
        assertTrue(loadedClaimedPlayers.hasPlayer("Fred VanVleet"));
        assertTrue(loadedClaimedPlayers.hasPlayer("Kevin Knox"));
        assertTrue(loadedClaimedPlayers.hasPlayer("Andre Drummond"));

        assertTrue(loadedTeamPlayers.hasPlayer("James Harden"));
        assertTrue(loadedTeamPlayers.hasPlayer("Pascal Siakam"));
        assertTrue(loadedTeamPlayers.hasPlayer("Bojan Bogdanovic"));
    }
}
