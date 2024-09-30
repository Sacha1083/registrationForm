package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class CountryPanel extends JPanel {
    private static JComboBox<String> countrySelector;
    public CountryPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight() / 2;
        int width = (int) (height * 0.33);

        // Title
        gbc.gridy = 0; // Fila
        gbc.gridx = 1; // Columna
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel titleForm = new JLabel("Select a country and province");
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Image
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/country.jpg")));
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(image.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        add(imageLabel, gbc);

        // Panel for the country and province selectors
        JPanel countryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputConstrain = new GridBagConstraints();
        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.insets = new Insets(10, 10, 10, 10);
        inputConstrain.anchor = GridBagConstraints.WEST;

        // Country
        String[] countries = {"Spain", "Swiss"};
        countrySelector = new JComboBox<>(countries);
        countrySelector.setPreferredSize(new Dimension(200, 30));

        // Province
        String[] provincesSpain = {"Madrid", "Barcelona", "Valencia"};
        String[] provincesSwiss = {"Zurich", "Geneva", "Basel"};
        JComboBox<String> provinceSelector = new JComboBox<>(provincesSpain);

        // Panel para los selectores
        JPanel selectorPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 0;
        gbc.gridx = 0;
        selectorPanel.add(countrySelector, gbc);
        gbc.gridy = 1;
        selectorPanel.add(provinceSelector, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        add(selectorPanel, gbc);

        countrySelector.addActionListener(_ -> {
            String country = (String) countrySelector.getSelectedItem();
            provinceSelector.removeAllItems();
            if (Objects.equals(country, "Spain")) {
                Arrays.stream(provincesSpain).forEach(provinceSelector::addItem);
            } else {
                Arrays.stream(provincesSwiss).forEach(provinceSelector::addItem);
            }
        });

        // Buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> app.nextPanel());
        add(nextButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.previousPanel());
        add(backButton, gbc);
    }
}