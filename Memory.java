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
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Memory implements ActionListener {
    private JFrame frame;
    private JPanel panel;
    private JLabel title;
    private JButton back;
    private int elapsedTime, seconds, minutes, hours, bestVel;
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

    public Memory() {
        frame = new JFrame("Brain Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        frame.setLayout(null);
        frame.setBackground(Color.white);

        title = new JLabel("Card Matching Game");
        title.setFont(titleFont);
        title.setBounds(150, 100, 500, 75);
        title.setForeground(Color.red);

        scorelbl = new JLabel("Score: " + String.valueOf(score));
        scorelbl.setFont(myFont);
        scorelbl.setBounds(300, 5, 500, 75);

        back = new JButton("Back");
        back.addActionListener(this);
        // Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
        back.setFont(tinyFont);
        back.setFocusable(false);
        back.setBounds(25, 15, 80, 30);

        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel.setFont(myFont);
        timeLabel.setBounds(550, 5, 200, 75);

        panel = new JPanel();
        panel.setBounds(160, 175, 350, 500);
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        timer.start();
        frame.add(title);
        frame.add(back);
        frame.add(timeLabel);
        frame.add(scorelbl);
        frame.add(panel);

        frame.setVisible(true);

        // front side up
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
            // ImageIcon icon = new
            // ImageIcon(getClass().getResource("cards/card_backside.png"));
            // buttons[n] = new JButton(icon);
            // buttons[n].addActionListener(this);
            // buttons[n].setFont(myFont);
            // buttons[n].setFocusable(false);

            System.out.println(n);
            ImageIcon icon;
            for (int i : cards.keySet()) {
                if (i == n) {
                    icon = new ImageIcon(getClass().getResource(cards.get(i)));
                    buttons[n] = new JButton(icon);
                    buttons[n].addActionListener(this);
                    buttons[n].setFont(myFont);
                    buttons[n].setFocusable(false);
                    break;
                }
            }

            System.out.println(avalCards);

            if (!cards.keySet().contains(n)) {
                System.out.println("other");
                int random = (int) (Math.random() * avalCards.size());
                System.out.println("avalCard size = " + avalCards.size());
                System.out.println("Random = " + random);
                String cardPath = avalCards.get(random);
                icon = new ImageIcon(getClass().getResource(cardPath));
                buttons[n] = new JButton(icon);
                buttons[n].addActionListener(this);
                buttons[n].setFont(myFont);
                avalCards.remove(cardPath);
                cards.put(n, cardPath);
            }

        }

        for (int j = 0; j < 16; j++) {
            panel.add(buttons[j]);
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int n = 0; n < 16; n++) {
            ImageIcon icon = new ImageIcon(getClass().getResource("cards/card_backside.png"));
            buttons[n].setIcon(icon);
        }
    }

    public void newGame() {
        ArrayList<String> avalCards = new ArrayList<String>(Arrays.asList(cardPics));
        for (int n = 0; n < 16; n++) {
            System.out.println(n);
            ImageIcon icon;
            for (int i : cards.keySet()) {
                if (i == n) {
                    icon = new ImageIcon(getClass().getResource(cards.get(i)));
                    buttons[n] = new JButton(icon);
                    buttons[n].addActionListener(this);
                    buttons[n].setFont(myFont);
                    buttons[n].setFocusable(false);
                    break;
                }
            }

            System.out.println(avalCards);

            if (!cards.keySet().contains(n)) {
                System.out.println("other");
                int random = (int) (Math.random() * avalCards.size());
                System.out.println("avalCard size = " + avalCards.size());
                System.out.println("Random = " + random);
                String cardPath = avalCards.get(random);
                icon = new ImageIcon(getClass().getResource(cardPath));
                buttons[n] = new JButton(icon);
                buttons[n].addActionListener(this);
                buttons[n].setFont(myFont);
                avalCards.remove(cardPath);
                cards.put(n, cardPath);
            }

            try {
                Thread.sleep(500); // let the user see their error
            } catch (Exception ex) {
                System.out.println("error");
                System.exit(1);
            }

            for (JButton button : buttons) {
                button.setBackground(Color.red);
            }

        }
    }

    public void backsideShow() {
        // try {
        // Thread.sleep(2000);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        frame.setVisible(false);
        for (int n = 0; n < 16; n++) {
            ImageIcon icon = new ImageIcon(getClass().getResource("cards/card_backside.png"));
            buttons[n].setIcon(icon);
        }

        panel = new JPanel();
        panel.setBounds(160, 175, 350, 500);
        panel.setLayout(new GridLayout(4, 4, 5, 5));
        for (int j = 0; j < 16; j++) {
            panel.add(buttons[j]);
        }
        frame.add(panel);
        frame.setVisible(true);

    }

    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime = elapsedTime + 1000;
            hours = (elapsedTime / 3600000);
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            seconds_string = String.format("%02d", seconds);
            minutes_string = String.format("%02d", minutes);
            hours_string = String.format("%02d", hours);
            timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        }
    });

    public void updateScore() {
        score++;
        scorelbl.setText("Score: " + score);
        frame.add(scorelbl);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            frame.dispose();
            Options options = new Options();
        }

        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                // add to selected, turn the card, calculate time, print stuff out
                selected.add(cards.get(i));
                buttons[i].setIcon(new ImageIcon(getClass().getResource(cards.get(i))));

            }

            // if ((selected.size() == 1) && selected.get(0).equals(cards.get(i))) {
            // System.out.println("update score");
            // updateScore();
            // } else {

            // }

            // if (selected.size() == 2) {
            // selected.clear();
            // }

        }
    }
}
