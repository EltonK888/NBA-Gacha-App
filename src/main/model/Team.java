package model;

import java.util.ArrayList;
import java.util.List;

// A class that represents the players on the user's active team
public class Team extends PlayerRoster {
    public static final int MAX_TEAM_SIZE = 15; // maximum number of players than can be on one team


    public Team() {
        super();
    }


    // MODIFIES: this
    // EFFECTS: Adds the player to the team
    @Override
    public void addPlayer(Player p) {
        if (!this.isFull()) {
            playerList.add(p);
        }
    }

    public boolean isFull() {
        return playerList.size() == MAX_TEAM_SIZE;
    }
}
