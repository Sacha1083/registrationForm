package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final JTextField name;
    private final JTextField eMail;
    private final JPasswordField password;
    public LoginPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Para que el título ocupe todo el ancho
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel titleForm = new JLabel(TextData.readTitleFormPanel());
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputConstrain = new GridBagConstraints();
        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.insets = new Insets(10, 10, 10, 10);
        inputConstrain.anchor = GridBagConstraints.WEST;
        JLabel labelName = new JLabel("<html><h3>Name: </h3></html>");
        labelName.setFont(TextFont.textFormFont());
        formPanel.add(labelName, inputConstrain);

        // Name
        inputConstrain.gridx = 1;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        name = new JTextField();
        name.setFont(TextFont.textFont());
        name.setPreferredSize(new Dimension(200, 30));
        formPanel.add(name, inputConstrain);

        inputConstrain.gridy = 1;
        inputConstrain.gridx = 0;
        inputConstrain.fill = GridBagConstraints.NONE;
        JLabel labelMail = new JLabel("<html><h3>E-Mail: </h3></html>");
        labelMail.setFont(TextFont.textFormFont());
        formPanel.add(labelMail, inputConstrain);

        // E-Mail
        inputConstrain.gridx = 1;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        eMail = new JTextField();
        eMail.setFont(TextFont.textFont());
        eMail.setPreferredSize(new Dimension(200, 30));
        formPanel.add(eMail, inputConstrain);

        inputConstrain.gridy = 2;
        inputConstrain.gridx = 0;
        inputConstrain.fill = GridBagConstraints.NONE;
        JLabel labelPassword = new JLabel("<html><h3>Password: </h3></html>");
        labelPassword.setFont(TextFont.textFormFont());
        formPanel.add(labelPassword, inputConstrain);

        // Password
        inputConstrain.gridx = 1;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        password = new JPasswordField();
        password.setFont(TextFont.textFont());
        password.setPreferredSize(new Dimension(200, 30));
        formPanel.add(password, inputConstrain);

        // Add formPanel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        add(formPanel, gbc);

        // Buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            // Get data
            String nameText = name.getText();
            String eMailText = eMail.getText();
            String passwordText = new String(password.getPassword());

            // Validations
            if (nameText.isEmpty() || eMailText.isEmpty() || passwordText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!eMailText.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
                JOptionPane.showMessageDialog(this, "Invalid e-mail format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!passwordText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,16}$")) {
                JOptionPane.showMessageDialog(this, "Invalid password format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Print data and go to next panel
            System.out.println("Name: " + nameText);
            System.out.println("E-Mail: " + eMailText);
            System.out.println("Password: " + passwordText);
            app.nextPanel();
        });
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

    public String getName() {
        return name.getText();
    }

    public String geteMail() {
        return eMail.getText();
    }

    public String getPassword() {
        return new String(password.getPassword());
    }

    public void clearFields() {
        name.setText("");
        eMail.setText("");
        password.setText("");
    }
}