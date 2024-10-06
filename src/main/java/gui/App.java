package gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static PrincipalPanel principalPanel;
    private static LoginPanel nextPanel;
    private static CountryPanel countryPanel;

    public App() {
        setTitle("App"); // Titulo
        setSize(900, 700); // Tamaño
        setMinimumSize(new Dimension(900, 700)); // Tamaño minimo
        setLocationRelativeTo(null); // Centrar
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

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        principalPanel = new gui.PrincipalPanel(this);
        nextPanel = new LoginPanel(this);
        countryPanel = new CountryPanel(this);

        mainPanel.add(principalPanel, "PrincipalPanel");
        mainPanel.add(nextPanel, "NextPanel");
        mainPanel.add(countryPanel, "CountryPanel");

        add(mainPanel);
    }

    public void nextPanel() {
        cardLayout.next(mainPanel);
    }

    public void previousPanel() {
        cardLayout.previous(mainPanel);
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