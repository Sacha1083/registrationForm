package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class CountryPanel extends JPanel {
    private static JComboBox<String> countrySelector;
    private static JComboBox<String> provinceSelector;

    public CountryPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0; // Fila
        gbc.gridx = 0; // Columna
        gbc.gridheight = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.LEFT);
        imageLabel.setVerticalAlignment(JLabel.TOP);

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/country.jpg")));
        Image originalImage = imageIcon.getImage();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Dimension size = getSize();
                int newHeight = size.height;
                int newWidth = (int) (newHeight * 0.75);

                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            }
        });

        add(imageLabel, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.6;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        JLabel titleForm = new JLabel("Select a country and province");
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        JPanel selectorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputConstrain = new GridBagConstraints();
        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        inputConstrain.insets = new Insets(10, 10, 10, 10);
        inputConstrain.anchor = GridBagConstraints.CENTER;

        // Country
        String[] countries = {"Spain", "Swiss"};
        countrySelector = new JComboBox<>(countries);
        countrySelector.setPreferredSize(new Dimension(200, 30));

        // Province
        String[] provincesSpain = {"Madrid", "Barcelona", "Valencia"};
        String[] provincesSwiss = {"Zurich", "Geneva", "Basel"};
        provinceSelector = new JComboBox<>(provincesSpain);
        provinceSelector.setPreferredSize(new Dimension(200, 30));

        // Add country and province selectors to the panel
        selectorPanel.add(titleForm, inputConstrain);
        inputConstrain.gridy = 1;
        selectorPanel.add(countrySelector, inputConstrain);
        inputConstrain.gridy = 2;
        selectorPanel.add(provinceSelector, inputConstrain);

        // Add the selector panel to the main panel
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        add(selectorPanel, gbc);

        countrySelector.addActionListener(e -> {
            String country = (String) countrySelector.getSelectedItem();
            provinceSelector.removeAllItems();
            if (Objects.equals(country, "Spain")) {
                Arrays.stream(provincesSpain).forEach(provinceSelector::addItem);
            } else {
                Arrays.stream(provincesSwiss).forEach(provinceSelector::addItem);
            }
        });

        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.previousPanel());
        add(backButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String country = (String) countrySelector.getSelectedItem();
            String province = (String) provinceSelector.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Country: " + country + "\nProvince: " + province);
        });
        add(sendButton, gbc);
    }
}