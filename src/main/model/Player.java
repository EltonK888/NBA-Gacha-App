package model;

import java.util.HashMap;
import java.util.List;
import ui.NbaGachaApp;

// A class that represents an NBA Player
public class Player {
    public String name;
    public String position;
    public String team;
    public int stars;
    public int playerID;
    public HashMap<String, Double> playerStats;

    /* REQUIRES: playerData must have size of 15 containing the data of the given player the following indices must
    *           contain the values: 0 = rank, 1 = name, 2 = position, 3 = age, 4 = team, 5 = minutes, 6 = field goals
    *           7 = field goal attempts, 8 = field goal percentage 9 = rebounds, 10 = assists, 11 = steals,
    *           12 = blocks, 13 = points, 14 Stars.
    *           Rank, age and stars are integers represented as strings
    *           Minutes, field goals, field goal attempts, field goal percentage, rebounds, assists, steals, blocks,
    *           points are doubles represented as strings
    *           stars must be a integer represented as a string from 3-5 inclusive
    * EFFECTS: Constructs a new basketball player with their information
    */
    public Player(List<String> playerData) {
        this.name = playerData.get(NbaGachaApp.NAME_INDEX);
        this.position = playerData.get(NbaGachaApp.POSITION_INDEX);
        this.stars = Integer.parseInt(playerData.get(NbaGachaApp.STAR_INDEX));
        this.playerID = Integer.parseInt(playerData.get(NbaGachaApp.ID_INDEX));
        this.team = playerData.get(NbaGachaApp.TEAM_INDEX);
        setPlayerStats(playerData);
    }

    /* REQUIRES: playerData must have size of 15 containing the data of the given player the following indices must
    *           contain the values: 0 = rank, 1 = name, 2 = position, 3 = age, 4 = team, 5 = minutes, 6 = field goals
    *           7 = field goal attempts, 8 = field goal percentage 9 = rebounds, 10 = assists, 11 = steals,
    *           12 = blocks, 13 = points, 14 Stars.
    * MODIFIES: this
    * EFFECTS: Creates a hashmap with the keys being "minutes", "rebounds", "assists", "blocks", "points", mapping
    *          to the corresponding stat value of the player
    */
    public void setPlayerStats(List<String> playerData) {
        this.playerStats = new HashMap<>();

        // pull the data from the List
        Double minutesPlayed = Double.parseDouble(playerData.get(NbaGachaApp.MINUTES_PLAYED_INDEX));
        Double rebounds = Double.parseDouble(playerData.get(NbaGachaApp.REBOUNDS_INDEX));
        Double assists = Double.parseDouble(playerData.get(NbaGachaApp.ASSIST_INDEX));
        Double blocks = Double.parseDouble(playerData.get(NbaGachaApp.BLOCKS_INDEX));
        Double points = Double.parseDouble(playerData.get(NbaGachaApp.POINTS_INDEX));

        // set the hashmap up with the keys and their corresponding values
        playerStats.put("minutes", minutesPlayed);
        playerStats.put("rebounds", rebounds);
        playerStats.put("assists", assists);
        playerStats.put("blocks", blocks);
        playerStats.put("points", points);
    }

    // EFFECTS: Returns the stats of the player (minutes, assists, rebounds, blocks, points)
    public HashMap<String, Double> getStats() {
        return playerStats; // stub
    }

    // EFFECTS: Returns the name of the player
    public String getName() {
        return name;
    }

    // EFFECTS: Returns the team the player plays for
    public String getTeam() {
        return team;
    }

    // EFFECTS: Returns the position that the player usually plays
    public String getPosition() {
        return position;
    }

    // EFFECTS: Returns the ID of the player
    public int getPlayerID() {
        return playerID;
    }

    // EFFECTS: Returns the tier of the player
    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return name + " " + position + " " + team;
    }
}
