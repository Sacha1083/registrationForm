package gui;

import util.Log;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

/**
 * SplashScreen class
 * <p>
 *     Class for displaying a splash screen when the application starts. <br>
 *     It shows the application logo and a progress bar while the application loads.
 * </p>
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class SplashScreen extends JWindow  {

    /**
     * SplashScreen constructor
     * <p>
     *     Initializes the splash screen.
     * </p>
     * @since JDK21.0.5
     */
    public SplashScreen() {
    }

    /**
     * Show splash screen
     * <p>
     *     Displays the splash screen with the application logo and a progress bar. <br>
     *     The progress bar fills up randomly to simulate loading.
     * </p>
     * @since JDK21.0.5
     */
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
            Log.config("Splash screen loaded");
        } catch (NullPointerException e) {
            imageLabel.setText("Image not found");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            Log.error("Image not found - " + e.getMessage());
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
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Log.error("Error in the splash screen - " + e.getMessage());
            }
            Log.info("Loading progress: " + i + "%");
        }
        Log.config("Splash screen loaded");

        setVisible(false);
    }

    public void configSplashScreen() {
        setLayout(new BorderLayout());
        setSize(900, 500);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, 900, 500, 50, 50));
    }
}