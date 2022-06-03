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
    private JButton[] buttons = new JButton[16];
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);
    private Font titleFont = new Font("Ink Free", Font.BOLD, 40);
    private Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
    boolean clicked = false;
    private JPanel panel, wordsPanel;
    // private JPanel controlPanel;
    private boolean[] used = new boolean[16];
    private int choosen;
    private ArrayList<String> grid = new ArrayList<>();

    public Words() {
        // = new JFrame("Brain Games") {

        // // @Override
        // // public void paint(Graphics g) {
        // // g.drawRect(200, 500, 500, 100);
        // // }
        // };

        setTitle("Brian Games");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 800);
        setLayout(null);

        title = new JLabel("Word Finder");
        title.setFont(titleFont);
        title.setBounds(200, 60, 500, 125);
        title.setForeground(Color.red);

        // panel = new JPanel();
        // panel.setBounds(50, 150, 400, 400);
        // panel.setLayout(new GridLayout(4, 4, 5, 5));

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

        // Random r1 = new Random();
        // char randomizedCharacter1 = (char) (r1.nextInt(26) + 'a');
        // char rand = 'a' + 5;
        // System.out.println("first rand - "  + rand);
        // System.out.println("random char- " + randomizedCharacter1);

        // Random randlala = new Random();
        // char c = (char)(randlala.nextInt(26) + 'a');
        // System.out.println("second rand -" + c);
        // // char c = 'A';

        for (int i = 0; i < 16; i++) {
            int rand = (int) (Math.random() * 27);
            System.out.println(rand);
            String letter = Character.toString('A' + rand);
            System.out.println(letter);

            System.out.println(grid.toString());
            while(grid.contains(letter)){
                rand = (int) (Math.random() * 26);
                letter = Character.toString('A' + rand);
            }

            grid.add(letter);
            buttons[i] = new JButton(letter);
            buttons[i].addActionListener((ActionListener) this);
            buttons[i].setFont(myFont);
            buttons[i].setFocusable(false);

            // System.out.println("rand char = " + randChar);
            // //System.out.println("random char- " + randomizedCharacter);
            // String letter = Character.toString(randChar);
            // //System.out.println(letter);
            // //System.out.println(grid.toString());
            // // while (grid.contains(letter)) {
            // // letter = Character.toString((r.nextInt(26) + 'A'));
            // // System.out.println("work");
            // // }
            // grid.add(letter);
            

        }

        panel = new JPanel();
        panel.setBounds(125, 170, 400, 400);
        panel.setLayout(new GridLayout(4, 4, 3, 3));
        for (int j = 0; j < buttons.length; j++) {
            panel.add(buttons[j]);
        }

        // wordsPanel = new JPanel();
        // wordsPanel.setBounds(200, 500, 100, 100);

        add(panel);
        add(title);
        add(scorelbl);
        add(timeLabel);
        add(back);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(80, 620, 500, 125);
        g.setColor(Color.white);
        g.fillRect(80, 620, 500, 125);

    }

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
        if(e.getSource() == back){
            Options options = new Options();
        }

    }

}
