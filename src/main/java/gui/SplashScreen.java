package gui;

import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

// https://docs.oracle.com/javase/tutorial/uiswing/examples/misc/SplashDemoProject/src/misc/SplashDemo.java
public class SplashScreen extends JWindow  {
    private final int duration;

    public SplashScreen(int duration) {
        this.duration = duration;
    }

    public void showSplash() {
        JPanel content = new JPanel(new BorderLayout());
        setContentPane(content);
        setSize(900, 800);
        setLocationRelativeTo(null);

        JLabel label = new JLabel();
        try {
            Image img = (new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/logo.jpg"))).getImage());
            Image newImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(newImg));
        } catch (NullPointerException e) {
            label.setText("Image not found");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            System.out.println("Image not found - " + e.getMessage());
        }

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);

        JLabel copyrt = new JLabel("Copyright 2024", JLabel.CENTER);
        copyrt.setFont(TextFont.textFormFont());

        content.add(label, BorderLayout.NORTH);
        content.add(copyrt, BorderLayout.CENTER);
        content.add(progressBar, BorderLayout.SOUTH);

        setVisible(true);

        for (int i = 0; i <= 100; i++) {
            progressBar.setValue(i);
            try {
                Thread.sleep(duration / 100);
            } catch (InterruptedException e) {
                System.out.println("Error in the splash screen - " + e.getMessage());
            }
        }

        setVisible(false);
    }
}