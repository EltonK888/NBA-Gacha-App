package ui;

import model.ClaimedPlayers;
import model.Player;
import model.PlayerRoster;
import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


// Citation to basketball-reference.com for the player information
// 18 5 star players
// 37 4 star players
// 204 3 star players

// A class for the NBAGacha Application
public class NbaGachaApp {
    private static Random rand;
    public static Player currentPlayer;
    public static ClaimedPlayers claimed;
    public static Team team;
    private static JsonReader reader;
    private static JsonWriter writer;

    public static final int ID_INDEX = 0; // list index for the player ID
    public static final int NAME_INDEX = 1; // list index for the player name
    public static final int POSITION_INDEX = 2; // list index for the player position
    public static final int TEAM_INDEX = 4; // list index for the player's team
    public static final int MINUTES_PLAYED_INDEX = 5; // list index for how many minutes the player plays
    public static final int REBOUNDS_INDEX = 9; // list index for how many rebounds the player gets per game
    public static final int ASSIST_INDEX = 10; // list index for how many assists the player gets per game
    public static final int BLOCKS_INDEX = 12; // list index for how many blocks the player gets per game
    public static final int POINTS_INDEX = 13; // list index for how many points the player gets per game
    public static final int STAR_INDEX = 14; // the rank of a player (5 highest, 3 lowest)
    public static final int FIVE_STAR_ROLL_TABLE = 18; // determines the upper bound to roll on for the 5 star players
    public static final int FOUR_STAR_ROLL_TABLE = 55; // determines the upper bound to roll on for the 4 star players
    public static final int THREE_STAR_ROLL_TABLE = 259; // determines the upper bound to roll on for the 3 star players
    public static final double FIVE_STAR_ROLL_CHANCE = 0.05; // roll chance for a 5 star player
    public static final double FOUR_STAR_ROLL_CHANCE = 0.10; // roll chance for a 4 star player
    public static final double THREE_STAR_ROLL_CHANCE = 0.85; // roll chance for a 3 star player
    public static final String SAVED_DATA_PATH = "./data/savedData.json"; // path to the saved data

    // EFFECTS: constructs the app by creating the list of claimed players and teams. Also initialized the random int
    //          roller
    public NbaGachaApp() {
        rand = new Random();
        team = new Team();
        claimed = new ClaimedPlayers();
    }

    // MODIFIES: this
    // EFFECTS: Runs the main loop of the gacha game
    public void runApp() {
        //Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        new GUI();
    }

    public void mainLoop() {
        /*
        loadSavedData();
        System.out.println("Welcome to the NBA Gacha Game! Enter any key to Roll, 'C' for chances,"
                + " 'T' to see your players, or 'Q' to quit");
        String userInput = scanner.next();
        boolean run = !userInput.equalsIgnoreCase("q");
        while (run) {
            if (userInput.equalsIgnoreCase("c")) {
                System.out.println(printRollChances());
            } else if (userInput.equalsIgnoreCase("t")) {
                claimedMenu(scanner);
            } else {
                try {
                    rollPlayer(scanner);
                } catch (IOException e) {
                    System.out.println("Error occurred while rolling a player");
                }
            }
            userInput = scanner.next();
            run = !userInput.equalsIgnoreCase("q");
        }
        saveData(scanner);
         */
    }

    // REQUIRES: roll must correspond with a player ID in the database
    // MODIFIES: this
    // EFFECTS: Creates an NBA Player with the same ID as the role
    //          Throws an IOException if there was an error reading the player database
    public static Player generatePlayer(int roll) throws IOException {
        return new Player(roll);
    }

    // MODIFIES: this
    // EFFECTS: Adds the player to the claimed player list
    public static String claimPlayer(Player p) {
        claimed.addPlayer(p);
        return p.getName() + " has been claimed!";
    }

    // MODIFIES: this
    // EFFECTS: Rolls a player based off the roll chances and asks if the user would like to claim the player
    //          Throws IOException if there was an error reading the player database
    public static Player rollPlayer() throws IOException {
        double rollTable = rand.nextDouble(); // determines what star table to roll on
        int roll; // the integer that will be rolled

        if (rollTable < FIVE_STAR_ROLL_CHANCE) {
            // roll on the five star table
            roll = rand.nextInt(FIVE_STAR_ROLL_TABLE - 1) + 1;
        } else if (rollTable >= FIVE_STAR_ROLL_CHANCE && rollTable < FOUR_STAR_ROLL_CHANCE) {
            // roll on the four star table
            roll = rand.nextInt(FOUR_STAR_ROLL_TABLE - FIVE_STAR_ROLL_TABLE) + 1;
        } else {
            // roll on the three star table
            roll = rand.nextInt(THREE_STAR_ROLL_TABLE - FOUR_STAR_ROLL_TABLE) + 1;
        }

        currentPlayer = generatePlayer(roll);
        return currentPlayer;
        //System.out.println("You have rolled: a " + currentPlayer.getStars() + " star player, "
        //+ currentPlayer.toString());
        //System.out.println("Would you like to claim?\nEnter 'Y' to claim or any other key to continue");
        //String userInput = scanner.next();

        //if (userInput.equals("Y") || userInput.equals("y")) {
        //    System.out.println(claimPlayer(currentPlayer));
        //}
    }

