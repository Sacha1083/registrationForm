package gui;

import gui.model.entity.Usuario;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsPanel {
    public static void getStatisticsPanel(App app, JPanel previousPanel) {
        JPanel statisticsPanel = new JPanel(new BorderLayout());
        statisticsPanel.setName("statisticsPanel");

        if (!app.containsPanel(statisticsPanel)) {
            UsuarioController usuarioController = new UsuarioController();
            List<Usuario> usuarios = usuarioController.getAllUsers();
            JPanel dataPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Parte Mostrar todos los usuarios
            String[] columnNames = {"ID", "Nombre", "Correo", "Contraseña", "Año Registro"};
            Object[][] data = new Object[usuarios.size()][5];
            for (int i = 0; i < usuarios.size(); i++) {
                data[i][0] = usuarios.get(i).getId();
                data[i][1] = usuarios.get(i).getName();
                data[i][2] = usuarios.get(i).getEmail();
                data[i][3] = usuarios.get(i).getPassword();
                data[i][4] = usuarios.get(i).getRegisterYear();
            }

            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);

            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridwidth = GridBagConstraints.CENTER;
            gbc.weightx = 0.7;
            gbc.weighty = 0.3;
            JLabel title = new JLabel("Usuarios: ");
            title.setFont(TextFont.titleFont());
            dataPanel.add(title, gbc);

            gbc.gridy = 1;
            gbc.weighty = 0.7;
            gbc.fill = GridBagConstraints.BOTH;
            dataPanel.add(scrollPane, gbc);

            // Parte gráficos
            int[] usersPerYear = new int[usuarios.size()];
            for (int i = 0; i < usuarios.size(); i++) {
                usersPerYear[i] = usuarios.get(i).getRegisterYear();
            }

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            Map<Integer, Long> usersCountPerYear = Arrays.stream(usersPerYear)
                    .boxed()
                    .collect(Collectors.groupingBy(year -> year, Collectors.counting()));

            usersCountPerYear.forEach((year, count) -> dataset.addValue(count, "Usuarios", String.valueOf(year)));

            JFreeChart chart = ChartFactory.createBarChart("Usuarios por año", "Año", "Usuarios", dataset, PlotOrientation.VERTICAL, false, true, false);
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(dataPanel.getWidth(), 900));

            gbc.gridy = 2;
            gbc.weighty = 1.0; // Ajusta la altura del chartPanel
            dataPanel.add(chartPanel, gbc);

            // Grafico lineal
            JFreeChart chart2 = ChartFactory.createLineChart("Usuarios por año", "Año", "Usuarios", dataset, PlotOrientation.VERTICAL, true, true, false);
            ChartPanel chartPanel2 = new ChartPanel(chart2);
            chartPanel2.setPreferredSize(new Dimension(dataPanel.getWidth(), 900));

            gbc.gridy = 3;
            gbc.weighty = 1.0;
            dataPanel.add(chartPanel2, gbc);

            // Parte botones
            JPanel buttonsPanel = new JPanel(new FlowLayout());
            JButton backButton = new JButton("Back");
            buttonsPanel.add(backButton);

            // Parte Final
            statisticsPanel.add(Menu.getMenu(app, previousPanel), BorderLayout.NORTH);
            statisticsPanel.add(dataPanel, BorderLayout.CENTER);
            statisticsPanel.add(buttonsPanel, BorderLayout.SOUTH);

            app.showStatistics(statisticsPanel);
            System.out.println("Showing statistics panel");

            backButton.addActionListener(l -> {
                app.quitStatistics(statisticsPanel, previousPanel);
            });
        } else {
            app.showPanel(statisticsPanel);
        }
    }
}
