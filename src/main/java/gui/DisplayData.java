package gui;

import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
    private final JButton saveToFile;

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

        // Save to file
        saveToFile = new JButton("Export");
        formGbc.gridy = 5;
        formGbc.gridx = 0;
        formGbc.gridwidth = 2;
        formGbc.anchor = CENTER;
        formPanel.add(saveToFile, formGbc);

        saveToFile.addActionListener(e -> saveDataToFile());

        // Ctrl + E to export
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
        InputMap inputMap = saveToFile.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = saveToFile.getActionMap();

        // Asociar la acción al InputMap y ActionMap
        inputMap.put(keyStroke, "saveAction");
        actionMap.put("saveAction", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDataToFile();
            }
        });


        // Add form panel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.fill = BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = NORTH;
        add(formPanel, gbc);

        // Buttons
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
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            app.nextPanel();
        });
        nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        nextButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                nextButton.doClick();
            }
        });
        add(nextButton, gbc);

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
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile(), "user_data.txt");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Name: " + nameLabel.getText() + "\n");
                writer.write("E-Mail: " + emailLabel.getText() + "\n");
                writer.write("Password: " + passwordLabel.getText() + "\n");
                writer.write("Country: " + countryLabel.getText() + "\n");
                writer.write("Province: " + provinceLabel.getText() + "\n");
                JOptionPane.showMessageDialog(this, "File saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file.\nMessage: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}