package gui.view;

import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

// https://docs.oracle.com/javase/tutorial/uiswing/misc/splashscreen.html
public class SplashScreen extends JWindow  {
    public SplashScreen() {
    }

    public void showSplash() {
        configSplashScreen();
        JPanel imagePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel imageCopyPanel = new JPanel(new BorderLayout());

        JLabel imageLabel = new JLabel();
        try {
            Image img = (new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/logo.jpg"))).getImage());
            Image newImg = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(newImg));
        } catch (NullPointerException e) {
            imageLabel.setText("Image not found");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            System.out.println("Image not found - " + e.getMessage());
        }

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true);

        JLabel copyrightLabel = new JLabel("Copyright© 2024", JLabel.CENTER);
        copyrightLabel.setFont(TextFont.textFormFont());

        gbc.fill = GridBagConstraints.HORIZONTAL;
        imagePanel.add(imageLabel, gbc);
        imageCopyPanel.add(imagePanel, BorderLayout.CENTER);
        imageCopyPanel.add(copyrightLabel, BorderLayout.SOUTH);
        add(imageCopyPanel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        setVisible(true);

        for (int i = 0; i <= 100; i++) {
            progressBar.setValue(i);
            try {
                Thread.sleep((int) (Math.random() * 100));
                if (Math.random() < 0.1) {
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                System.out.println("Error in the splash screen - " + e.getMessage());
            }
        }

        setVisible(false);
    }

    public void configSplashScreen() {
        setLayout(new BorderLayout());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 500, 50, 50));
    }
}