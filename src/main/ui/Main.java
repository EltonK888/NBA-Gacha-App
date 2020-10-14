package ui;

import java.io.IOException;

import static java.lang.System.exit;


// The main class that runs the gacha application
public class Main {
    // calls the method to run the gacha game
    public static void main(String[] args) {
        NbaGachaApp app = new NbaGachaApp();
        try {
            app.runApp();
        } catch (IOException e) {
            System.out.println("There was an error reading the player database");
            exit(0);
        }
    }
}
