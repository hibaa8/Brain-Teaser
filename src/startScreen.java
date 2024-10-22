package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class startScreen implements ActionListener {
    private JFrame frame;
    private JLabel title;
    private JButton start, continueGame;
    private Font myFont = new Font("Ink Free", Font.BOLD, 30);

    public startScreen() {
        frame = new JFrame("Brain Games");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setForeground(Color.white);

        title = new JLabel("Brain Games");
        Font titleFont = new Font("Ink Free", Font.BOLD, 50);
        title.setFont(titleFont);
        title.setBounds(125, 50, 400, 75);
        title.setForeground(Color.red);

        JLabel img = new JLabel(new ImageIcon(getClass().getResource("../img/start_img.png")));
        img.setBounds(125, 150, 300, 200);

        start = new JButton("Start");
        start.addActionListener(this);
        start.setFont(myFont);
        start.setFocusable(false);
        start.setBounds(190, 400, 150, 50);

        frame.add(title);
        frame.add(img);
        frame.add(start);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            frame.dispose();
            new Options();
        }

        if (e.getSource() == continueGame) {
            new MathGame();
        }

    }

}
