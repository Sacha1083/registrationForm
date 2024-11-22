package gui;

import com.formdev.flatlaf.FlatLightLaf;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

public class Main {
    /**
     * Start the app
     * @exits 0 - User canceled theme selection, 1 - Error loading theme, 2 - Error loading login window, 3 - Error loading splash screen, 4 - Error loading program
     */
    public static void main(String[] args) {
        if (checkFileIntegrity()) {
            try {
                FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
                String[] options = {"Light Theme", "Dark Theme"};

                // Create a custom panel with a gradient background
                JPanel panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        int width = getWidth();
                        int height = getHeight();
                        Color color1 = Color.LIGHT_GRAY;
                        Color color2 = Color.WHITE;
                        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                        g2d.setPaint(gp);
                        g2d.fillRect(0, 0, width, height);
                    }
                };
                JLabel label = new JLabel("Choose a theme");
                label.setFont(TextFont.textFormFont());
                panel.add(label);

                int choice = JOptionPane.showOptionDialog(null, panel, "Theme Selection",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                try {
                    if (choice == 1) {
                        System.out.println("Dark Theme Selected");
                        FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacDarkLaf());
                    } else if (choice == 0) {
                        System.out.println("Light Theme Selected");
                        FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
                    } else {
                        System.out.println("User canceled theme selection");
                        System.exit(0);
                    }
                } catch (Exception e) {
                    JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(dialog, "Error loading theme", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Error loading theme - " + e.getMessage());
                    System.exit(1);
                }

                // Login JWindow
                CountDownLatch latch = new CountDownLatch(1);
                Thread loginThread = new Thread(() -> {
                    try {
                        LoginWindow loginWindow = new LoginWindow(latch);
                        loginWindow.setVisible(true);
                    } catch (Exception e) {
                        System.out.println("Error loading login window - " + e.getMessage());
                        System.exit(2);
                    }
                });
                loginThread.start();
                latch.await();


                // Show splash screen
                try {
                    SplashScreen splash = new SplashScreen();
                    splash.showSplash();
                } catch (Exception ex) {
                    System.out.println("Error loading splash screen - " + ex.getMessage());
                    System.exit(3);
                }

                // Start the app
                java.awt.EventQueue.invokeLater(() -> {
                    App app = new App();
                    app.setVisible(true);
                });
            } catch (Exception e) {
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, "Error loading program", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Error loading program - " + e.getMessage());
                System.exit(4);
            }
        } else {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Error loading program", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error loading program - File integrity check failed");
            System.exit(4);
        }
    }

    private static boolean checkFileIntegrity() {
        String users = Paths.get(System.getProperty("user.dir"),"user.dad").toString();
        File userFile = new File(users);

        if (!userFile.exists()) {
            System.out.println("Creating new user file because it does not exist");
            Usuario nuevoUsuario = new Usuario("admin", "admin", "admin");
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Paths.get(System.getProperty("user.dir"), "user.dad").toString(), false));
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(nuevoUsuario);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }
        return true;
    }
}