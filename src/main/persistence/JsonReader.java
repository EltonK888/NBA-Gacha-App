package persistence;

import model.ClaimedPlayers;
import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/* Credit to the JsonSerializationDemo project posted by CPSC 210. Most of the reading and writing json logic is
 * taken from their repository at the following link https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReader {
    private String source;
    public Team savedTeam;
    public ClaimedPlayers savedClaimedPlayers;

    // EFFECTS: Constructs a JsonReader and instantiates the rosters
    public JsonReader(String source) {
        this.source = source;
        savedTeam = new Team();
        savedClaimedPlayers = new ClaimedPlayers();
    }

    // MODIFIES: this
    // EFFECTS: Creates the roster based off the players in the saved data
    public void setRosters() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        buildBuildRoster(jsonObject, JsonKeyConstants.SAVED_TEAM_PLAYERS_KEY);
        buildBuildRoster(jsonObject, JsonKeyConstants.SAVED_CLAIMED_PLAYERS_KEY);
    }

    // EFFECTS: Reads the json file into a string
    //          - Throws an IOException if there was an error opening the file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: this
    // EFFECTS: Builds the player roster based off the saved data
    //          - Throws an IOException if there was an error opening an IOStream from the database
    private void buildBuildRoster(JSONObject jsonObject, String rosterType) throws IOException {
        JSONArray jsonObjectSavedData = jsonObject.getJSONObject(
                JsonKeyConstants.SAVED_DATA_KEY).getJSONArray(rosterType);

        for (Object json: jsonObjectSavedData) {
            JSONObject json1 = (JSONObject) json;

            if (rosterType.equals(JsonKeyConstants.SAVED_TEAM_PLAYERS_KEY)) {
                savedTeam.addPlayer(new Player(json1.getInt(Player.JSON_PLAYERID_KEY)));
            } else if (rosterType.equals(JsonKeyConstants.SAVED_CLAIMED_PLAYERS_KEY)) {
                savedClaimedPlayers.addPlayer(new Player(json1.getInt(Player.JSON_PLAYERID_KEY)));
            }
        }
    }

    // EFFECTS: Returns the active team that was saved to the json file
    public Team getSavedTeam() {
        return savedTeam;
    }

    // EFFECTS: Returns the claimed players that was saved to the json file
    public ClaimedPlayers getSavedClaimedPlayers() {
        return savedClaimedPlayers;
    }
}
