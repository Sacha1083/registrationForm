package gui;

import gui.model.entity.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static PrincipalPanel principalPanel;
    private static RegisterPanel registerPanel;
    private static CountryPanel countryPanel;
    private static DisplayData displayData;
    private static FinishPanel finishPanel;
    private UsuarioController usuarioController;

    public App() {
        // Configure the app & app icon
        configApp();

        // Create the main panel and add the panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // App panels
        principalPanel = new PrincipalPanel(this);
        registerPanel = new RegisterPanel(this);
        countryPanel = new CountryPanel(this);
        displayData = new DisplayData(this);
        finishPanel = new FinishPanel(this);

        mainPanel.add(principalPanel, principalPanel.getName());
        mainPanel.add(registerPanel, registerPanel.getName());
        mainPanel.add(countryPanel, countryPanel.getName());
        mainPanel.add(displayData, displayData.getName());
        mainPanel.add(finishPanel, finishPanel.getName());

        // Add the main panel to the principal frame
        add(mainPanel);
    }

    // Set the title, size, location and close operation
    private void configApp() {
        setTitle("App");
        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        usuarioController = new UsuarioController();
        addIcon();
    }

    // Add the app icon
    private void addIcon() {
        try {
            setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/icon.png"))).getImage());
        } catch (NullPointerException e) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Error loading app icon", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error loading app icon");
            System.exit(0);
        }
    }

    // Method to next panel
    public void nextPanel() {
        cardLayout.next(mainPanel);
        displayData.updateData(this);
    }

    // Method to previous panel
    public void previousPanel() {
        cardLayout.previous(mainPanel);
    }

    public void showPanel(JPanel panel) {
        cardLayout.show(mainPanel, panel.getName());
    }

    public void showStatistics(JPanel statisticsPanel) {
        mainPanel.add(statisticsPanel, statisticsPanel.getName());
        cardLayout.show(mainPanel, statisticsPanel.getName());
    }

    public void quitStatistics(JPanel statisticsPanel, JPanel previousPanel) {
        mainPanel.remove(statisticsPanel);
        cardLayout.show(mainPanel, previousPanel.getName());
    }

    public boolean containsPanel(JPanel panel) {
        List<Component> panels = List.of(mainPanel.getComponents());

        for (Component p : panels) {
            if (p.getName().equals(panel.getName())) {
                return true;
            }
        }

        return false;
    }

    public String getUserName() {
        return registerPanel.getUserName();
    }

    public String geteMail() {
        return registerPanel.geteMail();
    }

    public String getPassword() {
        return registerPanel.getPassword();
    }

    public String getCountry() {
        return countryPanel.getCountry();
    }

    public String getProvince() {
        return countryPanel.getProvince();
    }
}