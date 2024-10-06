package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DisplayData extends JPanel {
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel countryLabel;
    private JLabel provinceLabel;
    private JCheckBox saveToFileCheckBox;

    public DisplayData(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("User Data");
        titleLabel.setFont(TextFont.titleFont());
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

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
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Name: "), gbc);
        gbc.gridx = 1;
        add(nameLabel, gbc);

        // Email
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("E-Mail: "), gbc);
        gbc.gridx = 1;
        add(emailLabel, gbc);

        // Password
        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Password: "), gbc);
        gbc.gridx = 1;
        add(passwordLabel, gbc);

        // Country
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Country: "), gbc);
        gbc.gridx = 1;
        add(countryLabel, gbc);

        // Province
        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Province: "), gbc);
        gbc.gridx = 1;
        add(provinceLabel, gbc);

        // Save to file checkbox
        saveToFileCheckBox = new JCheckBox("Save to file");
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(saveToFileCheckBox, gbc);

        // Botones
        gbc.gridwidth = 1;
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;  // Evitar que el botón se expanda
        gbc.weightx = 0;
        gbc.weighty = 0;
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));  // Tamaño fijo para evitar expansión
        backButton.addActionListener(e -> app.previousPanel());
        add(backButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(100, 30));  // Tamaño fijo para evitar expansión
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