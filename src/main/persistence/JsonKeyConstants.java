package persistence;

// A class for the keys used in the saved json data files
public final class JsonKeyConstants {
    public static final String SAVED_CLAIMED_PLAYERS_KEY = "savedClaimedPlayers"; // key for the claimed players list
    public static final String SAVED_TEAM_PLAYERS_KEY = "savedActiveTeam"; // key for the active team list
    public static final String SAVED_DATA_KEY = "savedData"; // key for the active team and claimed players objects
    public static final int TAB = 4; // tab spacing for writing to the saved data json files
}

// TODO: change this interface into a constants final class http://www.javapractices.com/topic/TopicAction.do?Id=2