package gui;

import util.TextData;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Menu {
    public static JMenuBar getMenu(App app) {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu(TextData.getText("menuFile"));
        JMenuItem showStatsMenuItem = new JMenuItem(TextData.getText("menuFileItem1"));
        JMenuItem exitMenuItem = new JMenuItem(TextData.getText("menuFileItem2"));
        fileMenu.add(showStatsMenuItem);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        showStatsMenuItem.addActionListener(e -> {
            app.showStatistics(app);
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
