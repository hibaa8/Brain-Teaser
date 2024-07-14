package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Options implements ActionListener {
    private JFrame frame;
    private JLabel title;
    private JButton math, memory, words, back;
    private Font myFont = new Font("Ink Free", Font.BOLD, 25);

    public Options() {
        frame = new JFrame("Brain Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);

        title = new JLabel("Game Options");
        Font titleFont = new Font("Ink Free", Font.BOLD, 50);
        title.setFont(titleFont);
        title.setBounds(125, 50, 400, 75);
        title.setForeground(Color.red);

        math = new JButton("Math");
        math.addActionListener(this);
        math.setFont(myFont);
        math.setFocusable(false);
        math.setBounds(150, 150, 250, 80);

        memory = new JButton("Memory");
        memory.addActionListener(this);
        memory.setFont(myFont);
        memory.setFocusable(false);
        memory.setBounds(150, 300, 250, 80);

        words = new JButton("Word");
        words.addActionListener(this);
        words.setFont(myFont);
        words.setFocusable(false);
        words.setBounds(150, 450, 250, 80);

        back = new JButton("Back");
        back.addActionListener(this);
        Font tinyFont = new Font("Ink Free", Font.BOLD, 15);
        back.setFont(tinyFont);
        back.setFocusable(false);
        back.setBounds(25, 15, 80, 30);

        frame.add(title);
        frame.add(back);
        frame.add(math);
        frame.add(memory);
        frame.add(words);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == math) {
            frame.dispose();
            new MathGame();
        }

        if (e.getSource() == memory) {
            frame.dispose();
            new Memory();
        }

        if (e.getSource() == words) {
            frame.dispose();
            new Words();
        }

        if (e.getSource() == back) {
            frame.dispose();
            new startScreen();
        }

    }

}