    // MODIFIES: this
    // EFFECTS: Runs the menu where the user can see their claimed players and team
    public void claimedMenu(Scanner scanner) {
        String userInput;
        boolean run = true;
        while (run) {
            System.out.print("Press 'C' to see claimed players or 'T' to see the players on your active team, 'M' "
                    + "to modify your active team,");
            System.out.println(" and any other key to return to the main menu");
            userInput = scanner.next();
            if (userInput.equals("C") || userInput.equals("c")) {
                System.out.print(claimed.printPlayers());
            } else if (userInput.equals("T") || userInput.equals("t")) {
                System.out.print(team.printPlayers());
            } else if (userInput.equals("M") || userInput.equals("m")) {
                modifyTeam(scanner);
            } else {
                run = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Runs the menu where the user can choose to modify players on their team
    public void modifyTeam(Scanner scanner) {
        if (team.isEmpty() && claimed.isEmpty()) {
            System.out.println("You have no players on your team and claim list!");
        } else {
            boolean run = true;
            while (run) {
                System.out.println("Press 'A' to add a player from your claimed players to the team or 'R' to remove a"
                        + " player from the team or any other key to return to the main menu");
                String userInput = scanner.next();
                if (userInput.equals("A") || userInput.equals("a")) {
                    if (claimed.isEmpty()) {
                        System.out.println("You have no claimed players than you can add to your team!");
                    } else {
                        swapPlayerOntoTeam(scanner);
                    }
                } else if (userInput.equals("R") || userInput.equals("r")) {
                    removePlayerFromTeam(scanner);
                } else {
                    run = false;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Runs the menu where the user can choose to swap a player from the claimed roster to the active team
    public void swapPlayerOntoTeam(Scanner scanner) {
        System.out.println("What Player would you like to swap to the roster?");
        System.out.print(claimed.printPlayers());
        String userInput = scanner.next();
        if (claimed.hasPlayer(userInput)) {
            if (team.isFull()) {
                String playerName1 = userInput;
                System.out.println("Your team is full, who would you like to swap the player with?");
                String playerName2 = scanner.next();
                if (team.hasPlayer(playerName2)) {
                    claimed.switchToActiveTeam(team, playerName1, playerName2);
                    System.out.println(playerName2 + " was swapped onto the active team");
                } else {
                    System.out.println("Sorry, " + playerName2 + " is not on your team");
                }
            } else {
                claimed.switchToActiveTeam(team, userInput);
                System.out.println(userInput + " was swapped onto the active team");
            }
        } else {
            System.out.println("Sorry, you have not claimed this player yet.");
        }

    }

    // MODIFIES: this
    // EFFECTS: Runs the menu where the user can remove a player from the team to place back onto their claimed roster
    public void removePlayerFromTeam(Scanner scanner) {
        if (team.isEmpty()) {
            System.out.println("You don't have any players on your team.");
        } else {
            System.out.println("What Player would you like to remove from the team?");
            System.out.print(team.printPlayers());
            String userInput = scanner.next();
            if (team.hasPlayer(userInput)) {
                Player removedPlayer = team.removePlayer(userInput);
                claimed.addPlayer(removedPlayer);
                System.out.println(removedPlayer.getName() + " swapped from the team to the claimed roster");
            } else {
                System.out.println("This player is not on your team.");
            }
        }
    }

    // EFFECTS: Returns a string with the roll chances of the 5 star, 4 star, and 3 star roll tables
    public static String printRollChances() {
        return "The chances of rolling a 5 star player is: " + FIVE_STAR_ROLL_CHANCE * 100 + "%.\nThe chances of "
                + "rolling a 4 star players is: " + FOUR_STAR_ROLL_CHANCE * 100 + "%.\nThe chances of rolling a 3 "
                + "star player is: " + THREE_STAR_ROLL_CHANCE * 100 + "%.";

    }

    // MODIFIES: this
    // EFFECTS: Loads the saved data from the json file
    public static Map<String, PlayerRoster> loadSavedData() {
        //System.out.println("Would you like to load your saved data? Enter 'Y' for yes or anything else to continue");
        //String userInput = scanner.next();
        //if (userInput.equalsIgnoreCase("y")) {
        Map<String, PlayerRoster> rosters = new HashMap<String, PlayerRoster>();
        reader = new JsonReader(SAVED_DATA_PATH);
        try {
            reader.setRosters();
            team = reader.getSavedTeam();
            claimed = reader.getSavedClaimedPlayers();
            rosters = new HashMap<String, PlayerRoster>();
            rosters.put("team", team);
            rosters.put("claimed", claimed);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading saved data");
        }
        return rosters;
    }

    // MODIFIES: this
    // EFFECTS: Writes the saved data from the app to a json file
    public static void saveData(ClaimedPlayers claimed, Team team) {
        //System.out.println("Would you like to overwrite your saved data? Enter 'Y' to overwrite or any other key");
        //String userInput = scanner.next();
        //if (userInput.equalsIgnoreCase("y")) {
        writer = new JsonWriter(SAVED_DATA_PATH);
        try {
            writer.open();
            writer.write(claimed, team);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("There was an error saving your data");
        }
        //}
    }
}
