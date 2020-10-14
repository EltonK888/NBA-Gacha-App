package model;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerRoster {
    List<Player> playerList;

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
                s += "Name: " + p.getName() + " Stars: " + p.getStars() + " Team: " + p.getTeam() + "\n";
            }
        }
        return s;
    }

    // EFFECTS: Returns the list of players
    public List<Player> getPlayerList() {
        return playerList;
    }

    // EFFECTS: Returns the number of players a user has claimed
    public int size() {
        return playerList.size();
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

    // REQUIRES: the with the parameter name must be in the player list
    // MODIFIES: this
    // EFFECTS: Removes and returns the player with the given name from the player list
    public Player removePlayer(String name) {
        Player p = getPlayerByName(name);
        playerList.remove(p);
        return p;
    }

    // EFFECTS: Returns a boolean which is true if the roster is empty
    public boolean isEmpty() {
        return playerList.isEmpty();
    }
}
