package ui;

import javax.swing.*;
import java.awt.*;


// A class that creates a button with specific dimensions and text
public class Button extends JButton {

    // EFFECTS: Constructs a button with specified text and certain dimesions for the app
    public Button(String buttonText) {
        super(buttonText);
        setPreferredSize(new Dimension(150, 40));
        setMaximumSize(new Dimension(150, 40));
        setFocusable(false);
    }

}
