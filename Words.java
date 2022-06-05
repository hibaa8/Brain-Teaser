import javax.imageio.ImageIO;
import javax.security.auth.kerberos.KerberosCredMessage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Words extends JFrame implements ActionListener {
    // private JFrame
    private JLabel title;
    private JButton back, back2;
    private int elapsedTime, seconds, minutes, hours;
    private double bestVel;
    private int ttlHrs, ttlMins, tltSecs;
    private int score;
    private JLabel scorelbl;
    private String seconds_string = String.format("%02d", seconds);
    private String minutes_string = String.format("%02d", minutes);
    private String hours_string = String.format("%02d", hours);
    private JLabel timeLabel = new JLabel();
    // private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private JButton[] buttons = new JButton[64];
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);
    private Font titleFont = new Font("Ink Free", Font.BOLD, 40);
    private Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
    boolean clicked = false;
    private JPanel panel, wordsPanel;
    // private JPanel controlPanel;
    private boolean[] used = new boolean[16];
    HashMap<Integer, String> grid = new HashMap<Integer, String>();
    String[][] grid2 = new String[8][8];
    private int choosen;
    // private ArrayList<String> grid = new ArrayList<String>();
    private ArrayList<String> usedWords = new ArrayList<String>();
    private String[] words = { "fold", "sad", "show", "oven", "snow", "mine", "rain", "math", "mug", "toss", "ride",
            "jem", "pool", "skip" };
    private boolean[] rows = new boolean[8];
    private boolean[] cols = new boolean[8];
    private boolean[] diagonals = new boolean[4];

    public Words() {

        setTitle("Brian Games");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 800);
        setLayout(null);

        title = new JLabel("Word Finder");
        title.setFont(titleFont);
        title.setBounds(200, 60, 500, 125);
        title.setForeground(Color.red);

        scorelbl = new JLabel("Score: " + score);
        scorelbl.setFont(myFont);
        scorelbl.setBounds(150, 15, 200, 75);

        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel.setFont(myFont);
        timeLabel.setBounds(400, 15, 200, 75);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFont(tinyFont);
        back.setFocusable(false);
        back.setBounds(25, 15, 80, 30);

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                grid2[i][j] = "1";
            }
        }

        // for (int j = 0; j < buttons.length; j++) {
        // buttons[j] = new JButton("1");
        // buttons[j].addActionListener((ActionListener) this);
        // buttons[j].setFont(myFont);
        // buttons[j].setFocusable(false);
        // }

        // 1) Pick a random word from words list
        // 2) choose a way to orient the letters on the buttons - horizontal, vertical,
        // or diagonal
        // 3) make the letters appear on the buttons in that manner
        // 4) Add the word to the used words list
        // 5) choose 5 words in total to appear on the board
        // 6) create a hashmap where the key is the index of the button and the value is
        // the letter it corresponds to.
        // 7) the hashmap will be used to create words which will then be matched to the
        // words in the usedWords array

        for (int i = 0; i < 4; i++) {
            int randInd = (int) (Math.random() * words.length);
            String randWord = words[randInd];
            while (usedWords.contains(randWord)) {
                randInd = (int) (Math.random() * words.length);
                randWord = words[randInd];
            }
            usedWords.add(randWord);

            // int randOrientation = (int) Math.random() * 3;

            int randOrientation = (int) (Math.random() * 2);
            // int randOrientation = 1;
            if (randOrientation == 0) {
                horizontalOrient(randWord);
            } else if (randOrientation == 1) {
                verticalOrient(randWord);
            } else {
                diagonalOrient(randWord);
            }
        }
        // System.out.println(buttons);

        int buttonInd = 0;
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                buttons[buttonInd] = new JButton(grid2[i][j]);
                buttons[buttonInd].addActionListener((ActionListener) this);
                buttons[buttonInd].setFont(myFont);
                buttons[buttonInd].setFocusable(false);
                buttonInd++;
            }
        }

        panel = new JPanel();
        panel.setBounds(80, 150, 500, 450);
        panel.setLayout(new GridLayout(8, 8, 3, 3));

        System.out.println("\nbuttons array");
        for (JButton button : buttons) {
            System.out.println(button);
        }
        for (int j = 0; j < buttons.length; j++) {
            panel.add(buttons[j]);
        }

        add(panel);
        add(title);
        add(scorelbl);
        add(timeLabel);
        add(back);
        setVisible(true);
    }

    public void horizontalOrient(String word) {
        System.out.println("horizontal orientation");
        int rand = (int) (Math.random() * 2);
        System.out.println("rand int - " + rand);
        boolean reverse = rand == 0;
        System.out.println("reverse: " + reverse);

        int row = (int) (Math.random() * 8);
        System.out.println("\nrow: " + row);
        System.out.println("\nrows array: ");
        for (boolean b : rows) {
            System.out.println(b);
        }
        while (rows[row] == true) {
            row = (int) (Math.random() * 8);
            System.out.println("new row: " + row);
        }
        rows[row] = true;
        int wordInd = 0;
        int startInd = 0;
        int endInd = 0;
        startInd = (int) (Math.random() * (8 - word.length() + 1));
        endInd = (startInd + word.length()) - 1;
        System.out.println("start ind: " + startInd);
        System.out.println("end ind: " + endInd);
        if (!reverse) {

            // } else if (row == 1) {
            // startInd = (int)(Math.random() * (8-word.length()));
            // startInd = 4;
            // endInd = 8;
            // } else if (row == 2) {
            // startInd = 8;
            // endInd = 12;
            // } else {
            // startInd = 12;
            // endInd = 16;
            // }

            // System.out.println(startInd);
            // System.out.println(endInd);
            // endInd = (startInd + 4) - 1;
            for (int i = startInd; i <= endInd; i++) {
                System.out.println("inside loop");
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[row][i].equals("1")) {
                    // String letter = word.substring(wordInd, wordInd + 1);
                    // grid.put(i, letter);
                    grid2[row][i] = letter;
                    // JButton button = new JButton(letter);
                    // button.addActionListener((ActionListener) this);
                    // button.setFont(myFont);
                    // button.setFocusable(false);
                    // buttons[i] = button;

                    wordInd++;
                }
            }
        } else {
            // if (row == 0) {
            // startInd = 3;
            // endInd = 0;
            // } else if (row == 1) {
            // startInd = 7;
            // endInd = 4;
            // } else if (row == 2) {
            // startInd = 11;
            // endInd = 8;
            // } else {
            // startInd = 15;
            // endInd = 12;
            // }

            for (int i = endInd; i >= startInd; i--) {
                System.out.println("inside loop");
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[row][i].equals("1")) {
                    // String letter = word.substring(wordInd, wordInd + 1);
                    // grid.put(i, letter);
                    grid2[row][i] = letter;
                    // JButton button = new JButton(letter);
                    // button.addActionListener((ActionListener) this);
                    // button.setFont(myFont);
                    // button.setFocusable(false);
                    // buttons[i] = button;

                    wordInd++;
                }
            }
        }
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                System.out.print(grid2[i][j]);
            }
            System.out.println();
        }
    }

    // public boolean checkCol(String word, int startInd, int endInd, int wordInd,
    // int col){
    // boolean colWorks = true;
    // for (int i = startInd; i <= endInd; i++) {
    // System.out.println("checking col");
    // String letter = word.substring(wordInd, wordInd + 1);
    // if (grid2[i][col].equals("1") || grid2[i][col].equals(letter)) {
    // wordInd++;
    // }else{
    // colWorks = false;
    // }
    // }
    // return colWorks;
    // }

    public int chooseCol() {
        int col = (int) (Math.random() * 8);
        System.out.println("\ncol: " + col);
        System.out.println("\ncol array: ");
        for (boolean b : cols) {
            System.out.println(b);
        }
        while (rows[col] == true) {
            col = (int) (Math.random() * 8);
            System.out.println("new col: " + col);
        }
        return col;
    }

    public int checkCol(String word, int startInd, int endInd, int wordInd, int col) {
        boolean colWorks = true;
        for (int i = startInd; i <= endInd; i++) {
            System.out.println("checking col");
            String letter = word.substring(wordInd, wordInd + 1);
            if (grid2[i][col].equals("1") || grid2[i][col].equals(letter)) {
                wordInd++;
            } else {
                colWorks = false;
            }
        }
        while (!colWorks) {
            col = chooseCol();
            wordInd = 0;
            checkCol(word, startInd, endInd, wordInd, col);
        }
        return col;
    }

    public void verticalOrient(String word) {

        System.out.println("vertical orientation");
        int rand = (int) (Math.random() * 2);
        System.out.println("rand int - " + rand);
        boolean reverse = rand == 0;
        System.out.println("reverse: " + reverse);

        int col = chooseCol();

        rows[col] = true;

        int wordInd = 0;
        int startInd = 0;
        int endInd = 0;
        startInd = (int) (Math.random() * (8 - word.length() + 1));
        endInd = (startInd + word.length()) - 1;
        System.out.println("start ind: " + startInd);
        System.out.println("end ind: " + endInd);

        // for(int i = 0; i < grid2[col].length; i++){
        // for(int j = 0; j < grid2.length; j++){
        // if(grid2[j][i].equals("1") || grid2[j][i].equals())
        // }
        // }

        col = checkCol(word, startInd, endInd, wordInd, col);

        if (!reverse) {

            // } else if (row == 1) {
            // startInd = (int)(Math.random() * (8-word.length()));
            // startInd = 4;
            // endInd = 8;
            // } else if (row == 2) {
            // startInd = 8;
            // endInd = 12;
            // } else {
            // startInd = 12;
            // endInd = 16;
            // }

            // System.out.println(startInd);
            // System.out.println(endInd);
            // endInd = (startInd + 4) - 1;

            for (int i = startInd; i <= endInd; i++) {
                System.out.println("inside loop");
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][col].equals("1")) {
                    // String letter = word.substring(wordInd, wordInd + 1);
                    // grid.put(i, letter);
                    grid2[i][col] = letter;
                    // JButton button = new JButton(letter);
                    // button.addActionListener((ActionListener) this);
                    // button.setFont(myFont);
                    // button.setFocusable(false);
                    // buttons[i] = button;

                    wordInd++;
                }

            }
        } else {
            // if (row == 0) {
            // startInd = 3;
            // endInd = 0;
            // } else if (row == 1) {
            // startInd = 7;
            // endInd = 4;
            // } else if (row == 2) {
            // startInd = 11;
            // endInd = 8;
            // } else {
            // startInd = 15;
            // endInd = 12;
            // }

            for (int i = endInd; i >= startInd; i--) {
                System.out.println("inside loop");
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][col].equals("1")) {
                    // String letter = word.substring(wordInd, wordInd + 1);
                    // grid.put(i, letter);
                    grid2[i][col] = letter;
                    // JButton button = new JButton(letter);
                    // button.addActionListener((ActionListener) this);
                    // button.setFont(myFont);
                    // button.setFocusable(false);
                    // buttons[i] = button;

                    wordInd++;
                }
            }
        }
        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                System.out.print(grid2[i][j]);
            }
            System.out.println();
        }

    }

    public void diagonalOrient(String word) {

    }

    // ArrayList<String> usedWords = new ArrayList<String>();
    // for(Integer i: grid.keySet()){
    // System.out.println("key - " + i);
    // System.out.println("value -" + grid.get(i));
    // }

    // while(grid.keySet().size() < 16){
    // System.out.println("keysize -" + grid.keySet().size());
    // int randWordInd = (int)(Math.random() * words.length);
    // String word = words[randWordInd];

    // if(!(usedWords.contains(word))){
    // usedWords.add(word);
    // }
    // grid.put(randWordInd, word);
    // System.out.println("grid size- " + grid.keySet().size());
    // horizontalOrient(word);
    // int randOrientation = (int)(Math.random() * 3);
    // if(randOrientation == 0){
    // horizontalOrient(word);
    // }else if(randOrientation == 1){
    // verticalOrient(word);
    // }else{
    // diagonalOrient(word);
    // }
    // }

    // for (int i = 0; i < 16; i++) {
    // int randWordInd = (int)(Math.random() * words.length);
    // String word = words[randWordInd];

    // String word = "";
    // //choose random starting position on the grid
    // int randStart = (int)(Math.random() * 16);
    // System.out.println("rand int - " + randStart);
    // //if the grid doesn't already have a letter at that position,
    // if(!(grid.keySet().contains(randStart))){
    // int randWordInd = (int)(Math.random() * words.length);
    // word = words[randWordInd];
    // // if(word.equals(grid.get(randWordInd))){
    // // word = words[randWordInd];
    // // }
    // while(grid.values().contains(word)){
    // randWordInd = (int)(Math.random() * words.length);
    // word = words[randWordInd];
    // }
    // int randOrientation = (int)(Math.random() * 3);
    // if(randOrientation == 0){
    // horizontalOrient();
    // }else if(randOrientation == 1){
    // verticalOrient();
    // }else{
    // diagonalOrient();
    // }
    // System.out.println(grid);

    // grid.put(randStart, word);
    // buttons[i] = new JButton(word);
    // buttons[i].addActionListener((ActionListener) this);
    // buttons[i].setFont(myFont);
    // buttons[i].setFocusable(false);
    // }

    // }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(80, 650, 500, 125);
        g.setColor(Color.white);
        g.fillRect(80, 650, 500, 125);

    }

    public void createGrid() {
        int start = (int) (Math.random() * 16);

    }

    // public void horizontalOrient(String word){
    // boolean reverse = false;
    // int rand = (int)(Math.random() * 2);
    // if(rand == 0){
    // reverse = true;
    // }
    // int randRow = (int)(Math.random() * 4) + 1;
    // //System.out.println("row - " + randRow);
    // int max = 4 * randRow - 1;
    // int min = 4 * (randRow - 1);
    // //System.out.println("min - " + min);
    // //System.out.println("max - " + max);
    // if(!reverse){
    // int strInd = 0;

    // for(int i = min; i < (min + word.length()); i++){
    // buttons[i] = new JButton(word.substring(strInd, strInd + 1));
    // buttons[i].addActionListener((ActionListener) this);
    // buttons[i].setFont(myFont);
    // buttons[i].setFocusable(false);
    // strInd++;
    // }
    // }else{
    // int strInd = word.length() - 1;
    // System.out.println("word -" + word);
    // System.out.println("min - " + min);
    // for(int i = min; i < (min + word.length()); i++){
    // //System.out.println("str ind- " + strInd);
    // System.out.println(i);
    // buttons[i] = new JButton(word.substring(strInd, strInd + 1));
    // buttons[i].addActionListener((ActionListener) this);
    // buttons[i].setFont(myFont);
    // buttons[i].setFocusable(false);
    // strInd--;
    // }
    // }

    // }

    // public void verticalOrient(String word){}

    // public void diagonalOrient(String word){}

    // public void paintComponent(Graphics g){
    // paintComponents(g);
    // Graphics2D g2d = (Graphics2D) g;

    // g2d.setColor(Color.gray);
    // g2d.drawRect(50,50,300,100);

    // // g.setColor(Color.RED);
    // // g.fillRect(50,50,300,100);
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            Options options = new Options();
        }

    }

}
