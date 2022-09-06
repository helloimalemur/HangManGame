//https://inventwithpython.com/invent4thed/images/00081.jpeg
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
    String title = "Hangman";
    boolean running = false;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    String word;
    char[] maskedWord;
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
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        //timer = new Timer(DELAY, this);
        running = true;
        //timer.start();
    }





    public void newWord() {
        try {
            word = FetchWord.genNewWord();
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        char[] wordarray = new char[word.length()];
        for(int i=0;i<word.length();i++) {
            wordarray[i] = word.charAt(i);
        }
    }

    public void resetGame() {
        //reset game variables
        //new word
        startGame();
    }
    public void gameOver() {
        running = false;
        timer.stop();
    }


    ///Graphics
    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.white);
            Font font = new Font("Ink Free", Font.BOLD, 40);
            g.setFont(font);
            g.drawString(title, (SCREEN_WIDTH - getFontMetrics(font).stringWidth(title))/2, (getFont().getSize())*2);

        } else {
            gameOver();
        }

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        draw(g);
    }
    //user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            //get new word

        } else {
            timer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'z') {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
