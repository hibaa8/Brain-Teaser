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

public class Words extends JFrame implements ActionListener{
    private JFrame frame;
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
    private JButton[] buttons = new JButton[25];
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);
    private Font titleFont = new Font("Ink Free", Font.BOLD, 40);
    private Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
    boolean clicked = false;
    private JPanel panel, wordsPanel;
    //private JPanel controlPanel;
    private boolean[] used = new boolean[16];
    private int choosen;

    public Words() {
        frame = new JFrame("Brain Games") {

            @Override public void paint(Graphics g) {
                g.drawRect(200, 500, 500, 100);
            }
        };

        frame.setTitle("Brian Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 800);
        frame.setLayout(null);

        title = new JLabel("Word Finder");
        title.setFont(titleFont);
        title.setBounds(250, 60, 500, 125);
        title.setForeground(Color.red);

        panel = new JPanel();
        panel.setBounds(200, 150, 400, 400);
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        scorelbl = new JLabel("Score: " + score);
        scorelbl.setFont(myFont);
        scorelbl.setBounds(200, 15, 200, 75);

        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel.setFont(myFont);
        timeLabel.setBounds(450, 15, 200, 75);

        back = new JButton("Back");
        back.addActionListener(this);
        back.setFont(tinyFont);
        back.setFocusable(false);
        back.setBounds(25, 15, 80, 30);

        char c = 'A';

        for(int i = 0; i < 25; i++){
            buttons[i] = new JButton(Character.toString(c));
            buttons[i].addActionListener((ActionListener) this);
            buttons[i].setFont(myFont);
            buttons[i].setFocusable(false);
            c++;
        }

        panel = new JPanel();
        panel.setBounds(50, 200, 400,400);
        panel.setLayout(new GridLayout(5,5,5,5));
        for(int j = 0; j < buttons.length; j++){
            panel.add(buttons[j]);
        }

        wordsPanel = new JPanel();
        wordsPanel.setBounds(200, 500, 100, 100);
        

        add(panel);
        add(title);
        add(scorelbl);
        add(timeLabel);
        add(back);
        setVisible(true);
    }

    // public void paintComponent(Graphics g){
    //     frame.paintComponents(g);
    //     Graphics2D g2d = (Graphics2D) g;

    //     g2d.setColor(Color.gray);
    //     g2d.drawRect(50,50,300,100);
        

    //     // g.setColor(Color.RED);
    //     // g.fillRect(50,50,300,100);
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }





}
