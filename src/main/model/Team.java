package model;

import java.util.ArrayList;
import java.util.List;

// A class
public class Team {
    public static final int TEAM_SIZE = 15; // maximum number of players than can be on one team

    List<Player> team;

    public Team() {
        team = new ArrayList<>(TEAM_SIZE);
    }

    // EFFECTS: Returns the active team roster
    public List<Player> getTeam() {
        return team;
    }
}
