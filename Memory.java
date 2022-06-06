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

public class Memory implements ActionListener {
    private JFrame frame;
    // private JPanel panel;
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
    private JButton[] buttons = new JButton[16];
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);
    private Font titleFont = new Font("Ink Free", Font.BOLD, 40);
    private Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
    private HashMap<Integer, String> cards = new HashMap<Integer, String>();
    private String[] cardPics = { "cards/2_of_clubs.png", "cards/3_of_diamonds.png", "cards/4_of_hearts.png",
            "cards/5_of_spades.png", "cards/6_of_clubs.png", "cards/7_of_diamonds.png", "cards/8_of_hearts.png",
            "cards/9_of_spades.png" };
    private ArrayList<String> selected = new ArrayList<String>();
    // private int initialSec = 0;
    boolean clicked = false;
    private JPanel cardsPanel;
    private JPanel controlPanel;
    private boolean[] used = new boolean[16];
    private int choosen;
    // private JFrame frame2 = new JFrame();

    public Memory() {
        frame = new JFrame("Brain Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(625, 750);
        frame.setLayout(null);
        cardsPanel = new JPanel();
        cardsPanel.setBounds(120, 175, 350, 500);
        cardsPanel.setLayout(new GridLayout(4, 4, 5, 5));

        initControlPanel();

        frame.add(cardsPanel);

        newGame();

    }

    public void newGame() {

        clicked = false;
        for (boolean i : used) {
            i = false;
        }
        // cardsPanel.removeAll();
        buttons = new JButton[16];

        initCardsScreen(cardsPanel);
        // frame.add(controlPanel);
        // frame.add(cardsPanel);

        // frame.setVisible(true);

    }

    public boolean checkWin() {
        boolean won = true;
        for (int i = 0; i < used.length; i++) {
            if (!used[i])
                return false;
        }
        return true;
    }

    public boolean checkLose() {
        // reset();
        // timer.start();
        System.out.println("minute - " + minutes);
        if (minutes == 1) {
            return true;
        }
        return false;
    }

    public void initCardsScreen(JPanel fpanel) {
        // frame.setVisible(true);
        for (int i = 0; i < cardPics.length; i++) {
            int random = (int) (Math.random() * 16);
            while (cards.keySet().contains(random)) {
                random = (int) (Math.random() * 16);
            }
            String path = cardPics[i];
            ImageIcon icon = new ImageIcon(getClass().getResource(cardPics[i]));
            cards.put(random, path);
        }

        for (int name : cards.keySet()) {
            String key = String.valueOf(name);
            String value = cards.get(name);
            System.out.println(key + ":" + value);
        }
        ArrayList<String> avalCards = new ArrayList<String>(Arrays.asList(cardPics));

        for (int n = 0; n < 16; n++) {
            ImageIcon icon = new ImageIcon(getClass().getResource("cards/card_backside.png"));
            buttons[n] = new JButton(icon);
            buttons[n].setActionCommand("" + n);
            buttons[n].addActionListener(this);
            buttons[n].setFont(myFont);
            buttons[n].setFocusable(false);

            System.out.println(avalCards);

            if (!cards.keySet().contains(n)) {
                System.out.println("other");
                int random = (int) (Math.random() * avalCards.size());
                System.out.println("avalCard size = " + avalCards.size());
                System.out.println("Random = " + random);
                String cardPath = avalCards.get(random);
                // icon = new ImageIcon(getClass().getResource(cardPath));
                // buttons[n] = new JButton(icon);
                // buttons[n].addActionListener(this);
                // buttons[n].setFont(myFont);
                avalCards.remove(cardPath);
                cards.put(n, cardPath);
            }
            fpanel.add(buttons[n]);
            // buttons[n].paintImmediately(buttons[n].getVisibleRect());
        }

        // initControlPanel();

        // frame.add(cardsPanel);

        frame.setVisible(true);

        // for (int j = 0; j < 16; j++) {
        // fpanel.add(buttons[j]);
        // }

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].paintImmediately(buttons[i].getVisibleRect());
        }

        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
            System.out.println("error");
            System.exit(1);
        }

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setIcon(new ImageIcon(getClass().getResource(cards.get(i))));
            buttons[i].paintImmediately(buttons[i].getVisibleRect());
        }

        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
            System.out.println("error");
            System.exit(1);
        }

        for (int i = 0; i < buttons.length; i++) {
            ImageIcon icon = new ImageIcon(getClass().getResource("cards/card_backside.png"));
            buttons[i].setIcon(icon);

        }

    }

    public void initControlPanel() {
        title = new JLabel("Card Matching Game");
        title.setFont(titleFont);
        title.setBounds(120, 100, 500, 75);
        title.setForeground(Color.red);

        scorelbl = new JLabel("Score: " + String.valueOf(score));
        scorelbl.setFont(myFont);
        scorelbl.setBounds(275, 5, 500, 75);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFont(tinyFont);
        back.setFocusable(false);
        back.setBounds(25, 15, 80, 30);

        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel.setFont(myFont);
        timeLabel.setBounds(490, 5, 200, 75);

        timer.start();
        frame.add(title);
        frame.add(scorelbl);
        frame.add(back);
        frame.add(timeLabel);
    }

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime = elapsedTime + 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            if (minutes == 1) {
                timer.stop();
                endScreen();
            }
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        }
    });

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back || e.getSource() == back2) {
            frame.dispose();
            Options options = new Options();
        }

        int i = Integer.parseInt(e.getActionCommand());
        buttons[i].setIcon(new ImageIcon(getClass().getResource(cards.get(i))));
        if (clicked && i != choosen) {
            if (!(cards.get(choosen).equals(cards.get(i)))) {
                buttons[i].paintImmediately(buttons[i].getVisibleRect());
                try {
                    Thread.sleep(500);
                } catch (Exception ex) {
                    System.out.println("error");
                    System.exit(1);
                }
                ImageIcon icon = new ImageIcon(getClass().getResource("cards/card_backside.png"));
                buttons[i].setIcon(icon);
                buttons[choosen].setIcon(icon);
                used[choosen] = false;
            } else {
                used[i] = true;
                updateScore();
            }
            clicked = false;
        } else {
            choosen = i;
            used[choosen] = true;
            clicked = true;
        }
        if (checkWin() || checkLose()) {
            timer.stop();
            reset();
            endScreen();
            // int ans = JOptionPane.showConfirmDialog(null, "You Won! Would you like to
            // play again?", "You Win!",
            // JOptionPane.YES_NO_OPTION);
            // if (ans == 0)
            // newGame();
            // else
            // System.exit(0);

        }
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

    public void updateScore() {
        score++;
        scorelbl.setText("Score: " + score);
        frame.add(scorelbl);

    }

    // public void outOfTime() {
    // JLabel outOfTime = new JLabel("(Ran out of time)");
    // outOfTime.setFont(myFont);
    // outOfTime.setBounds(350, 25, 500, 100);
    // endScreen();
    // }

    public void endScreen() {
        JFrame frame2 = new JFrame();
        frame.dispose();
        // JFrame frame2 = new JFrame();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500, 550);
        frame2.setLayout(null);
        frame2.setForeground(Color.white);
        frame2.setTitle("Brain Games");

        JLabel title2 = new JLabel("Game Over");
        title2.setFont(titleFont);
        title2.setBounds(120, 25, 500, 100);
        title2.setForeground(Color.red);

        // if (checkLose()) {
        // JLabel outOfTime = new JLabel("(Ran out of time)");
        // outOfTime.setFont(myFont);
        // outOfTime.setBounds(350, 25, 500, 100);
        // }

        JLabel scorelbl2 = new JLabel("Score: " + String.valueOf(score));
        scorelbl2.setFont(myFont);
        scorelbl2.setBounds(160, 100, 500, 100);

        seconds_string = String.format("%02d", tltSecs);
        minutes_string = String.format("%02d", ttlMins);
        hours_string = String.format("%02d", ttlHrs);
        JLabel timeLabel2 = new JLabel("Time: " + hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel2.setFont(myFont);
        timeLabel2.setBounds(140, 175, 500, 100);

        int totalTime = (ttlHrs * 3600) + (ttlMins * 60) + tltSecs;
        double vel = (double) totalTime / score;
        System.out.println("" + ttlHrs + ttlMins + tltSecs);
        System.out.println(vel);

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
        back2.setBounds(165, 400, 100, 50);

        frame2.add(title2);
        frame2.add(scorelbl2);
        frame2.add(velLbl);
        frame2.add(bestVelLbl);
        frame2.add(timeLabel2);
        frame2.add(back2);
        frame2.setVisible(true);
    }

}
