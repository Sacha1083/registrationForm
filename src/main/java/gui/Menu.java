package gui;

import util.BackupData;
import util.TextData;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * Menu class
 * <p>
 *     Class for creating the application menu. <br>
 *     It contains the file and info menus with their respective items.
 * </p>
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class Menu {

    /**
     * Get menu
     * <p>
     *     Creates the application menu with the file and info menus. <br>
     *     It adds the menu items to the menus and sets up the action listeners for each item.
     * </p>
     * @param app The main application class
     * @param previousPanel The previous panel
     * @return The application menu
     * @see App - The main application class
     * @see JPanel - Container for the different panels
     * @since JDK21.0.5
     */
    public static JMenuBar getMenu(App app, JPanel previousPanel) {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu(TextData.getText("menuFile"));
        JMenuItem showStatsMenuItem = new JMenuItem(TextData.getText("menuFileItem1"));
        JMenuItem backupMenuItem = new JMenuItem(TextData.getText("menuFileItem3"));
        JMenuItem exitMenuItem = new JMenuItem(TextData.getText("menuFileItem2"));
        fileMenu.add(showStatsMenuItem);
        fileMenu.add(backupMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        showStatsMenuItem.addActionListener((e) -> StatisticsPanel.getStatisticsPanel(app, previousPanel));
        exitMenuItem.addActionListener((e) -> System.exit(0));
        backupMenuItem.addActionListener((e) -> {
            if (BackupData.updateData()) {
                JOptionPane.showMessageDialog(app, TextData.getText("backupSuccess"), TextData.getText("backupSuccessTitle"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(app, TextData.getText("backupError"), TextData.getText("backupErrorTitle"), JOptionPane.ERROR_MESSAGE);
            }
        });

        // Info menu
        JMenu infoMenu = new JMenu(TextData.getText("menuInfo"));
        JMenuItem helpMenuItem = new JMenuItem(TextData.getText("menuInfoItem1"));
        infoMenu.add(helpMenuItem);
        menuBar.add(infoMenu);

        helpMenuItem.addActionListener((e) -> {
            try {
                URL url = Paths.get(System.getProperty("user.dir"), "javadoc", "apidocs", "index.html").toUri().toURL();
                if (url != null) {
                    Desktop.getDesktop().browse(url.toURI());
                } else {
                    JOptionPane.showMessageDialog(app, TextData.getText("javadocNotFound"), TextData.getText("errorTitle"), JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
                showErrorDialog(TextData.getText("javadocError"), 5);
            }
        });

        return menuBar;
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
        dialog.setVisible(true);
        System.err.println(message);
        System.exit(exitCode);
    }
}
