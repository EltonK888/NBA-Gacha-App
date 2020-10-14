package model;

import java.util.ArrayList;
import java.util.List;

// A class that is a collection of the claimed players
public class ClaimedPlayers extends PlayerRoster {

    public ClaimedPlayers() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: Adds the player to the claimed players list
    public boolean claimPlayer(Player p) {
        playerList.add(p);
        return true;
    }


    // REQUIRES: p must be in the claimed players list
    // MODIFIES: this, team
    // EFFECTS: Adds the player from the claimed list to the active team
    public void switchToActiveTeam(Team team, String playerName) {
        team.addPlayer(getPlayerByName(playerName));
        removePlayer(playerName);
    }

    // REQUIRES: p must be in the claimed players list
    // MODIFIES: this, team
    // EFFECTS: Swaps the player p1 from the claimed list to the active team with p2
    public void switchToActiveTeam(Team team, String p1Name, String p2Name) {
        // first remove the two players from the claimed and team list and store the objects
        Player removedPlayerFromTeamList = team.removePlayer(p2Name);
        Player removedPlayerFromClaimList = removePlayer(p1Name);

        // add the removed players to the other list
        team.addPlayer(removedPlayerFromClaimList);
        addPlayer(removedPlayerFromTeamList);

    }

    // MODIFIES: this
    // EFFECTS: Adds the player to the list
    public void addPlayer(Player p) {
        playerList.add(p);
    }

}
