package ui;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    Image background;

    public BackgroundPanel(Image background) {
        this.background = background;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
    }
}
