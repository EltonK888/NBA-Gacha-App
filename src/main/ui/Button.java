package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton {

    private final String loadDataButtonString = "Load Data";
    private final String newGameBUttonString = "New Game";
    public GridBagConstraints constraints;

    public Button(String buttonText) {
        super(buttonText);
        setPreferredSize(new Dimension(150, 40));
        setMaximumSize(new Dimension(150, 40));
        //addActionListener(this);
        setFocusable(false);
    }

    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(loadDataButtonString)) {
            NbaGachaApp.loadSavedData();
            setVisible(false);

        } else if (e.getActionCommand().equals(newGameBUttonString)) {
            setVisible(false);
        }
    }

     */
}
