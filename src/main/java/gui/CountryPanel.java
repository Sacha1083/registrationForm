package gui;

import util.TextData;
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

/**
 * <h1>CountryPanel class</h1>
 * <p>
 * Panel for selecting the country and province of the user. <br>
 * It displays a list of countries and provinces for the user to choose from. <br>
 * The user can proceed to the next panel.
 * </p>
 * @see App - The main application class
 * @see TextData - Class for managing text data
 * @see TextFont - Class for managing text fonts
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class CountryPanel extends JPanel {
    private static JComboBox<CountryItem> countrySelector;
    private static JComboBox<String> provinceSelector;

    /**
     * <h1>CountryPanel constructor</h1>
     * <p>
     * Initializes the country selection panel. <br>
     * It displays a list of countries and provinces for the user to choose from. <br>
     * The user can proceed to the next panel.
     * </p>
     * @param app The main application class
     * @see App - The main application class
     * @since JDK21.0.5
     */
    public CountryPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setName("CountryPanel");

        // Read country data from file
        List<String> countryData = new ArrayList<>();
        try (InputStream inputStream = getClass().getResourceAsStream("/files/country.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                countryData.add(line);
            }
        } catch (Exception e) {
            String msg = TextData.getText("errReadingFile");
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, msg, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(msg);
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
            String msg = TextData.getText("errLoadImage");
            JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, msg, "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(msg);
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
        JLabel titleForm = new JLabel(TextData.getText("titleCountryPanel"));
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
        String[] countries = countryData.get(0).split(";");
        String[] imagePaths = countryData.get(2).split(";");
        CountryItem[] countryItems = new CountryItem[countries.length];
        for (int i = 0; i < countries.length; i++) {
            countryItems[i] = new CountryItem(countries[i], imagePaths[i]);
        }
        countrySelector = new JComboBox<>(countryItems);
        countrySelector.setPreferredSize(new Dimension(200, 80));
        countrySelector.setRenderer(new CountryItemRenderer());

        // Province
        String[] provinces = countryData.get(1).split(";");
        provinceSelector = new JComboBox<>(provinces[0].split(","));
        provinceSelector.setPreferredSize(new Dimension(200, 80));

        // Add listener to country selector
        countrySelector.addActionListener(e -> {
            CountryItem selectedCountry = (CountryItem) countrySelector.getSelectedItem();
            provinceSelector.removeAllItems();
            int countryIndex = Arrays.asList(countries).indexOf(Objects.requireNonNull(selectedCountry).getName());
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
        JButton nextButton = new JButton(TextData.getText("button.next"));
        nextButton.addActionListener(e -> app.nextPanel());
        nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        nextButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                nextButton.doClick();
            }
        });
        add(nextButton, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHWEST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton backButton = new JButton(TextData.getText("buttonBack"));
        backButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, TextData.getText("userDataBackMessage"), TextData.getText("userDataBackTitle"), JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                app.previousPanel();
            }
        });
        add(backButton, gbc);
    }

    /**
     * <h1>Get country</h1>
     * <p>
     * Returns the selected country.
     * </p>
     * @return The selected country
     * @since JDK21.0.5
     */
    public String getCountry() {
        return ((CountryItem) Objects.requireNonNull(countrySelector.getSelectedItem())).getName();
    }

    /**
     * <h1>Get province</h1>
     * <p>
     * Returns the selected province.
     * </p>
     * @return The selected province
     * @since JDK21.0.5
     */
    public String getProvince() {
        return (String) provinceSelector.getSelectedItem();
    }

    private static class CountryItem {
        private final String name;
        private final String imagePath;

        public CountryItem(String name, String imagePath) {
            this.name = name;
            this.imagePath = imagePath;
        }

        public String getName() {
            return name;
        }

        public String getImagePath() {
            return imagePath;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * <h1>CountryItemRenderer class</h1>
     * <p>
     * Custom renderer for the country selector.
     * </p>
     * @since JDK21.0.5
     */
    private static class CountryItemRenderer extends JLabel implements ListCellRenderer<CountryItem> {
        @Override
        public Component getListCellRendererComponent(JList<? extends CountryItem> list, CountryItem value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(value.getName());
            ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(value.getImagePath())));
            Image image = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setOpaque(true);
            return this;
        }
    }
}