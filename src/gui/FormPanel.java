package gui;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    public FormPanel(App app) {
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

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputConstrain = new GridBagConstraints();
        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.insets = new Insets(10, 10, 10, 10);
        inputConstrain.anchor = GridBagConstraints.WEST;
        JLabel labelName = new JLabel("<html><h3>Name: </h3></html>");
        labelName.setFont(TextFont.textFormFont());
        formPanel.add(labelName, inputConstrain);

        inputConstrain.gridx = 1;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        JTextField name = new JTextField();
        name.setFont(TextFont.textFont());
        name.setPreferredSize(new Dimension(200, 30));
        formPanel.add(name, inputConstrain);

        inputConstrain.gridy = 1;
        inputConstrain.gridx = 0;
        inputConstrain.fill = GridBagConstraints.NONE;
        JLabel labelMail = new JLabel("<html><h3>E-Mail: </h3></html>");
        labelMail.setFont(TextFont.textFormFont());
        formPanel.add(labelMail, inputConstrain);

        inputConstrain.gridx = 1;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        JTextField eMail = new JTextField();
        eMail.setFont(TextFont.textFont());
        eMail.setPreferredSize(new Dimension(200, 30));
        formPanel.add(eMail, inputConstrain);

        inputConstrain.gridy = 2;
        inputConstrain.gridx = 0;
        inputConstrain.fill = GridBagConstraints.NONE;
        JLabel labelPassword = new JLabel("<html><h3>Password: </h3></html>");
        labelPassword.setFont(TextFont.textFormFont());
        formPanel.add(labelPassword, inputConstrain);

        inputConstrain.gridx = 1;
        inputConstrain.fill = GridBagConstraints.HORIZONTAL;
        JPasswordField password = new JPasswordField();
        password.setFont(TextFont.textFont());
        password.setPreferredSize(new Dimension(200, 30));
        formPanel.add(password, inputConstrain);

        // añadir un boton de send al formulario
        inputConstrain.gridy = 3;
        inputConstrain.gridx = 1;
        inputConstrain.gridwidth = GridBagConstraints.REMAINDER;
        inputConstrain.fill = GridBagConstraints.NONE;
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String nameText = name.getText();
            String eMailText = eMail.getText();
            String passwordText = new String(password.getPassword());
            System.out.println("Name: " + nameText);
            System.out.println("E-Mail: " + eMailText);
            System.out.println("Password: " + passwordText);
        });
        formPanel.add(sendButton, inputConstrain);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Para que el formulario ocupe todo el ancho
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        add(formPanel, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Botón en la parte inferior derecha
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Para alinear el botón a la derecha
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> app.nextPanel());
        add(nextButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Botón en la parte inferior izquierda
        gbc.anchor = GridBagConstraints.SOUTHWEST; // Para alinear el botón a la izquierda
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> app.previousPanel());
        add(backButton, gbc);
    }
}