package gui;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    public FormPanel(App app) {
        // formulario para pedir nombre, correo electrónico y contraseña
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel("Name:");
        add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Submit");
        add(submitButton, gbc);

        gbc.gridy = 4;
        JButton backButton = new JButton("Back");
        add(backButton, gbc);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            System.out.println("Name: " + name);
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
        });

        backButton.addActionListener(e -> app.previousPanel());
    }
}