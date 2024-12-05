import com.formdev.flatlaf.FlatLightLaf;
import gui.App;
import gui.LoginWindow;
import gui.SplashScreen;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static Path userDataPath;
    /**
     * Start the app
     * @exits 0 - User canceled theme selection, 1 - Error loading theme, 2 - Error loading login window, 3 - Error loading splash screen, 4 - Error loading program 5 - File integrity check failed
     */
    public static void main(String[] args) {
        FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        new TextData();

        if (checkFileIntegrity()) {
            loadApp();

        } else {
            // Hay un problema con los archivos. Por favor, reinstale el programa.
            String message = TextData.getText("console&err.fileIntegrityCheckFailed");
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(message);
            System.exit(5);
        }
    }

    private static boolean checkFileIntegrity() {
        userDataPath = Paths.get(System.getProperty("user.dir"), "data", "userData.db");
        return Files.exists(userDataPath);
    }

    private static Path getUserDataPath() {
        return userDataPath;
    }

    private static void loadApp() {
        try {
            String[] options = {TextData.getText("theme.light"), TextData.getText("theme.dark")};

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

            JLabel label = new JLabel(TextData.getText("theme.title"));
            label.setFont(TextFont.textFormFont());
            panel.add(label);

            int choice = JOptionPane.showOptionDialog(null, panel, TextData.getText("theme.window.title"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            try {
                if (choice == 1) {
                    System.out.println(TextData.getText("console&label.darkThemeSelected"));
                    FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacDarkLaf());
                } else if (choice == 0) {
                    System.out.println(TextData.getText("console&label.lightThemeSelected"));
                    FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
                } else {
                    System.out.println(TextData.getText("console&err.userCanceledThemeSelection"));
                    System.exit(0);
                }
            } catch (Exception e) {
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                String message = TextData.getText("console&err.errorLoadingTheme");
                JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(message + " - " + e.getMessage());
                System.exit(1);
            }

            // Login JWindow
            CountDownLatch latch = new CountDownLatch(1);
            Thread loginThread = new Thread(() -> {
                try {
                    LoginWindow loginWindow = new LoginWindow(latch);
                    loginWindow.setVisible(true);
                } catch (Exception e) {
                    String message = TextData.getText("console&err.errorLoadingLoginWindow");
                    System.out.println(message + " - " + e.getMessage());
                    JDialog dialog = new JDialog();
                    dialog.setAlwaysOnTop(true);
                    JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(2);
                }

            });
            loginThread.start();
            latch.await();


            // Show splash screen
            try {
                gui.SplashScreen splash = new SplashScreen();
                splash.showSplash();
            } catch (Exception ex) {
                String message = TextData.getText("console&err.errorLoadingSplashScreen");
                System.out.println(message + " - " + ex.getMessage());
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(3);
            }

            // Start the app
            java.awt.EventQueue.invokeLater(() -> {
                App app = new App();
                app.setVisible(true);
            });
        } catch (Exception e) {
            String message = TextData.getText("console&err.errorLoadingProgram");
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(message + " - " + e.getMessage());
            System.exit(4);
        }
    }
}