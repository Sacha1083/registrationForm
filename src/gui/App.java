package gui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    public App() {
        setTitle("App"); // Titulo
        setSize(900, 640); // Tamaño
        setMinimumSize(new Dimension(900, 640)); // Tamaño minimo
        setLocationRelativeTo(null); // Centrar
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/icon.png"))).getImage());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel principalPanel = new PrincipalPanel(this);
        JPanel nextPanel = new gui.FormPanel(this);

        mainPanel.add(principalPanel, "PrincipalPanel");
        mainPanel.add(nextPanel, "NextPanel");

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