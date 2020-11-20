package ui;

import model.ClaimedPlayers;
import model.Player;
import model.PlayerRoster;
import model.Team;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class GUI extends JFrame {
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    ImageIcon image = new ImageIcon("./src/assets/basketball-icon.png");
    Image backgroundImage = new ImageIcon("./src/assets/cool-background.png").getImage();
    BackgroundPanel bgPanel = new BackgroundPanel(backgroundImage);
    public static GridBagConstraints constraints = new GridBagConstraints();
    JButton loadButton;
    JButton newGameButton;
    JButton rollButton;
    JButton claimButton;
    JButton viewChancesButton;
    JButton viewRostersButton;
    JButton modifyTeamButton;
    JButton modifyClaimedPlayers;
    JButton quitButton;
    JButton removeButton;
    JButton addButton;
    JDialog rostersWindow;
    JLabel label;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JList roster;
    Player currentPlayer;
    Team team;
    ClaimedPlayers claimedPlayers;
    String[] modifyOrView = {"Modify", "View"};
    String[] claimedOrTeam = {"Claimed Players", "Current Team"};
    String[] addOrRemove = {"Add", "Remove"};

    // EFFECTS: Constructs the GUI that the user will interact with
    public GUI() {
        createFrame();
        createButtons();

        bgPanel.setLayout(new GridBagLayout());
        bgPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        createLabels();
        createIntroButtons();
    }

    // MODIFIES: this
    // EFFECTS: Creates the initial load/new game buttons when the user opens the application
    private void createIntroButtons() {
        constraints.gridy = 4;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(200, 50, 0, 0);
        constraints.fill = GridBagConstraints.NONE;
        bgPanel.add(loadButton, constraints);

        constraints.gridx = 3;
        bgPanel.add(newGameButton, constraints);

        constraints.gridy = 5;
        constraints.gridx = 0;
        constraints.insets = new Insets(20, 0, 0, 0);
        claimButton.setVisible(false);
        bgPanel.add(claimButton, constraints);
    }

    // MODIFIES: this
    // EFFECTS: Creates the frame of the game
    public void createFrame() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("NBA Gacha Game");
        setContentPane(bgPanel);
        setLocationRelativeTo(null);
        setIconImage(image.getImage());
        setVisible(true);
        pack();
    }


    // MODIFIES: this
    // EFFECTS: Creates the buttons that are used in the applications
    public void createButtons() {
        loadButton = new Button("Load Data");
        newGameButton = new Button("New Game");
        rollButton = new Button("Roll Player");
        claimButton = new Button("Claim!");
        viewChancesButton = new Button("View Chances");
        viewRostersButton = new Button("View Rosters");
        modifyTeamButton = new Button("Modify Team");
        modifyClaimedPlayers = new Button("Modify Claimed Roster");
        removeButton = new Button("Remove to claimed roster");
        addButton = new Button("Add to current team");
        quitButton = new Button("Quit");
        setOnclickBehaviours();
    }

    // MODIFIES: this
    // EFFECTS: Sets the onclick behaviour for each button used in the game
    public void setOnclickBehaviours() {
        setLoadButtonBehaviour();

        setNewGameButtonBehaviour();

        setRollButtonBehaviour();

        setViewChancesButtonBehaviour();

        setClaimButtonBehaviour();

        setQuitButtonBehaviour();

        setViewRostersButtonBehaviour();

        setAddButtonBehaviour();

        setRemoveButtonBehaviour();
    }

    // MODIFIES: this
    // EFFECTS: Creates the functionality when view rosters button is clicked. Either view or modify the rosters
    private void setViewRostersButtonBehaviour() {
        viewRostersButton.addActionListener(e -> {
            int addOrRemoveSelection = JOptionPane.NO_OPTION;
            int claimedOrTeamSelection = JOptionPane.NO_OPTION;

            int modifyOrViewSelection = createOptionDialog(
                    modifyOrView, "Would you like to modify or view your rosters?");

            if (modifyOrViewSelection == JOptionPane.YES_OPTION) { // YES for modify NO for view
                addOrRemoveSelection = createOptionDialog(
                        addOrRemove, "Do you want to add or remove players from your team?");

                if (addOrRemoveSelection == JOptionPane.YES_OPTION) { // YES for claimed players NO for team
                    claimedOrTeamSelection = JOptionPane.YES_OPTION; // claimed players
                } else {
                    claimedOrTeamSelection = JOptionPane.NO_OPTION; // current team
                }

            } else if (modifyOrViewSelection == JOptionPane.NO_OPTION) { // view
                claimedOrTeamSelection = createOptionDialog(
                        claimedOrTeam, "Which roster would you like to view?");
            }

            if (!claimedPlayers.isEmpty() || !team.isEmpty()) {
                viewPlayers(claimedOrTeamSelection, modifyOrViewSelection, addOrRemoveSelection);
            } else { // if there's no players, then show a message dialogue instead
                JOptionPane.showMessageDialog(this, "You have no players on your rosters!");
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the button that adds a player from the claimed players to the current team
    private void setAddButtonBehaviour() {
        addButton.addActionListener(event -> {
            try {
                Player playerToAdd = claimedPlayers.getPlayerList().get(roster.getSelectedIndex());
                if (!team.isFull()) {
                    claimedPlayers.switchToActiveTeam(team, playerToAdd.getName());
                } else {
                    JOptionPane.showMessageDialog(this, "Your current team is full!");
                }
                rostersWindow.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "You need to select a player!");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the button that removes a player from the current team back to the claimed roster
    private void setRemoveButtonBehaviour() {
        removeButton.addActionListener(event -> {
            try {
                Player playerToRemove = team.getPlayerList().get(roster.getSelectedIndex());
                team.removePlayer(playerToRemove.getName());
                claimedPlayers.addPlayer(playerToRemove);
                rostersWindow.dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "You need to select a player!");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the button when the user decides to claim a roll
    private void setClaimButtonBehaviour() {
        claimButton.addActionListener(e -> {
            playSound();
            claimedPlayers.addPlayer(currentPlayer);
            JOptionPane.showMessageDialog(this, "Player claimed!");
            claimButton.setEnabled(false);
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the display roll chances button
    private void setViewChancesButtonBehaviour() {
        viewChancesButton.addActionListener(e -> {
            String[] chancesList = NbaGachaApp.printRollChances().split("\n");
            displayChances(chancesList);
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour when someone rolls a player
    private void setRollButtonBehaviour() {
        rollButton.addActionListener(e -> {
            System.out.println(e.getActionCommand());
            try {
                currentPlayer = NbaGachaApp.rollPlayer();
                String[] currentPlayerAsStringArray = currentPlayer.toString().split(":");
                displayPlayer(currentPlayerAsStringArray);
                claimButton.setEnabled(true);
            } catch (IOException exception) {
                System.out.println("Error rolling player");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the new game button. Starts the game with a new team and new claimed roster
    private void setNewGameButtonBehaviour() {
        newGameButton.addActionListener(e -> {
            System.out.println(e.getActionCommand());
            team = new Team();
            claimedPlayers = new ClaimedPlayers();
            setMainVisible();
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the load game button. Starts the game with the saved team
    private void setLoadButtonBehaviour() {
        loadButton.addActionListener(e -> {
            System.out.println(e.getActionCommand());
            Map<String, PlayerRoster> rosters = NbaGachaApp.loadSavedData();
            team = (Team) rosters.get("team");
            claimedPlayers = (ClaimedPlayers) rosters.get("claimed");
            setMainVisible();
        });
    }

    // MODIFIES: this
    // EFFECTS: Sets the behaviour of the quit button. Prompts the user to save the data before closing the application
    private void setQuitButtonBehaviour() {
        quitButton.addActionListener(e -> {
            int input = JOptionPane.showConfirmDialog(this, "Would you like to save data?");
            if (input == JOptionPane.YES_OPTION) {
                NbaGachaApp.saveData(claimedPlayers, team);
            }
            System.exit(0);
        });
    }

    // MODIFIES: this
    // EFFECTS: Displays the rolled player on the GUI
    public void displayPlayer(String[] currentPlayerAsStringArray) {
        String playerName = "Name:" + currentPlayerAsStringArray[1].substring(0,
                currentPlayerAsStringArray[1].length() - 5);
        String playerStars = "Stars:" + currentPlayerAsStringArray[2].substring(0, 2);
        String playerPosition = "Position:" + currentPlayerAsStringArray[3].substring(0, 3);
        String playerTeam = "Team:" + currentPlayerAsStringArray[4];

        label.setText(playerName);
        label2.setText(playerStars);
        label3.setText(playerPosition);
        label4.setText(playerTeam);

        claimButton.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Creates the JList to display the roster and sets up the components for the pop up frame
    private void viewPlayers(int rosterType, int modifyOrView, int addOrRemove) {
        JScrollPane scrollPane;
        JLabel rostersFrameText = new Label("");
        rostersWindow = new JDialog(this, "Player Roster", true);


        if (rosterType == JOptionPane.YES_OPTION) { // claimed players
            roster = new JList<>(claimedPlayers.getPlayerList().toArray());
            rostersFrameText.setText("Claimed Players");
        } else if (rosterType == JOptionPane.NO_OPTION) { // current team
            roster = new JList<>(team.getPlayerList().toArray());
            rostersFrameText.setText("Current Team");
        }

        scrollPane = new JScrollPane(roster);


        createRosterFrame(
                rostersFrameText, scrollPane, rostersWindow, modifyOrView, addOrRemove, removeButton, addButton);
    }

    // MODIFIES: this
    // EFFECTS: Creates a pop up dialog with the given options and the string
    private int createOptionDialog(String[] options, String s) {
        return JOptionPane.showOptionDialog(this, s,
                "NBA Gacha Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, null);
    }

    // MODIFIES: this
    // EFFECTS: Creates the frame for the window that pops up when a user wants to view/modify their rosters
    private void createRosterFrame(
            JLabel rostersFrameText, JScrollPane scrollPane, JDialog rostersWindow,
            int modifyOrView, int addOrRemove, JButton removeButton, JButton addButton) {
        rostersWindow.setLayout(new BorderLayout());
        rostersWindow.setMinimumSize(new Dimension(500, 600));
        rostersWindow.setTitle("Player Roster");
        rostersWindow.setLocationRelativeTo(null);
        rostersWindow.setIconImage(image.getImage());
        rostersWindow.add(rostersFrameText, BorderLayout.NORTH);
        rostersWindow.add(scrollPane, BorderLayout.CENTER);

        if (addOrRemove == JOptionPane.YES_OPTION) { // determine if user wants to add players or remove from rosters
            rostersWindow.add(addButton, BorderLayout.SOUTH);
            addButton.setVisible(true);
        } else if (addOrRemove == JOptionPane.NO_OPTION) {
            rostersWindow.add(removeButton, BorderLayout.SOUTH);
            removeButton.setVisible(true);
        }
        if (modifyOrView == JOptionPane.NO_OPTION) { // only view
            removeButton.setVisible(false);
            addButton.setVisible(false);
        }

        rostersWindow.setVisible(true);
        rostersWindow.validate();
        rostersWindow.pack();
    }

    // MODIFIES: this
    // EFFECTS: Displays the roll chances on the GUI
    public void displayChances(String[] chancesList) {
        claimButton.setVisible(false);
        label.setText(chancesList[0]);
        label2.setText(chancesList[1]);
        label3.setText(chancesList[2]);
        label4.setText("");
    }

    /// MODIFIES: this
    // EFFECTS: Sets the layout of the main game screen where the user can roll and view their rosters
    public void setMainVisible() {
        claimButton.setVisible(false);
        loadButton.setVisible(false);
        newGameButton.setVisible(false);
        constraints.gridy = 4;
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(200, 25, 0, 25);
        bgPanel.add(rollButton, constraints);

        constraints.gridx = 1;
        bgPanel.add(viewChancesButton, constraints);

        constraints.gridx = 2;
        bgPanel.add(viewRostersButton, constraints);

        constraints.gridx = 3;
        bgPanel.add(quitButton, constraints);

        bgPanel.validate();
    }

    // MODIFIES: this
    // EFFECTS: Creates labels to display text on the GUI
    public void createLabels() {
        label = new Label("Welcome to NBA Gacha Game!");
        label2 = new Label("");
        label3 = new Label("");
        label4 = new Label("");

        constraints.gridx = 0;
        constraints.gridwidth = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        bgPanel.add(label, constraints);

        constraints.gridy = 1;
        bgPanel.add(label2, constraints);

        constraints.gridy = 2;
        bgPanel.add(label3, constraints);

        constraints.gridy = 3;
        bgPanel.add(label4, constraints);
    }

    // MODIFIES: this
    // EFFECTS: Plays a sound when the user decides to claim a roll
    // The implementation for playing sounds was found on a stackoverflow thread
    // link to the thread: https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java
    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
                    new File("./src/assets/claimed-sound.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
