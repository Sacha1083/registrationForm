package gui;

import com.formdev.flatlaf.FlatLightLaf;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static LoginPanel loginPanel;
    private static CountryPanel countryPanel;
    private static DisplayData displayData;

    public App() {
        // Configure the app & app icon
        configApp();

        // Create the main panel and add the panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // App panels
        PrincipalPanel principalPanel = new PrincipalPanel(this);
        loginPanel = new LoginPanel(this);
        countryPanel = new CountryPanel(this);
        displayData = new DisplayData(this);
        FinishPanel finishPanel = new FinishPanel(this);

        mainPanel.add(principalPanel, "PrincipalPanel");
        mainPanel.add(loginPanel, "NextPanel");
        mainPanel.add(countryPanel, "CountryPanel");
        mainPanel.add(displayData, "DisplayData");
        mainPanel.add(finishPanel, "FinishPanel");

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

    // Getters
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

    // Start the app
    public static void main(String[] args) {
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
                System.out.println("Error loading theme");
                System.exit(1);
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
            System.out.println("Error loading program");
            System.exit(1);
        }
    }
}