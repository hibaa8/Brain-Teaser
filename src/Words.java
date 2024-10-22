package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Words extends JFrame implements ActionListener {
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
    private JButton[] buttons = new JButton[64];
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);
    private Font titleFont = new Font("Ink Free", Font.BOLD, 40);
    private Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
    boolean clicked = false;
    private JPanel buttonsPanel, wordsPanel;
    private boolean[] used = new boolean[16];
//    HashMap<Integer, String> grid = new HashMap<Integer, String>();
    String[][] grid2 = new String[8][8];
//    private int chosen;
    private ArrayList<String> usedWords = new ArrayList<String>();
    private String[] w = { "school", "happy", "science", "math", "history", "book", "mail", "game", "golf", "soccer",
            "pool", "beach", "summer", "flower", "fire", "vacation", "june", "free", "party", "movie", "show", "hike",
            "bicycle", "eat", "money", "forest", "monkey", "dog", "cat", "bear", "fish", "computer", "sea", "water",
            "story", "farmer", "city", "year", "art", "writer", "power", "apple",
            "orange", "music", "bird", "meal", "poetry", "law", "device", "idea", "balloon", "cake", "airport", "pizza",
            "piano", "rain", "horse", "bed", "cartoon",
            "shoe", "crayon", "lamp", "truck", "dream", "market", "van", "whale", "energy", "football", "glass", "oil"
    };
    private ArrayList<String> words = new ArrayList<String>(Arrays.asList(w));

    private boolean[] rows = new boolean[8];
    private boolean[] cols = new boolean[8];
    private boolean[] diagonalsInd = new boolean[4];
    private ArrayList<ArrayList<String>> diagonals = new ArrayList<ArrayList<String>>();
    private String word = "";

    public Words() {

        setTitle("Brain Games");
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

        for (int i = 0; i < 4; i++) {
            int randInd = (int) (Math.random() * words.size());
            String randWord = words.get(randInd);
            while (usedWords.contains(randWord)) {
                randInd = (int) (Math.random() * words.size());
                randWord = words.get(randInd);
            }
            usedWords.add(randWord);
            int randOrientation = (int) (Math.random() * 3);
            if (randOrientation == 0) {
                horizontalOrient(randWord);
            } else if (randOrientation == 1) {
                verticalOrient(randWord);
            } else {
                diagonalOrient(randWord);
            }
        }

        for (int i = 0; i < grid2.length; i++) {
            for (int j = 0; j < grid2[0].length; j++) {
                if (grid2[i][j].equals("1")) {
                    Random r = new Random();
                    char c = (char) (r.nextInt(26) + 'a');
                    String letter = Character.toString(c);
                    grid2[i][j] = letter;
                }
            }
        }

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

        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(80, 160, 500, 450);
        buttonsPanel.setLayout(new GridLayout(8, 8, 3, 3));

        wordsPanel = new JPanel();
        wordsPanel.setBounds(80, 625, 500, 125);
        wordsPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        wordsPanel.setLayout(new GridLayout(2, 4, 5, 5));
        wordsPanel.setBackground(Color.white);


        for (int j = 0; j < buttons.length; j++) {
            buttonsPanel.add(buttons[j]);
        }

        timer.start();

        add(buttonsPanel);
        add(wordsPanel);
        add(title);
        add(scorelbl);
        add(timeLabel);
        add(back);
        setVisible(true);
    }

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime = elapsedTime + 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            if (minutes == 2) {
                timer.stop();
                endScreen();
            }
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        }
    });

    public int chooseRow() {
        int row = (int) (Math.random() * 8);


        while (rows[row] == true) {
            row = (int) (Math.random() * 8);
        }
        return row;
    }

    public int checkRow(String word, int startInd, int endInd, int wordInd, int row, boolean reverse) {
        boolean rowWorks = true;
        if (!reverse) {
            for (int i = startInd; i <= endInd; i++) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[row][i].equals("1") || grid2[row][i].equals(letter)) {
                    wordInd++;
                } else {
                    rowWorks = false;
                }
            }
        } else {
            for (int i = endInd; i >= startInd; i--) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[row][i].equals("1") || grid2[row][i].equals(letter)) {
                    wordInd++;
                } else {
                    rowWorks = false;
                }
            }
        }
        while (!rowWorks) {
            row = chooseRow();
            wordInd = 0;
            rowWorks = true;
            row = checkRow(word, startInd, endInd, wordInd, row, reverse);
        }
        return row;
    }

    public void horizontalOrient(String word) {
        int rand = (int) (Math.random() * 2);
        boolean reverse = rand == 0;

        int row = chooseRow();

        rows[row] = true;
        int wordInd = 0;
        int startInd = 0;
        int endInd = 0;
        startInd = (int) (Math.random() * (8 - word.length() + 1));
        endInd = (startInd + word.length()) - 1;

        row = checkRow(word, startInd, endInd, wordInd, row, reverse);

        if (!reverse) {

            for (int i = startInd; i <= endInd; i++) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[row][i].equals("1")) {
                    grid2[row][i] = letter;

                    wordInd++;
                }
            }
        } else {

            for (int i = endInd; i >= startInd; i--) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[row][i].equals("1")) {
                    grid2[row][i] = letter;
                    wordInd++;
                }
            }
        }

    }

    public int chooseCol() {
        int col = (int) (Math.random() * 8);


        while (rows[col] == true) {
            col = (int) (Math.random() * 8);
        }
        cols[col] = true;
        return col;
    }

    public int checkCol(String word, int startInd, int endInd, int wordInd, int col, boolean reverse) {
        boolean colWorks = true;
        if (!reverse) {
            for (int i = startInd; i <= endInd; i++) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][col].equals("1") || grid2[i][col].equals(letter)) {
                    wordInd++;
                } else {
                    colWorks = false;
                }
            }
        } else {
            for (int i = endInd; i >= startInd; i--) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][col].equals("1")) {
                    wordInd++;
                } else if (grid2[i][col].equals(letter)) {
                    wordInd += 2;
                } else {
                    colWorks = false;
                }
            }
        }
        while (!colWorks) {
            col = chooseCol();
            wordInd = 0;
            colWorks = true;
            col = checkCol(word, startInd, endInd, wordInd, col, reverse);
        }
        return col;
    }

    public void verticalOrient(String word) {
        int rand = (int) (Math.random() * 2);
        boolean reverse = rand == 0;

        int col = chooseCol();

        rows[col] = true;

        int wordInd = 0;
        int startInd = 0;
        int endInd = 0;
        startInd = (int) (Math.random() * (8 - word.length() + 1));
        endInd = (startInd + word.length()) - 1;

        col = checkCol(word, startInd, endInd, wordInd, col, reverse);

        if (!reverse) {

            for (int i = startInd; i <= endInd; i++) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][col].equals("1")) {
                    grid2[i][col] = letter;

                    wordInd++;
                }

            }
        } else {

            for (int i = endInd; i >= startInd; i--) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][col].equals("1")) {
                    grid2[i][col] = letter;

                    wordInd++;
                }
            }
        }

    }

    public int checkDiagonal(String word, int diag, int colInd, int wordInd) {
        boolean diagonalWorks = true;
        if (diag == 0) {
            for (int i = 0; i < word.length(); i++) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][colInd].equals("1")) {
                    colInd++;
                    wordInd++;
                } else if (grid2[i][colInd].equals(letter)) {
                    colInd++;
                    wordInd += 2;
                } else {
                    diagonalWorks = false;
                }
            }
        } else if (diag == 1) {
            colInd = 7;
            for (int i = 0; i < word.length(); i++) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][colInd].equals("1") || grid2[i][colInd].equals(letter)) {
                    colInd--;
                    wordInd++;
                } else if (grid2[i][colInd].equals(letter)) {
                    colInd--;
                    wordInd += 2;
                } else {
                    diagonalWorks = false;
                }
            }
        } else if (diag == 2) {
            for (int i = 7; i >= (7 - word.length()) + 1; i--) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][colInd].equals("1")) {
                    colInd++;
                    wordInd++;
                } else if (grid2[i][colInd].equals(letter)) {
                    colInd++;
                    wordInd += 2;
                } else {
                    diagonalWorks = false;
                }
            }
        } else {
            colInd = 7;
            for (int i = 7; i >= (7 - word.length()) + 1; i--) {
                String letter = word.substring(wordInd, wordInd + 1);
                if (grid2[i][colInd].equals("1")) {
                    colInd--;
                    wordInd++;
                } else if (grid2[i][colInd].equals(letter)) {
                    colInd--;
                    wordInd += 2;
                } else {
                    diagonalWorks = false;
                }
            }
        }
        while (!diagonalWorks) {
            diag = chooseDiagonal();
            wordInd = 0;
            colInd = 0;
            diagonalWorks = true;
            diag = checkDiagonal(word, diag, colInd, wordInd);
        }
        return diag;
    }

    public int chooseDiagonal() {
        int randDiag = (int) (Math.random() * 4);
        while (diagonalsInd[randDiag]) {
            randDiag = (int) (Math.random() * 4);
        }
        diagonalsInd[randDiag] = true;
        return randDiag;
    }

    public void diagonalOrient(String word) {
        int rand = (int) (Math.random() * 2);
        boolean reverse = rand == 0;

        int diag = chooseDiagonal();

        int colInd = 0;
        int wordInd = 0;

        diag = checkDiagonal(word, diag, colInd, wordInd);

        if (diag == 0) {
            for (int i = 0; i < word.length(); i++) {
                grid2[i][colInd] = word.substring(wordInd, wordInd + 1);
                colInd++;
                wordInd++;
            }
        } else if (diag == 1) {
            colInd = 7;
            for (int i = 0; i < word.length(); i++) {
                grid2[i][colInd] = word.substring(wordInd, wordInd + 1);
                colInd--;
                wordInd++;
            }
        } else if (diag == 2) {
            for (int i = 7; i >= (7 - word.length()) + 1; i--) {
                grid2[i][colInd] = word.substring(wordInd, wordInd + 1);
                colInd++;
                wordInd++;
            }
        } else {
            colInd = 7;
            for (int i = 7; i >= (7 - word.length()) + 1; i--) {
                grid2[i][colInd] = word.substring(wordInd, wordInd + 1);
                colInd--;
                wordInd++;
            }
        }

    }


    public boolean checkLose() {
        return false;
    }

    public void reset() {
        timer.stop();
        ttlHrs += hours;
        ttlMins += minutes;
        tltSecs += seconds;
        elapsedTime = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        seconds_string = String.format("%02d", seconds);
        minutes_string = String.format("%02d", minutes);
        hours_string = String.format("%02d", hours);
        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
    }

    public void endScreen() {
        JFrame frame2 = new JFrame();
        dispose();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500, 550);
        frame2.setLayout(null);
        frame2.setForeground(Color.white);
        frame2.setTitle("Brain Games");

        JLabel title2 = new JLabel("Game Over");
        title2.setFont(titleFont);
        title2.setBounds(135, 25, 500, 100);
        title2.setForeground(Color.red);

        JLabel scorelbl2 = new JLabel("Score: " + String.valueOf(score));
        scorelbl2.setFont(myFont);
        scorelbl2.setBounds(175, 100, 500, 100);

        seconds_string = String.format("%02d", tltSecs);
        minutes_string = String.format("%02d", ttlMins);
        hours_string = String.format("%02d", ttlHrs);
        JLabel timeLabel2 = new JLabel("Time: " + hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel2.setFont(myFont);
        timeLabel2.setBounds(140, 175, 500, 100);

        int totalTime = (ttlHrs * 3600) + (ttlMins * 60) + tltSecs;
        double vel = (double) totalTime / score;

        JLabel velLbl = new JLabel("Rate: " + String.valueOf(vel) + " \nsecs/correct answer");
        velLbl.setFont(new Font("Ink Free", Font.BOLD, 22));
        velLbl.setBounds(80, 250, 500, 80);

        if (vel > bestVel) {
            bestVel = vel;
            JLabel newVel = new JLabel("New rate!");
            newVel.setFont(new Font("Ink Free", Font.BOLD, 30));
            newVel.setBounds(300, 100, 500, 100);
            newVel.setForeground(Color.blue);
            frame2.add(newVel);
        }

        JLabel bestVelLbl = new JLabel("Best Rate: " + String.valueOf(bestVel) + " \nsecs/correct answer");
        bestVelLbl.setFont(new Font("Ink Free", Font.BOLD, 22));
        bestVelLbl.setBounds(80, 300, 500, 100);

        back2 = new JButton("Back");
        back2.addActionListener(this);
        // Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
        back2.setFont(myFont);
        back2.setFocusable(false);
        back2.setBounds(180, 400, 100, 50);

        frame2.add(title2);
        frame2.add(scorelbl2);
        frame2.add(velLbl);
        frame2.add(bestVelLbl);
        frame2.add(timeLabel2);
        frame2.add(back2);
        frame2.setVisible(true);
    }

    public void updateScore() {
        score++;
        scorelbl.setText("Score: " + score);
        add(scorelbl);
        if (score == 5) {
            timer.stop();
            reset();
            endScreen();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back || e.getSource() == back2) {
            dispose();
            Options options = new Options();
        }

        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                buttons[i].setBackground(Color.red);
                word += buttons[i].getText();
                if (words.contains(word)) {
                    updateScore();
                    JLabel wordlbl = new JLabel(word);
                    wordlbl.setFont(myFont);
                    wordsPanel.add(wordlbl);
                    word = "";
                }
            }
        }

    }

}
