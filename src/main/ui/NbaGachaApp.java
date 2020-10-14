package ui;

import model.ClaimedPlayers;
import model.Player;
import model.Team;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


// Citation to basketball-reference.com for the player information
// 18 5 star players
// 37 4 star players
// 204 3 star players


// A class for the Gacha Application
public class NbaGachaApp {
    private Random rand;
    private BufferedReader csvReader;
    public Player currentPlayer;
    public ClaimedPlayers claimed;
    public Team team;

    private static final String PATH_TO_CSV = "data/sportsref_download.csv";
    public static final int ID_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int POSITION_INDEX = 2;
    public static final int TEAM_INDEX = 4;
    public static final int MINUTES_PLAYED_INDEX = 5;
    public static final int REBOUNDS_INDEX = 9;
    public static final int ASSIST_INDEX = 10;
    public static final int BLOCKS_INDEX = 12;
    public static final int POINTS_INDEX = 13;
    public static final int STAR_INDEX = 14;
    public static final int FIVE_STAR_ROLL_TABLE = 18;
    public static final int FOUR_STAR_ROLL_TABLE = 55;
    public static final int THREE_STAR_ROLL_TABLE = 259;
    public static final double FIVE_STAR_ROLL_CHANCE = 0.05;
    public static final double FOUR_STAR_ROLL_CHANCE = 0.10;
    public static final double THREE_STAR_ROLL_CHANCE = 0.85;

    public NbaGachaApp() {
        rand = new Random();
        team = new Team();
        claimed = new ClaimedPlayers();
    }

    // MODIFIES: this, currentPlayer, claimed, team, csvReader
    // EFFECTS: Runs the main loop of the gacha game
    public void runApp() throws IOException {
        System.out.println("Welcome to the NBA Gacha Game! Enter any key to Roll, 'C' for chances,"
                + " 'T' to see your players, or 'Q' to quit");
        Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
        String userInput = scanner.next();
        boolean run = userInput.equals("Q") || userInput.equals("q");
        while (!run) {
            if (userInput.equals("C") || userInput.equals("c")) {
                System.out.println(printRollChances());
            } else if (userInput.equals("T") || userInput.equals("t")) {
                claimedMenu(scanner);
            } else {
                rollPlayer(scanner);
            }
            System.out.println("Enter any key to roll again, 'C' for chances, 'T' to see your players, or 'Q' to quit");
            userInput = scanner.next();
            run = userInput.equals("Q") || userInput.equals("q");
        }
    }

    // MODIFIES: this, currentPlayer
    // EFFECTS: Creates an NBA Player with the same ID as the role
    public Player generatePlayer(int roll) throws IOException {
        Player p;
        int playerID;

        readCSV(); // generate the file reader for the csv
        csvReader.readLine(); // read the first line through because it's just the headers
        String row = csvReader.readLine();

        // create a loop that reads until we find the player in the csv file
        while (row != null) {
            List<String> playerData = Arrays.asList(row.split(","));
            playerID = Integer.parseInt(playerData.get(ID_INDEX));
            if (playerID == roll) {
                p = new Player(playerData); // create the player
                return p;
            } else {
                row = csvReader.readLine();
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Opens the IOStream to read the CSV file containing the player information
    public void readCSV() {
        try {
            csvReader = new BufferedReader(new FileReader(PATH_TO_CSV));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this, claimed
    // EFFECTS: Adds the player to the claimed player list
    public String claimPlayer(Player p) {
        boolean success;
        success = claimed.claimPlayer(p);
        if (success) {
            return p.getName() + " has been claimed!";
        } else {
            return "There was an error claiming " + p.getName();
        }
    }

    // MODIFIES: scanner, rand, currentPlayer
    // EFFECTS: Rolls a player based off the roll chances and asks if the user would like to claim the player
    public void rollPlayer(Scanner scanner) throws IOException {
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
        System.out.println("You have rolled: a " + currentPlayer.getStars() + " star player, "
                + currentPlayer.toString());
        System.out.println("Would you like to claim?\nEnter 'Y' to claim or any other key to continue");
        String userInput = scanner.next();

        if (userInput.equals("Y") || userInput.equals("y")) {
            System.out.println(claimPlayer(currentPlayer));
        }
    }

    // MODIFIES: scanner
    // EFFECTS: Runs the menu where the user can see their claimed players and team
    public void claimedMenu(Scanner scanner) {
        String userInput;
        boolean run = true;
        while (run) {
            System.out.print("Press 'C' to see claimed players or 'T' to see the players on your active team, 'S' "
                    + "to switch players from your claimed roster to your active team,");
            System.out.println(" and any other key to return to the main menu");
            userInput = scanner.next();
            if (userInput.equals("C") || userInput.equals("c")) {
                System.out.print(claimed.printPlayers());
            } else if (userInput.equals("T") || userInput.equals("t")) {
                System.out.print(team.printPlayers());
            } else if (userInput.equals("S") || userInput.equals("s")) {
                switchPlayersClaimedToTeam(scanner);
            } else {
                run = false;
            }
        }
    }

    // MODIFIES: scanner, claimed, team
    // EFFECTS: Runs the menu where the user can swap a player from the claimed players to the team
    public void switchPlayersClaimedToTeam(Scanner scanner) {
        System.out.println("What player would you like to add to the roster?");
        String userInput = scanner.next();
        if (claimed.hasPlayer(userInput)) {
            if (team.isFull()) {
                String playerName1 = userInput;
                System.out.println("Your team is full, who would you like to swap the player with?");
                String playerName2 = scanner.next();
                if (team.hasPlayer(playerName2)) {
                    claimed.switchToActiveTeam(team, playerName1, playerName2);
                    System.out.println(userInput + " was swapped onto the active team");
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


    // EFFECTS: Returns a string with the roll chances of the 5 star, 4 star, and 3 star roll tables
    public String printRollChances() {
        return "The chances of rolling a 5 star player is: " + FIVE_STAR_ROLL_CHANCE * 100 + "%.\nThe chances of "
                + "rolling a 4 star players is: " + FOUR_STAR_ROLL_CHANCE * 100 + "%.\nThe chances of rolling a 3 "
                + "star player is: " + THREE_STAR_ROLL_CHANCE * 100 + "%.";

    }
}
