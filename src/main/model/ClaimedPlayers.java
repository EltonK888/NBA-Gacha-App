package model;

// A class that represents a collection of the claimed players
public class ClaimedPlayers extends PlayerRoster {

    // EFFECTS: Constructs the claimed players roster
    public ClaimedPlayers() {
        super();
    }

    // MODIFIES: this
    // EFFECTS: Adds the player to the claimed players list
    @Override
    public void addPlayer(Player p) {
        playerList.add(p);
    }


    // REQUIRES: A player with the playerName must be in the claimed players roster
    //           - the team must have room to add the player
    // MODIFIES: this, team
    // EFFECTS: Adds the player from the claimed roster to the active team, and removes it from the claimed roster
    public void switchToActiveTeam(Team team, String playerName) {
        team.addPlayer(getPlayerByName(playerName));
        removePlayer(playerName);
    }

    // REQUIRES: The player that we want to put on the team must exist in the claimed players roster
    //           - the team must be full so that we're just swapping players from the claimed roster to the team
    //           - the name of the player we're removing from the team must exist in the team
    // MODIFIES: this, team
    // EFFECTS: Swaps the player with the name nameOfPlayerToPutOnTeam from the claimed list to the active team with
    //          - the player with the name nameOfPlayerToRemoveFromTeam
    public void switchToActiveTeam(Team team, String nameOfPlayerToPutOnTeam, String nameOfPlayerToRemoveFromTeam) {
        // first remove the two players from the claimed and team list and store the objects
        Player removedPlayerFromTeamList = team.removePlayer(nameOfPlayerToRemoveFromTeam);
        Player removedPlayerFromClaimList = removePlayer(nameOfPlayerToPutOnTeam);

        // add the removed players to the other list
        team.addPlayer(removedPlayerFromClaimList);
        addPlayer(removedPlayerFromTeamList);

    }

}
