package persistence;

import model.ClaimedPlayers;
import model.Player;
import model.PlayerRoster;
import model.Team;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter {
    private JsonWriter writer;
    private JsonReader reader;
    private ClaimedPlayers claimedPlayers;
    private Team activeTeamPlayers;
    private Player p1, p2, p3, p4, p5;

    @Test
    public void testWriterNoFileFound() {
        writer = new JsonWriter("./data/\0:invalidFilenam:e.json");

        try {
            writer.open();
            fail("Exception should have been thrown");
        } catch (FileNotFoundException e) {
            // expected to throw exception
        }
    }

    @Test
    public void testWriterEmptyRosters() {
        writer = new JsonWriter("./data/testWriterEmptyFile.json");
        reader = new JsonReader("./data/testWriterEmptyFile.json");
        claimedPlayers = new ClaimedPlayers();
        activeTeamPlayers = new Team();

        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }

        writer.write(claimedPlayers, activeTeamPlayers);
        writer.close();

        try {
            reader.setRosters();
        } catch (IOException e) {
            fail("Should not have thrown an exception");
        }

        assertEquals(0, reader.getSavedClaimedPlayers().size());
        assertEquals(0, reader.getSavedTeam().size());
    }

    @Test
    public void testWriterSomePlayersOnRosters() {
        writer = new JsonWriter("./data/testWriterGeneralSavedData.json");
        reader = new JsonReader("./data/testWriterGeneralSavedData.json");
        claimedPlayers = new ClaimedPlayers();
        activeTeamPlayers = new Team();

        try {
            createPlayerRosters();
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }

        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }

        writer.write(claimedPlayers, activeTeamPlayers);
        writer.close();

        try {
            reader.setRosters();
        } catch (IOException e) {
            fail("Should not have thrown an exception");
        }

        assertEquals(3, reader.getSavedClaimedPlayers().size());
        assertEquals(2, reader.getSavedTeam().size());

        assertTrue(reader.getSavedClaimedPlayers().hasPlayer("Christian Wood"));
        assertTrue(reader.getSavedClaimedPlayers().hasPlayer("Kevin Huerter"));
        assertTrue(reader.getSavedClaimedPlayers().hasPlayer("Delon Wright"));
        assertTrue(reader.getSavedTeam().hasPlayer("CJ McCollum"));
        assertTrue(reader.getSavedTeam().hasPlayer("George Hill"));
    }

    private void createPlayerRosters() throws IOException {
        p1 = new Player(90); // Christian Wood
        p2 = new Player(100); // Kevin Huerter
        p3 = new Player(200); // Delon Wright
        p4 = new Player(20); // CJ McCollum
        p5 = new Player(150); // George Hill

        claimedPlayers.addPlayer(p1);
        claimedPlayers.addPlayer(p2);
        claimedPlayers.addPlayer(p3);

        activeTeamPlayers.addPlayer(p4);
        activeTeamPlayers.addPlayer(p5);
    }
}
