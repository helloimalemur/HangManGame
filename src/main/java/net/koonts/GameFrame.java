package net.koonts;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    static public final int SCREEN_WIDTH = 1000;
    static public final int SCREEN_HEIGHT = 600;
    GameFrame() {
        GamePanel gamePanel = new GamePanel();
        add(gamePanel);
        setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        setLocationRelativeTo(null);
        setBackground(Color.black);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

}
