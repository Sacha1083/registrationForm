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

        // Image Section
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.LEFT);
        imageLabel.setVerticalAlignment(JLabel.TOP);

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/img/country.jpg")));
        Image originalImage = imageIcon.getImage();

        // Listener for resizing image
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                Dimension size = getSize();
                int newHeight = size.height - 100;
                int newWidth = (int) (newHeight * 0.75);

                Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            }
        });

        add(imageLabel, gbc);

        // Title Section
        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;  // Title spans across the next 2 columns
        gbc.weightx = 0.7;  // Adjust weight to balance space between image and form
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = GridBagConstraints.CENTER; // Center the title horizontally
        JLabel titleForm = new JLabel("Select a country and province");
        titleForm.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Selector Section
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
        inputConstrain.gridy = 0;
        selectorPanel.add(countrySelector, inputConstrain);
        inputConstrain.gridy = 1;
        selectorPanel.add(provinceSelector, inputConstrain);

        // Add the selector panel to the main panel
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Spanning across 2 columns
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(selectorPanel, gbc);

        // Add listener to country selector
        countrySelector.addActionListener(e -> {
            String country = (String) countrySelector.getSelectedItem();
            provinceSelector.removeAllItems();
            if (Objects.equals(country, "Spain")) {
                Arrays.stream(provincesSpain).forEach(provinceSelector::addItem);
            } else {
                Arrays.stream(provincesSwiss).forEach(provinceSelector::addItem);
            }
        });

        // Buttons Section
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