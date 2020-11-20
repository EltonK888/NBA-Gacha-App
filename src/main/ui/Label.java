package ui;

import javax.swing.*;
import java.awt.*;

// A class that sets the colour and font of the text used in the GUI
public class Label extends JLabel {
    public Font font = new Font("Arial", Font.PLAIN, 40);
    public Color colour = new Color(21, 55, 100);

    // EFFECTS: Constructs a text label with the given font and colour
    public Label(String text) {
        setText(text);
        setForeground(colour);
        setFont(font);
    }

}

