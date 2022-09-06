package net.koonts;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GameFrame() {
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        setSize(new Dimension(600,600));
        setLocationRelativeTo(null);
        setBackground(Color.black);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

}
