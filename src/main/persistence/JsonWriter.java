package persistence;

import model.ClaimedPlayers;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/* Credit to the JsonSerializationDemo project posted by CPSC 210. Most of the reading and writing json logic is
 * taken from their repository at the following link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens the writer IOStream so that data can be written to file
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: Closes the writer IOStream
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes the data to the file
    public void write(ClaimedPlayers claimedPlayers, Team team) {
        JSONObject savedDataJsonObject = new JSONObject();
        JSONObject writtenJsonObject = new JSONObject();

        JSONArray claimedPlayersAsJsonArray = claimedPlayers.rosterToJson();
        JSONArray teamAsJsonArray = team.rosterToJson();

        savedDataJsonObject.put(JsonKeyConstants.SAVED_CLAIMED_PLAYERS_KEY, claimedPlayersAsJsonArray);
        savedDataJsonObject.put(JsonKeyConstants.SAVED_TEAM_PLAYERS_KEY, teamAsJsonArray);

        writtenJsonObject.put(JsonKeyConstants.SAVED_DATA_KEY, savedDataJsonObject);

        writer.print(writtenJsonObject.toString(JsonKeyConstants.TAB));
    }
}
