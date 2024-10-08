package gui;

import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.awt.GridBagConstraints.*;

public class CountryPanel extends JPanel {
    private static JComboBox<String> countrySelector;
    private static JComboBox<String> provinceSelector;

    public CountryPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Read country data from file
        List<String> countryData = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/files/country.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                countryData.add(line);
            }
        } catch (Exception e) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Error reading country data file", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error reading country data file");
            System.exit(0);
        }

        // Image Section
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.fill = BOTH;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.LEFT);
        imageLabel.setVerticalAlignment(JLabel.TOP);

        ImageIcon imageIcon = null;
        try {
            imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("img/country.jpg")));
        } catch (NullPointerException e) {
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error loading image");
            System.exit(0);
        }
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
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        gbc.weighty = 0.0;
        gbc.fill = HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.anchor = CENTER;
        JLabel titleForm = new JLabel("Select a country and province");
        titleForm.setHorizontalAlignment(SwingConstants.CENTER);
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Selector Section
        JPanel selectorPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputConstrain = new GridBagConstraints();
        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.fill = HORIZONTAL;
        inputConstrain.insets = new Insets(10, 10, 10, 10);
        inputConstrain.anchor = CENTER;

        // Country
        String[] countries = countryData.getFirst().split(";");
        countrySelector = new JComboBox<>(countries);
        countrySelector.setPreferredSize(new Dimension(200, 30));

        // Province
        String [] provinces = countryData.getLast().split(";");
        provinceSelector = new JComboBox<>(provinces[0].split(","));
        provinceSelector.setPreferredSize(new Dimension(200, 30));

        // Add listener to country selector
        countrySelector.addActionListener(e -> {
            String country = (String) countrySelector.getSelectedItem();
            provinceSelector.removeAllItems();
            int countryIndex = Arrays.asList(countries).indexOf(country);
            if (countryIndex != -1) {
                Arrays.stream(provinces[countryIndex].split(",")).forEach(provinceSelector::addItem);
            }
        });

        // Add country and province selectors to the panel
        inputConstrain.gridy = 0;
        selectorPanel.add(countrySelector, inputConstrain);
        inputConstrain.gridy = 1;
        selectorPanel.add(provinceSelector, inputConstrain);

        // Add the selector panel to the main panel
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        gbc.anchor = NORTH;
        gbc.fill = HORIZONTAL;
        add(selectorPanel, gbc);

        // Buttons Section
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHEAST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> app.nextPanel());
        add(nextButton, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHWEST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.previousPanel());
        add(backButton, gbc);
    }

    public String getCountry() {
        return (String) countrySelector.getSelectedItem();
    }

    public String getProvince() {
        return (String) provinceSelector.getSelectedItem();
    }
}