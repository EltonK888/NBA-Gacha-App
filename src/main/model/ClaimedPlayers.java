package model;

import java.util.ArrayList;
import java.util.List;

// A class that is a collection of the claimed players
public class ClaimedPlayers {
    List<Player> claimedPlayers;

    public ClaimedPlayers() {
        claimedPlayers = new ArrayList<>();
    }

    public void claimPlayer(Player p) {
        claimedPlayers.add(p);
    }

    // EFFECTS: Returns the players that a user as claimed
    public List<Player> getClaimedPlayers() {
        return claimedPlayers;
    }

    public int size() {
        return claimedPlayers.size();
    }
}
