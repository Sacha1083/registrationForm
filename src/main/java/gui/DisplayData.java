package gui;

import util.Menu;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.GridBagConstraints.*;

/**
 * DisplayData class
 * <p>
 * Panel for displaying the user data. <br>
 * It shows the user's name, email, password, country, and province. <br>
 * The user can save the data to a file.
 * </p>
 * @see View - The main application class
 * @see TextData - Class for managing text data
 * @see TextFont - Class for managing text fonts
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class DisplayData extends JPanel {
    private final JLabel nameLabel;
    private final JLabel emailLabel;
    private final JLabel passwordLabel;
    private final JLabel countryLabel;
    private final JLabel provinceLabel;
    private final JButton saveToFile;

    /**
     * DisplayData constructor
     * <p>
     * Initializes the user data display panel. <br>
     * It shows the user's name, email, password, country, and province. <br>
     * The user can save the data to a file.
     * </p>
     * @param view The main application class
     * @see View - The main application class
     * @since JDK21.0.5
     */
    public DisplayData(View view) {
        setLayout(new BorderLayout());
        setName("DisplayData");
        JPanel panelData = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleLabel = new JLabel(TextData.getText("userDataTitle"));
        titleLabel.setFont(TextFont.titleFont());
        panelData.add(titleLabel, gbc);

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
        formPanel.add(new JLabel(TextData.getText("userDataName")), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(nameLabel, formGbc);

        // Email
        formGbc.gridy = 1;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel(TextData.getText("userDataEmail")), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(emailLabel, formGbc);

        // Password
        formGbc.gridy = 2;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel(TextData.getText("userDataPassword")), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(passwordLabel, formGbc);

        // Country
        formGbc.gridy = 3;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel(TextData.getText("userDataCountry")), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(countryLabel, formGbc);

        // Province
        formGbc.gridy = 4;
        formGbc.gridx = 0;
        formGbc.fill = NONE;
        formPanel.add(new JLabel(TextData.getText("userDataProvince")), formGbc);
        formGbc.gridx = 1;
        formGbc.fill = HORIZONTAL;
        formPanel.add(provinceLabel, formGbc);

        // Save to file
        saveToFile = new JButton(TextData.getText("userDataExport"));
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
        panelData.add(formPanel, gbc);

        // Buttons
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = SOUTHWEST;
        gbc.fill = NONE;
        JButton backButton = new JButton(TextData.getText("buttonBack"));
        backButton.addActionListener(e -> view.previousPanel());
        panelData.add(backButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = SOUTHEAST;
        JButton nextButton = new JButton(TextData.getText("button.next"));
        nextButton.addActionListener(e -> {
            view.nextPanel();
        });
        nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        nextButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                nextButton.doClick();
            }
        });
        panelData.add(nextButton, gbc);

        add(Menu.getMenu(view, this), BorderLayout.NORTH);
        add(panelData, BorderLayout.CENTER);

        updateData(view);
    }

    /**
     * Update data
     * <p>
     * Updates the user data in the app class.
     * </p>
     * @param view The main application class
     * @see View - The main application class
     * @since JDK21.0.5
     */
    public void updateData(View view) {
        nameLabel.setText(view.getUserName());
        emailLabel.setText(view.geteMail());
        passwordLabel.setText(view.getPassword());
        countryLabel.setText(view.getCountry());
        provinceLabel.setText(view.getProvince());
    }

    /**
     * Save data to file
     * <p>
     * Saves the user data to a file.
     * </p>
     * @see JFileChooser - A JFileChooser is a quick and easy way to prompt the user to choose a file or a file saving location
     * @see File - An abstract representation of file and directory pathnames
     * @see FileWriter - Convenience class for writing character files
     * @see IOException - Signals that an I/O exception of some sort has occurred
     * @since JDK21.0.5
     */
    private void saveDataToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile(), "user_data.txt");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(TextData.getText("userDataName") + nameLabel.getText() + "\n");
                writer.write(TextData.getText("userDataEmail") + emailLabel.getText() + "\n");
                writer.write(TextData.getText("userDataPassword") + passwordLabel.getText() + "\n");
                writer.write(TextData.getText("userDataCountry") + countryLabel.getText() + "\n");
                writer.write(TextData.getText("userDataProvince") + provinceLabel.getText() + "\n");
                JOptionPane.showMessageDialog(this, TextData.getText("userDataFileSave"), "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, TextData.getText("userDataFileSaveError") + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}