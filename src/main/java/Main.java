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
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

/**
 * Main class
 *     The main class of the program. <br>
 *     It checks the integrity of the files and loads the application if the integrity is correct. <br>
 *     If the integrity of the files is not correct, it displays a message indicating this. <br>
 *     If an error occurs, the program ends. The @exits tag contains information about each exit code.
 *     <ul>
 *         <li>exits 0 - User canceled theme selection</li>
 *         <li>exits 1 - Error loading theme</li>
 *         <li>exits 2 - Error loading login window</li>
 *         <li>exits 3 - Error loading splash screen</li>
 *         <li>exits 4 - Error loading program</li>
 *     </ul>
 * @see FlatLightLaf#setup()  - FlatLaf library
 * @see TextData - The user selects a language and the program obtains from there the text to be displayed later.
 * @see TextData#getText(String)  - Gets the text depending on the language
 * @see JDialog - Dialog window
 * @see JOptionPane - Dialog window
 * @see App - Using the constructor of this class, the program starts.
 * @see #checkS3Bucket() - Method to check S3 bucket.
 * @see #loadApp() - Method that loads the application
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class Main {
    private static Path userDataPath;

    /**
     * Start of the program
     *
     * <p>
     *     Load the application, if the integrity of the files is correct. <br>
     *     If the integrity of the files is not correct, it displays a message indicating this. <br>
     *     If an error occurs, the program ends. The @exits tag contains information about each exit code.
     * </p>
     *
     * @since JDK21.0.5
     * @param args The command line arguments
     *
     * @see FlatLightLaf#setup()  - FlatLaf library
     * @see TextData - Using the constuctor, the user selects a language and the program obtains from there the text to be displayed later.TextData#getText(String)  - Gets the text depending on the language
     * @see JDialog - Dialog window
     * @see JOptionPane - Dialog window
     * @see #checkS3Bucket() - Method to check S3 bucket.
     * @see #loadApp() - Method that loads the application
     */
    public static void main(String[] args) {
        FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        new TextData();
        loadEnvVariables();

        if (checkS3Bucket()) {
            if (checkFileIntegrity()) {
                loadApp();
            } else {
                showErrorDialog(TextData.getText("fileError"), 5);
            }
        } else {
            showErrorDialog(TextData.getText("s3Error"), 5);
        }
    }

    /**
     * Checks if the S3 bucket exists and if the user has access to it.
     *
     * @return true if the bucket exists and the user has access, false otherwise
     * @see BackupData#checkS3Bucket() - Method that checks the S3 bucket
     * @since JDK21.0.5
     */
    private static boolean checkS3Bucket() {
        return BackupData.checkS3Bucket();
    }

    /**
     * Check the integrity of the files
     * if the .env file exists return true, otherwise return false
     * @return true if the .env file exists, false otherwise
     */
    private static boolean checkFileIntegrity() {
        File file = new File(System.getProperty("user.dir"));
        return file.exists();
    }

    /**
     * Load the application
     *
     * <p>
     *     Load the application.<br>
     *     If an error occurs, the program ends.<br>
     *     <ul>
     *         <li>exits 0 - User canceled theme selection</li>
     *         <li>exits 1 - Error loading theme</li>
     *         <li>exits 2 - Error loading login window</li>
     *         <li>exits 3 - Error loading splash screen</li>
     *         <li>exits 4 - Error loading program</li>
     *     </ul>
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
                showErrorDialog(TextData.getText("console&err.errorLoadingTheme"), 1);
            }

            // Login JWindow
            CountDownLatch latch = new CountDownLatch(1);
            Thread loginThread = new Thread(() -> {
                try {
                    LoginWindow loginWindow = new LoginWindow(latch);
                    loginWindow.setVisible(true);
                } catch (Exception e) {
                    showErrorDialog(TextData.getText("console&err.errorLoadingLoginWindow"), 2);
                }

            });
            loginThread.start();
            latch.await();


            // Show splash screen
            try {
                gui.SplashScreen splash = new SplashScreen();
                splash.showSplash();
            } catch (Exception ex) {
                showErrorDialog(TextData.getText("console&err.errorLoadingSplashScreen"), 3);
            }

            // Start the app
            java.awt.EventQueue.invokeLater(() -> {
                App app = new App();
                app.setVisible(true);
            });
        } catch (Exception e) {
            showErrorDialog(TextData.getText("console&err.errorLoadingProgram"), 4);
        }
    }

    /**
     * Show an error dialog
     * @param message the message to display
     * @param exitCode the exit code
     */
    private static void showErrorDialog(String message, int exitCode) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, message, "Error", JOptionPane.ERROR_MESSAGE);
        System.out.println(message);
        System.exit(exitCode);
    }

    /**
     * Load the environment variables from the .env file
     */
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