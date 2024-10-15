package gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static PrincipalPanel principalPanel;
    private static LoginPanel loginPanel;
    private static CountryPanel countryPanel;
    private static DisplayData displayData;
    private static FinishPanel finishPanel;

    public App() {
        // Set the title, size, location, close operation and icon of the app
        setTitle("App");
        setSize(900, 700);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        try {
            setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/icon.png"))).getImage());
        } catch (NullPointerException e) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Error loading app icon", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error loading app icon");
            System.exit(0);
        }

        // Create the main panel and add the panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        principalPanel = new PrincipalPanel(this);
        loginPanel = new LoginPanel(this);
        countryPanel = new CountryPanel(this);
        displayData = new DisplayData(this);
        finishPanel = new FinishPanel(this);

        mainPanel.add(principalPanel, "PrincipalPanel");
        mainPanel.add(loginPanel, "NextPanel");
        mainPanel.add(countryPanel, "CountryPanel");
        mainPanel.add(displayData, "DisplayData");
        mainPanel.add(finishPanel, "FinishPanel");

        add(mainPanel);
    }

    public void nextPanel() {
        cardLayout.next(mainPanel);
        displayData.updateData(this);
    }

    public void previousPanel() {
        cardLayout.previous(mainPanel);
    }

    public String getUserName() {
        return loginPanel.getUserName();
    }

    public String geteMail() {
        return loginPanel.geteMail();
    }

    public String getPassword() {
        return loginPanel.getPassword();
    }

    public String getCountry() {
        return countryPanel.getCountry();
    }

    public String getProvince() {
        return countryPanel.getProvince();
    }

    public static void main(String[] args) {
        try {
            FlatLightLaf.setup(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}