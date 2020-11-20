package ui;

import javax.swing.*;
import java.awt.*;

// A class that is the background component of the JFrame
public class BackgroundPanel extends JPanel {
    private Image background;

    // EFFECTS: Constructs the background panel with the background image
    public BackgroundPanel(Image background) {
        this.background = background;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: Paints the image onto the panel
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}
