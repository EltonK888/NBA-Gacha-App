package ui;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    public Font font = new Font("Arial", Font.PLAIN, 40);
    public Color colour = new Color(21, 55, 100);

    public Label(String text) {
        setText(text);
        setForeground(colour);
        setFont(font);
    }

}

