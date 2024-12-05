package gui;

import gui.model.entity.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsPanel {
    public static void getStatisticsPanel(App app, JPanel previousPanel) {
        UsuarioController usuarioController = new UsuarioController();
        JPanel statisticsPanel = new JPanel(new BorderLayout());
        JPanel dataPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        statisticsPanel.setName("statisticsPanel");

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

        statisticsPanel.add(Menu.getMenu(app, previousPanel), BorderLayout.NORTH);
        statisticsPanel.add(dataPanel, BorderLayout.CENTER);
        statisticsPanel.add(backButton, BorderLayout.SOUTH);

        if (!app.containsPanel(statisticsPanel)) {
            app.showStatistics(statisticsPanel);
            System.out.println("se añade el panel");
        } else {
            System.out.println("No se vuelve a añadir el panel");
        }

        backButton.addActionListener(l -> {
            app.quitStatistics(statisticsPanel, previousPanel);
        });
    }
}
