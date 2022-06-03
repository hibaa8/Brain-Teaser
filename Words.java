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
    HashMap<Integer, String> grid = new HashMap<Integer, String>();
    private int choosen;
    //private ArrayList<String> grid = new ArrayList<String>();
    private String[] words = {"fold", "sad", "show", "oven", "snow", "mine", "rain", "math", "mug", "toss", "ride", "jaw", "pool", "skip"};

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

        ArrayList<String> usedWords = new ArrayList<String>();
        while(grid.keySet().size() < 16){
            int randWordInd = (int)(Math.random() * words.length);
            String word = words[randWordInd];

            if(!(usedWords.contains(word))){
                usedWords.add(word);
            }
            horizontalOrient(word);
            int randOrientation = (int)(Math.random() * 3);
            // if(randOrientation == 0){
            //     horizontalOrient(word);
            // }else if(randOrientation == 1){
            //     verticalOrient(word);
            // }else{
            //     diagonalOrient(word);
            // }
        }

        

        // for (int i = 0; i < 16; i++) {
        //     int randWordInd = (int)(Math.random() * words.length);
        //     String word = words[randWordInd];



        //     String word = "";
        //     //choose random starting position on the grid
        //     int randStart = (int)(Math.random() * 16);
        //     System.out.println("rand int - " + randStart);
        //     //if the grid doesn't already have a letter at that position,
        //     if(!(grid.keySet().contains(randStart))){
        //         int randWordInd = (int)(Math.random() * words.length);
        //         word = words[randWordInd];
        //         // if(word.equals(grid.get(randWordInd))){
        //         //     word = words[randWordInd];
        //         // }
        //         while(grid.values().contains(word)){
        //             randWordInd = (int)(Math.random() * words.length);
        //             word = words[randWordInd];
        //         }
        //         int randOrientation = (int)(Math.random() * 3);
        //         if(randOrientation == 0){
        //             horizontalOrient();
        //         }else if(randOrientation == 1){
        //             verticalOrient();
        //         }else{
        //             diagonalOrient();
        //         }
        //         System.out.println(grid);

        //         grid.put(randStart, word);
        //         buttons[i] = new JButton(word);
        //         buttons[i].addActionListener((ActionListener) this);
        //         buttons[i].setFont(myFont);
        //         buttons[i].setFocusable(false);
        //     }
            
   
        // }

        panel = new JPanel();
        panel.setBounds(125, 170, 400, 400);
        panel.setLayout(new GridLayout(4, 4, 3, 3));
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

    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(80, 620, 500, 125);
        g.setColor(Color.white);
        g.fillRect(80, 620, 500, 125);

    }

   public void createGrid(){
       int start = (int) (Math.random() * 16);

   }

   public void horizontalOrient(String word){
        boolean reverse = false;
        int rand = (int)(Math.random() * 2);
        if(rand == 0){
            reverse = true;
        }
        int randRow = (int)(Math.random() * 4) + 1;
        int max = 4 * randRow - 1;
        int min = 4 * (randRow - 1);
        if(!reverse){
            int strInd = 0;
            for(int i = min; i <= max; i++){
                buttons[i] = new JButton(word.substring(strInd, strInd + 1));
                buttons[i].addActionListener((ActionListener) this);
                buttons[i].setFont(myFont);
                buttons[i].setFocusable(false);
                strInd++;
            }
        }else{
            int strInd = word.length() - 1;
            for(int i = min; i <= max; i++){
                buttons[i] = new JButton(word.substring(strInd, strInd + 1));
                buttons[i].addActionListener((ActionListener) this);
                buttons[i].setFont(myFont);
                buttons[i].setFocusable(false);
                strInd--;
            }
        }
   }

   public void verticalOrient(String word){}

   public void diagonalOrient(String word){}

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
