package gui;

import gui.model.entity.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class App extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static RegisterPanel registerPanel;
    private static CountryPanel countryPanel;
    private static DisplayData displayData;
    private UsuarioController usuarioController;

    public App() {
        // Configure the app & app icon
        configApp();

        // Create the main panel and add the panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // App panels
        PrincipalPanel principalPanel = new PrincipalPanel(this);
        registerPanel = new RegisterPanel(this);
        countryPanel = new CountryPanel(this);
        displayData = new DisplayData(this);
        FinishPanel finishPanel = new FinishPanel(this);

        mainPanel.add(principalPanel, "PrincipalPanel");
        mainPanel.add(registerPanel, "NextPanel");
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

    // Getters
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

    public void showStatistics(App app) {
        JPanel statisticsPanel = new JPanel(new BorderLayout());
        JPanel dataPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        List<Usuario> usuarios = usuarioController.getAllUsers();
        String[] columnNames = {"ID", "Nombre", "Correo", "Contraseña"};
        Object[][] data = new Object[usuarios.size()][4];
        for (int i = 0; i < usuarios.size(); i++) {
            data[i][0] = usuarios.get(i).getId();
            data[i][1] = usuarios.get(i).getName();
            data[i][2] = usuarios.get(i).getEmail();
            data[i][3] = usuarios.get(i).getPassword();
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        gbc.gridy = 0;
        gbc.gridx = 0;
        dataPanel.add(new JLabel("Usuarios: "), gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridy = 1;
        dataPanel.add(scrollPane, gbc);

        JButton backButton = new JButton("Back");

        statisticsPanel.add(Menu.getMenu(app), BorderLayout.NORTH);
        statisticsPanel.add(dataPanel, BorderLayout.CENTER);
        statisticsPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(statisticsPanel, "dataPanel");
        cardLayout.show(mainPanel, "dataPanel");

        backButton.addActionListener(e -> {
            mainPanel.remove(statisticsPanel);
            cardLayout.show(mainPanel, "PrincipalPanel");
        });
    }
}