package persistence;

// A class for the keys used in the saved json data files
public interface JsonKeyConstants {
    String SAVED_CLAIMED_PLAYERS_KEY = "savedClaimedPlayers"; // key for the claimed players list
    String SAVED_TEAM_PLAYERS_KEY = "savedActiveTeam"; // key for the active team list
    String SAVED_DATA_KEY = "savedData"; // key for the active team and claimed players objects
    int TAB = 4; // tab spacing for writing to the saved data json files
}
