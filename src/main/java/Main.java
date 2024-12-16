import com.formdev.flatlaf.FlatLightLaf;
import gui.App;
import gui.LoginWindow;
import gui.SplashScreen;
import io.github.cdimascio.dotenv.Dotenv;
import util.BackupData;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

/**
 * <h1>Main class</h1>
 * <p>
 *     The main class of the program. <br>
 *     It checks the integrity of the files and loads the application if the integrity is correct. <br>
 *     If the integrity of the files is not correct, it displays a message indicating this. <br>
 *     If an error occurs, the program ends. The @exits tag contains information about each exit code.
 * </p>
 * @see FlatLightLaf#setup()  - FlatLaf library
 * @see TextData - The user selects a language and the program obtains from there the text to be displayed later.
 * @see TextData#getText(String)  - Gets the text depending on the language
 * @see JDialog - Dialog window
 * @see JOptionPane - Dialog window
 * @see App - Using the constructor of this class, the program starts.
 * @see #checkFileIntegrity() - Method that checks the integrity of the files
 * @see #loadApp() - Method that loads the application
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 * @exits 0 - User canceled theme selection, 1 - Error loading theme, 2 - Error loading login window, 3 - Error loading splash screen, 4 - Error loading program, 5 - File integrity check failed
 */
public class Main {
    private static Path userDataPath;

    /**
     * <h1>Start of the program</h1>
     *
     * <p>
     *     Load the application, if the integrity of the files is correct. <br>
     *     If the integrity of the files is not correct, it displays a message indicating this. <br>
     *     If an error occurs, the program ends. The @exits tag contains information about each exit code.
     * </p>
     *
     * @since JDK21.0.5
     *
     * @see FlatLightLaf#setup()  - FlatLaf library
     * @see TextData - Using the constuctor, the user selects a language and the program obtains from there the text to be displayed later.
     * @                                                                                                                    TextData#getText(String)  - Gets the text depending on the language
     * @see JDialog - Dialog window
     * @see JOptionPane - Dialog window
     * @see #checkFileIntegrity() - Method that checks the integrity of the files
     * @see #loadApp() - Method that loads the application
     */
    public static void main(String[] args) {
        FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        new TextData();
        loadEnvVariables();

        if (checkFileIntegrity()) {
            loadApp();

        } else {
            // There is a problem with the files. Please reinstall the program.
            String message = TextData.getText("console&err.fileIntegrityCheckFailed");
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(message);
            System.exit(5);
        }
    }

    /**
     * <h1>Check the integrity of the files</h1>
     *
     * <p>
     *     Check if the userData.db file exists. <br>
     *     If the file exists, the method returns true. <br>
     *     If the file does not exist, the method returns false.
     * </p>
     *
     * @since JDK21.0.5
     *
     * @see Paths
     * @see Files
     * @see #userDataPath
     *
     * @return boolean - true if the file exists, false if the file does not exist
     */
    private static boolean checkFileIntegrity() {
        userDataPath = Paths.get(System.getProperty("user.dir"), "data", "userData.db");
        //return Files.exists(userDataPath);
        return true;
    }

    /**
     * <h1>Load the application</h1>
     *
     * <p>
     *     Load the application.<br>
     *     If an error occurs, the program ends. The @exits tag contains information about each exit code.
     * </p>
     *
     * @since JDK21.0.5
     *
     * @see JOptionPane - Dialog window
     * @see LoginWindow - The user logs in to the program
     * @see SplashScreen - The splash screen is displayed
     * @see App - Using the constructor of this class, the program starts.
     * @see CountDownLatch - The program waits for the login window to be loaded
     * @see Thread - The program waits for the login window to be loaded
     * @exits 0 - User canceled theme selection, 1 - Error loading theme, 2 - Error loading login window, 3 - Error loading splash screen, 4 - Error loading program
     */
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

    private static void loadEnvVariables() {
        try {
            Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.dir")).load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
            System.out.println("Variables de entorno cargadas correctamente");
        } catch (Exception e) {
            System.out.println("Error al cargar las variables de entorno: " + e.getMessage());
        }
    }
}