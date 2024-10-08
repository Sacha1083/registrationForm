package gui;

import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.GridBagConstraints.*;

public class DisplayData extends JPanel {
    private final JLabel nameLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JLabel countryLabel;
    private final JLabel provinceLabel;
    private final JCheckBox saveToFileCheckBox;

    public DisplayData(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleLabel = new JLabel("User Data");
        titleLabel.setFont(TextFont.titleFont());
        add(titleLabel, gbc);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 10, 10, 10);
        formGbc.anchor = WEST;

        // Labels and Fields
        nameLabel = new JLabel();
        emailLabel = new JLabel();
        passwordLabel = new JLabel();
        countryLabel = new JLabel();
        provinceLabel = new JLabel();
        nameLabel.setFont(TextFont.textFormFont());
        emailLabel.setFont(TextFont.textFormFont());
        passwordLabel.setFont(TextFont.textFormFont());
        countryLabel.setFont(TextFont.textFormFont());
        provinceLabel.setFont(TextFont.textFormFont());

        // Name
        formGbc.gridy = 0;
        formGbc.gridx = 0;
        formPanel.add(new JLabel("Name: "), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(nameLabel, formGbc);

        // Email
        formGbc.gridy = 1;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel("E-Mail: "), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(emailLabel, formGbc);

        // Password
        formGbc.gridy = 2;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel("Password: "), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(passwordLabel, formGbc);

        // Country
        formGbc.gridy = 3;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel("Country: "), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(countryLabel, formGbc);

        // Province
        formGbc.gridy = 4;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel("Province: "), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(provinceLabel, formGbc);

        // Save to file checkbox
        saveToFileCheckBox = new JCheckBox("Save to file");
        formGbc.gridy = 5;
        formGbc.gridx = 0;
        formGbc.gridwidth = 2;
        formGbc.anchor = CENTER;
        formPanel.add(saveToFileCheckBox, formGbc);

        // Add form panel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.fill = BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = NORTH;
        add(formPanel, gbc);

        // Botones
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = SOUTHWEST;
        gbc.fill = NONE;
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.previousPanel());
        add(backButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = SOUTHEAST;
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            if (saveToFileCheckBox.isSelected()) {
                saveDataToFile();
            }
            System.exit(0);
        });
        add(exitButton, gbc);

        updateData(app);
    }

    public void updateData(App app) {
        nameLabel.setText(app.getUserName());
        emailLabel.setText(app.geteMail());
        passwordLabel.setText(app.getPassword());
        countryLabel.setText(app.getCountry());
        provinceLabel.setText(app.getProvince());
    }

    private void saveDataToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("user_data.txt"));
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Name: " + nameLabel.getText() + "\n");
                writer.write("E-Mail: " + emailLabel.getText() + "\n");
                writer.write("Password: " + passwordLabel.getText() + "\n");
                writer.write("Country: " + countryLabel.getText() + "\n");
                writer.write("Province: " + provinceLabel.getText() + "\n");
                JOptionPane.showMessageDialog(this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}