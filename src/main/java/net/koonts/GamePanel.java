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
import java.util.Arrays;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    String title = "Hangman";
    static public final int SCREEN_WIDTH = 1000;
    static public final int SCREEN_HEIGHT = 600;
    boolean running = false;
    boolean won = false;
    static final int UNIT_SIZE = 25;
    static int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    String word;
    String finalword;
    char[] maskedWord;
    char[] wordArray;
    char[] formattedWord;
    char[] guessArray;
    boolean[] matches;
    String currentGuess;
    List<String> guesses = new ArrayList<>();
    int guessesRemaining;
    GridLayout gridLayout = new GridLayout(3,3);
    Button button = new Button();
    TextField textField = new TextField();
    Timer timer;
    Font font = new Font("Ink Free", Font.BOLD, 40);


    GamePanel() {
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setVisible(true);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        Font textAndButton = new Font("Ink Free", Font.BOLD, 15);
        setLayout(null);
        button.setLocation(0,0);
        button.setPreferredSize(new Dimension(80,40));
        button.setFont(textAndButton);
        button.setLabel("Guess!");
        button.setBounds(10,500,80,30);
        button.addActionListener(this);
        add(button);
        textField.setBounds(100,505,200,20);
        textField.setFont(textAndButton);
        textField.setPreferredSize(new Dimension(200,20));
        add(textField);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        //timer = new Timer(DELAY, this);
        running = true;
        newWord();
        //timer.start();
    }




    public void checkGuess() {
        String guess = textField.getText();
        wordArray.toString().replace("[","");
        wordArray.toString().replace("]","");
        wordArray.toString().replace(",","");

        guessArray = new char[guess.length()];
        for (int i = 0; i < guess.length(); i++) {
            guessArray[i] = guess.charAt(i);
        }
        System.out.println(wordArray);
        System.out.println(guessArray);
        int truths = 0;
        matches = new boolean[wordArray.length];
        for (int i=0;i<wordArray.length;i++) {
            if ((Character.compare(wordArray[i],guessArray[i]))==0) {
                matches[i] = true;
                System.out.println("match");
                truths +=1;
            } else {
                matches[i] = false;
            }
            if (truths == wordArray.length) {
                won = true;
            } else { won = false;}
            System.out.println(truths);
        }
        System.out.println("comparing;");
        System.out.println(guessArray);
        System.out.println(wordArray);
        System.out.println("Result: " + won);
        textField.setText("");

    }

    public void newWord() {
        try {
            word = FetchWord.genNewWord();
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        wordArray = new char[word.length()-4];
        maskedWord = new char[word.length()-4];
        formattedWord = new char[word.length()-4];
        int j = 0;
        for(int i=2;i<word.length()-2;i++){
            formattedWord[j] = word.charAt(i);
            j = j+1;

        }

        for(int i=0;i<(word.length()-4);i++) {
            wordArray[i] = formattedWord[i];
            maskedWord[i] = '-';
        }
        System.out.println(wordArray);
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
            g.setFont(font);
            g.drawString(title, (SCREEN_WIDTH - getFontMetrics(font).stringWidth(title))/2, (getFont().getSize())*3);
            g.drawString(Arrays.toString(maskedWord), (SCREEN_WIDTH - getFontMetrics(font).stringWidth(Arrays.toString(maskedWord)))/2, SCREEN_HEIGHT-(getFont().getSize())*3);

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
        if(e.getSource() == button) {
            checkGuess();
        }

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
