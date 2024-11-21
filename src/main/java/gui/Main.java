package gui;

import com.formdev.flatlaf.FlatLightLaf;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
                try {
                    JDialog loginDialog = new JDialog((Frame) null, "Login", true);
                    new LoginWindow();
                    loginDialog.setContentPane(LoginWindow.getLoginWindow());
                    loginDialog.setMinimumSize(new Dimension(800, 600));
                    loginDialog.setSize(900, 800);
                    loginDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    loginDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    loginDialog.pack();
                    loginDialog.setLocationRelativeTo(null);
                    loginDialog.setVisible(true);
                } catch (Exception e) {
                    System.out.println("Error loading login window - " + e.getMessage());
                    System.exit(2);
                }

                // Show splash screen
                try {
                    SplashScreen splash = new SplashScreen(1000);
                    splash.showSplash();
                } catch (Exception e) {
                    System.out.println("Error loading splash screen - " + e.getMessage());
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

        if (userFile.exists()) {
            return true;
        } else {
            try {
                return userFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}