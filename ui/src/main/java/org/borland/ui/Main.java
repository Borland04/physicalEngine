package org.borland.ui;

import javax.swing.*;

public class Main {
    private JPanel mainPanel;
    private JButton button1;
    private JPanel worldRenderPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
