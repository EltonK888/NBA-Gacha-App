package ui;

import java.io.IOException;


public class Main {
    // calls the method to run the gacha game
    public static void main(String[] args) {
        NbaGachaApp app = new NbaGachaApp();
        try {
            app.runApp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
