//http://random-word-api.herokuapp.com/home
//https://random-word-api.herokuapp.com/word
//https://www.wikihow.com/Play-Hangman
//https://en.wikipedia.org/wiki/Hangman_(game)
package net.koonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    String word;
    String currentGuess;
    List<String> guesses = new ArrayList<>();
    int guessesRemaining;
    GridLayout gridLayout = new GridLayout(3,3);
    Timer timer;


    GamePanel() {
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(600,600));
        //this.setLayout(gridLayout);
        this.setFocusable(true);
        startGame();
        System.out.println(newWord());
    }

    public void startGame() {
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public String newWord() {
        String w = "";
        try {
            w = FetchWord.genNewWord();
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        return w;
    }

    public void draw(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.red);
        g.fillOval(getWidth()/4, getHeight()/4,
                getWidth()/2, getHeight()/2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        draw(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
