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
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    String title = "Hangman";
    static public final int SCREEN_WIDTH = 1000;
    static public final int SCREEN_HEIGHT = 600;
    boolean running = false;
    boolean matchWord = false;
    boolean lost = false;
    static final int UNIT_SIZE = 25;
    static int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 100;
    int guessLimit;
    int guessCount;
    String word;
    String finalword;
    char[] maskedWord;
    char[] wordArray;
    char[] formattedWord;
    char[] guessArray;
    boolean[] matches;
    String currentGuess;
    List<String> guesses = new ArrayList<>();
    GridLayout gridLayout = new GridLayout(3,3);
    Button button = new Button();
    TextField textField = new TextField();
    Timer timer;
    Font font = new Font("Ink Free", Font.BOLD, 40);


    GamePanel() {
        this.setSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.green);
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
        timer = new Timer(DELAY, this);
        running = true;
        newWord();
        timer.start();
        guessCount = 0;
    }



    public boolean checkGuess() {

        guessCount += 1;
        //grab user guess and create char[] of corresponding size
        String guess = textField.getText();
        if (guess.length()>0) {
            guessArray = new char[guess.length()];
        } else {
            guessArray = new char[0];
        }
        for (int i = 0; i < guess.length(); i++) {
            guessArray[i] = guess.charAt(i);
        }

        //section compares guess and word array
        int truths = 0;
        matches = new boolean[wordArray.length];// array to store matched char
        if (wordArray.length == guessArray.length) { //verify word and guess array are the same size
            for (int i = 0; i < wordArray.length; i++) {
                if ((Character.compare(wordArray[i], guessArray[i])) == 0) {////compare word and guess array
                    matches[i] = true; //store match true in matches array in position relative to word/guess char
                    System.out.println("match");
                    truths += 1; //number of matches
                } else {
                    matches[i] = false;
                }
                if (truths == wordArray.length) { //if the number of matches equals the
                    //// number of char in word, guess is correct
                    matchWord = true;
                    for (int k = 0; k < maskedWord.length; k++) {
                        if (matches[k]) { //for positions in matches[] which hold a good guess
                            maskedWord[k] = wordArray[k];// show matches by replacing dash with corresponding character
                        }
                    }
                } else {
                    matchWord = false;
                }
            }

        } else { // if word and guess array are NOT the same size
            if (guessArray.length>0) { // and guess array is greater than 0
                for (int j = 0; j < wordArray.length; j++) {
                    if (Character.compare(guessArray[0], wordArray[j]) == 0) {//compare guess and word array
                        matches[j] = true; //store matched characters in array
                        System.out.println("match");
                        truths += 1;
                    } else {
                        matches[j] = false;
                    }
                }

            }

            //if there is no match yet /guess and word array are not the same size
            for (int k = 0; k < maskedWord.length; k++) {
                if (matches[k]) { //for positions in matches[] which hold a good guess
                    maskedWord[k] = wordArray[k];// show matches by replacing dash with corresponding character
                }
            }
        }



        System.out.println("comparing;");
        System.out.println(guessArray);

        System.out.println("Result: " + matchWord);
        textField.setText("");
        repaint();
        int l=0;
        if (guessArray.length==0) {// no answer input
            int confirm = 0;
            for (int m=0;m<wordArray.length;m++) { //iterate over wordArray
                if ((Character.compare(wordArray[m], maskedWord[l])) == 0) { //compare wordArray and maskedWord
                    matches[m] = true; //store matched characters in array
                    confirm += 1;
                    l++;
                } else {
                    matches[m] = false;
                    l++;
                }
                if (confirm == wordArray.length) {
                    matchWord = true;
                    unmaskWord();
                } else {
                    matchWord = false;
                }
            }
        }

        return matchWord;
    }

    public void newWord() { //get new word from api, remove extra characters, and return as char[]
        try {
            word = FetchWord.genNewWord();//word from api example returned word ['cardinal']
        } catch(IOException | InterruptedException e) {
            System.out.println(e);
        }
        wordArray = new char[word.length()-4];//
        maskedWord = new char[word.length()-4];//create char[] remove 4 unwanted characters by iterating over
        formattedWord = new char[word.length()-4]; // global wordArray and maskedWord are used
        int j = 0;
        for(int i=2;i<word.length()-2;i++){
            wordArray[j] = word.charAt(i);
            maskedWord[j] = '-'; //set maskedWord to all dashes
            j = j+1;
        }
        wordArray.toString().replace("[", "");
        wordArray.toString().replace("]", "");
        wordArray.toString().replace(",", "");
        guessLimit = (int) (wordArray.length*1.75);
        System.out.println(wordArray);
    }

    public void resetGame() {
        //reset game variables
        //new word
        startGame();
    }
    public void gameOver() {
        running = false;
    }
    public void unmaskWord() {
        for (int k = 0; k < wordArray.length; k++) {
            maskedWord[k] = wordArray[k];
        }
    }


    ///Graphics
    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString(title, (SCREEN_WIDTH - getFontMetrics(font).stringWidth(title))/2, (getFont().getSize())*3);
            String guessesRemaining = "Guesses left: " + (guessLimit-guessCount);
            g.drawString(guessesRemaining, ((SCREEN_WIDTH-getFontMetrics(font).stringWidth(guessesRemaining))/2), 80);
            g.drawString(Arrays.toString(maskedWord), (SCREEN_WIDTH - getFontMetrics(font).stringWidth(Arrays.toString(maskedWord)))/2, SCREEN_HEIGHT-(getFont().getSize())*3);
            if (matchWord) {
                g.drawString("WINNER",(SCREEN_WIDTH - getFontMetrics(font).stringWidth("WINNER"))/2, SCREEN_HEIGHT/2);
            }
            if ((guessCount>=guessLimit)){
                g.drawString("LOSE",(SCREEN_WIDTH - getFontMetrics(font).stringWidth("WINNER"))/2, SCREEN_HEIGHT/2);
                gameOver();
            }
        } else {
            timer.stop();
        }

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    //user input
    @Override
    public void actionPerformed(ActionEvent e) {


        if (running) {
            //get new word
            if(e.getSource() == button) {
                checkGuess();
            }

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
