package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// An abstract class that determines behaviour for the types of player lists
public abstract class PlayerRoster {
    List<Player> playerList;

    // EFFECTS: Constructs a player list to hold a collection of players
    public PlayerRoster() {
        playerList = new ArrayList<>();
    }

    // EFFECTS: Returns a list of claimed players in a format of:
    //          Name: <player name>, Stars: <player stars>, Team: <team name>
    public String printPlayers() {
        String s = "";
        if (isEmpty()) {
            s = "There are no players!\n";
        } else {
            for (Player p : playerList) {
                s += p.toString() + "\n";
            }
        }
        return s;
    }

    // EFFECTS: Returns the list of players
    public List<Player> getPlayerList() {
        return playerList;
    }

    // REQUIRES: The player must be in the player roster
    // EFFECTS: Returns the player object that matches the name parameter
    public Player getPlayerByName(String name) {
        for (Player p: playerList) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    // EFFECTS: Checks to see if the user has claimed a player with the given name
    public boolean hasPlayer(String playerName) {
        for (Player claimedPlayer: playerList) {
            if (claimedPlayer.getName().equals(playerName)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: The player with the name must be in the player list
    // MODIFIES: this
    // EFFECTS: Removes and returns the player with the given name from the player list
    public Player removePlayer(String name) {
        Player p = getPlayerByName(name);
        playerList.remove(p);
        return p;
    }

    // EFFECTS: Returns the roster of players as a JSON array
    public JSONArray rosterToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p: playerList) {
            jsonArray.put(p.playerToJson());
        }

        return jsonArray;
    }

    // EFFECTS: Returns the number of players a user has claimed
    public int size() {
        return playerList.size();
    }

    // EFFECTS: Returns a boolean which is true if the roster is empty
    public boolean isEmpty() {
        return playerList.isEmpty();
    }

    // EFFECTS: Adds a player to the roster
    public abstract void addPlayer(Player p);
}
