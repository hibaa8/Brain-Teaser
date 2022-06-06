import javax.swing.*;

import org.w3c.dom.Text;

import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.HashMap;
import java.util.logging.Formatter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MathGame implements ActionListener, KeyListener {
    private JFrame frame;
    private JPanel numsPanel;
    private JPanel operPanel;
    private JLabel title;
    private JLabel questionlbl;
    private JLabel questionNumlbl;
    private JLabel scorelbl;
    private int question;
    private int score = 0;
    private int elapsedTime = 0;
    private int seconds, minutes, hours;
    private int bestScore = 0;
    private double bestVel = 0;
    private int ttlHrs, ttlMins, tltSecs;
    private String seconds_string = String.format("%02d", seconds);
    private String minutes_string = String.format("%02d", minutes);
    private String hours_string = String.format("%02d", hours);
    // private String timeString = String.format("%02d", hours) +
    // String.format("%02d", minutes) + String.format("%02d", seconds);
    private JLabel timeLabel = new JLabel();
    // private JLabel difficultylbl;
    private JTextField textfield;
    private JFrame frame2;
    private JButton[] numButtons = new JButton[10];
    private JButton back, delButton, clrButton, enterButton, negButton, back2;
    private double result;
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);
    private Font titleFont = new Font("Ink Free", Font.BOLD, 40);
    private Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
    private HashMap<String, Integer> questions = new HashMap<String, Integer>();

    public MathGame() {
        frame = new JFrame("Brain Games");
        // frame.getContentPane().setBackground(Color.gray);
        frame.setTitle("Brain Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.setLayout(null);
        // setBackground(Color.white);

        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        title = new JLabel("Long addition/subtraction");
        title.setFont(titleFont);
        title.setBounds(90, 70, 500, 125);
        title.setForeground(Color.red);

        // for (int i = 0; i < 10; i++) {
        for (int i = 0; i < 10; i++) {
            int randint1 = (int) (Math.random() * 100) + 1;
            int randint2 = (int) (Math.random() * 100) + 1;
            // String operation;
            int answer;
            int choice = (int) (Math.random() * 2) + 1;
            String input = String.valueOf(randint1);
            if (choice == 1) {
                input += " + ";
                answer = randint1 + randint2;
            } else {
                input += " - ";
                answer = randint1 - randint2;
            }
            input += String.valueOf(randint2);
            questions.put(input, answer);

        }
        System.out.println(questions);

        question = 0;
        questionlbl = new JLabel(questions.keySet().stream().findFirst().get());
        questionlbl.setFont(new Font("Ink Free", Font.BOLD, 32));
        questionlbl.setBounds(250, 170, 400, 75);

        back = new JButton("Back");
        back.addActionListener(this);
        // Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
        back.setFont(tinyFont);
        back.setFocusable(false);
        back.setBounds(25, 15, 80, 30);

        questionNumlbl = new JLabel("Question: " + question + 1);
        questionNumlbl.setFont(myFont);
        questionNumlbl.setBounds(150, 15, 300, 75);

        scorelbl = new JLabel("Score: " + score);
        scorelbl.setFont(myFont);
        scorelbl.setBounds(320, 15, 200, 75);

        timeLabel.setText(hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel.setFont(myFont);
        timeLabel.setBounds(450, 15, 200, 75);

        timer.start();

        textfield = new JTextField();
        textfield.setBounds(75, 260, 500, 50);
        textfield.setFont(myFont);

        for (int n = 0; n < 10; n++) {
            numButtons[n] = new JButton(String.valueOf(n));
            numButtons[n].addActionListener(this);
            numButtons[n].setFont(myFont);
            numButtons[n].setFocusable(false);
        }

        negButton = new JButton("(-)");
        negButton.addActionListener(this);
        negButton.setFont(myFont);
        negButton.setFocusable(false);

        delButton = new JButton("del");
        delButton.addActionListener(this);
        delButton.setFont(myFont);
        delButton.setFocusable(false);

        clrButton = new JButton("clear");
        clrButton.addActionListener(this);
        clrButton.setFont(myFont);
        clrButton.setFocusable(false);

        numsPanel = new JPanel();
        numsPanel.setBounds(95, 410, 350, 150);
        numsPanel.setLayout(new GridLayout(2, 5, 5, 5));
        for (int j = 0; j < 10; j++) {
            numsPanel.add(numButtons[j]);
        }

        operPanel = new JPanel();
        operPanel.setBounds(450, 410, 100, 150);
        operPanel.setLayout(new GridLayout(3, 1, 5, 5));

        operPanel.add(delButton);
        operPanel.add(clrButton);
        operPanel.add(negButton);

        enterButton = new JButton("Enter");
        enterButton.addActionListener(this);
        enterButton.setFont(myFont);
        enterButton.setFocusable(false);
        enterButton.setBounds(260, 340, 100, 40);

        // frame.addKeyListener(new KeyListener() {

        // @Override
        // public void keyTyped(KeyEvent e) {
        // // TODO Auto-generated method stub

        // }

        // @Override
        // public void keyReleased(KeyEvent e) {
        // // TODO Auto-generated method stub

        // }
        // });

        frame.add(title);
        frame.add(questionlbl);
        frame.add(questionNumlbl);
        frame.add(scorelbl);
        frame.add(timeLabel);
        frame.add(textfield);
        frame.add(numsPanel);
        frame.add(operPanel);
        frame.add(back);
        frame.add(enterButton);
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

    // public void keyPressed(KeyEvent e) {
    // System.out.println("key pressed");
    // if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    // question++;
    // System.out.println(textfield.getText());
    // System.out.println("Question: " + question);
    // System.out.println("correct answer = " +
    // questions.get(questionlbl.getText()));
    // System.out.println("inputted answer = " + textfield.getText());

    // if (questions.get(questionlbl.getText()) ==
    // Integer.valueOf(textfield.getText())) {
    // score++;
    // }
    // System.out.println("Score: " + score);
    // if (question <= 9) {
    // newQuestion();
    // } else {
    // endScreen();
    // }
    // }
    // }

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

    public void newQuestion() {
        textfield.setText("");
        reset();
        questionNumlbl.setText("Question: " + (question + 1));
        scorelbl.setText("Score: " + score);
        frame.add(questionNumlbl);
        frame.add(scorelbl);
        String questionStr = (String) questions.keySet().toArray()[question];
        questionlbl.setText(questionStr);
        frame.add(questionlbl);
        timer.start();
    }

    public void endScreen() {
        frame.dispose();
        frame2 = new JFrame();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500, 600);
        frame2.setLayout(null);
        frame2.setForeground(Color.white);
        frame2.setTitle("Brain Games");

        JLabel title2 = new JLabel("Game Over");
        title2.setFont(titleFont);
        title2.setBounds(100, 25, 500, 100);
        title2.setForeground(Color.red);

        JLabel scorelbl2 = new JLabel("Score: " + String.valueOf(score));
        scorelbl2.setFont(myFont);
        scorelbl2.setBounds(100, 100, 500, 100);

        seconds_string = String.format("%02d", tltSecs);
        minutes_string = String.format("%02d", ttlMins);
        hours_string = String.format("%02d", ttlHrs);
        JLabel timeLabel2 = new JLabel("Time: " + hours_string + ":" + minutes_string + ":" + seconds_string);
        timeLabel2.setFont(myFont);
        timeLabel2.setBounds(100, 175, 500, 100);

        int totalTime = (ttlHrs * 3600) + (ttlMins * 60) + tltSecs;
        double vel = (double) totalTime / score;
        System.out.println("" + ttlHrs + ttlMins + tltSecs);
        System.out.println(vel);

        JLabel velLbl = new JLabel("Rate: " + String.valueOf(vel) + " \nsecs/correct answer");
        velLbl.setFont(new Font("Ink Free", Font.BOLD, 22));
        velLbl.setBounds(100, 250, 500, 100);

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
        bestVelLbl.setBounds(100, 325, 500, 100);

        back2 = new JButton("Back");
        back2.addActionListener(this);
        Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
        back2.setFont(tinyFont);
        back2.setFocusable(false);
        back2.setBounds(150, 450, 80, 30);

        frame2.add(title2);
        frame2.add(scorelbl2);
        frame2.add(velLbl);
        frame2.add(bestVelLbl);
        frame2.add(timeLabel2);
        frame2.add(back2);
        frame2.setVisible(true);
    }

    // public void keyPressed(KeyEvent e) {
    // if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    // question++;
    // System.out.println(textfield.getText());
    // System.out.println("Question: " + question);
    // System.out.println("correct answer = " +
    // questions.get(questionlbl.getText()));
    // System.out.println("inputted answer = " + textfield.getText());

    // if (questions.get(questionlbl.getText()) ==
    // Integer.valueOf(textfield.getText())) {
    // score++;
    // }
    // System.out.println("Score: " + score);
    // if (question <= 9) {
    // newQuestion();
    // } else {
    // endScreen();
    // }
    // }
    // }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == back) {
            frame.dispose();
            Options options = new Options();
        }

        if (e.getSource() == back2) {
            frame2.dispose();
            Options options = new Options();
        }

        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numButtons[i]) {
                textfield.setText(textfield.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == negButton) {
            if (textfield.getText().equals("")) {
                textfield.setText("-");
            }
        }

        if (e.getSource() == clrButton) {
            textfield.setText("");
        }

        if (e.getSource() == delButton) {
            String s = textfield.getText();
            textfield.setText("");
            s = s.substring(0, s.length() - 1);
            textfield.setText(s);
        }

        if (e.getSource() == enterButton) {
            question++;
            System.out.println(textfield.getText());
            System.out.println("Question: " + question);
            System.out.println("correct answer = " + questions.get(questionlbl.getText()));
            System.out.println("inputted answer = " + textfield.getText());

            if (questions.get(questionlbl.getText()) == Integer.valueOf(textfield.getText())) {
                score++;
            }
            System.out.println("Score: " + score);
            if (question <= 9) {
                newQuestion();
            } else {
                endScreen();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("key pressed");
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            question++;
            System.out.println(textfield.getText());
            System.out.println("Question: " + question);
            System.out.println("correct answer = " + questions.get(questionlbl.getText()));
            System.out.println("inputted answer = " + textfield.getText());

            if (questions.get(questionlbl.getText()) == Integer.valueOf(textfield.getText())) {
                score++;
            }
            System.out.println("Score: " + score);
            if (question <= 9) {
                newQuestion();
            } else {
                endScreen();
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    // @Override
    // public void keyPressed(KeyEvent e) {
    // if (e.getKeyCode() == KeyEvent.VK_ENTER) {
    // question++;
    // if (question <= 9) {
    // newQuestion();
    // } else {
    // JFrame frame = new JFrame();

    // }
    // }
    // }

}
