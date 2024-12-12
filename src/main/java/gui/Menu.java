package gui;

import util.TextData;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * <h1>Menu class</h1>
 * <p>
 *     Class for creating the application menu. <br>
 *     It contains the file and info menus with their respective items.
 * </p>
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class Menu {

    /**
     * <h1>Get menu</h1>
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
        JMenuItem exitMenuItem = new JMenuItem(TextData.getText("menuFileItem2"));
        fileMenu.add(showStatsMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        showStatsMenuItem.addActionListener(e -> {
            StatisticsPanel.getStatisticsPanel(app, previousPanel);
        });

        exitMenuItem.addActionListener(e -> {
            System.exit(0);
        });

        // Info menu
        JMenu infoMenu = new JMenu(TextData.getText("menuInfo"));
        JMenuItem helpMenuItem = new JMenuItem(TextData.getText("menuInfoItem1"));
        infoMenu.add(helpMenuItem);
        menuBar.add(infoMenu);

        helpMenuItem.addActionListener(e -> {
            URL url = null;
            try {
                url = new URL("https://github.com/Sacha1083/");
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (URISyntaxException exe) {
                    exe.printStackTrace();
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            }
        });

        return menuBar;
    }
}
