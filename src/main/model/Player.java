package model;

import java.util.HashMap;

// A class that represents an NBA Player
public class Player {
    public String name;
    public String position;
    public int tier;
    public int playerID;
    public HashMap<String, Integer> playerStats;

    public Player(String name, String position, int tier, int playerID) {
        this.name = name;
        this.position = position;
        this.tier = tier;
        this.playerID = playerID;
    }

    // EFFECTS: Returns the stats of the player e.g. tier, average points-per-game, rebounds, blocked shots, assists
    public HashMap<String, Integer> getStats() {
        return playerStats; // stub
    }
}
