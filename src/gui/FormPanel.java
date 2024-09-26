package gui;

import javax.swing.*;
import java.awt.*;

public class FormPanel extends JPanel {
    public FormPanel(App app) {
        // Formulario para pedir nombre, correo electrónico y contraseña
        JPanel formPanel = new JPanel(new GridBagLayout());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagConstraints inputConstrain = new GridBagConstraints();

        gbc.gridy = 0; // Fila
        gbc.gridx = 0; // Columna
        JLabel titleForm = new JLabel(TextData.readTitleFormPanel());
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.insets = new Insets(0, 10, 0, 0);
        JLabel labelName = new JLabel("<html><h3>Name: </h3></html>");
        labelName.setFont(TextFont.textFormFont());
        formPanel.add(labelName, inputConstrain);

        inputConstrain.gridx = 1;
        JTextField name = new JTextField();
        name.setFont(TextFont.textFont());
        formPanel.add(name, inputConstrain);

        inputConstrain.gridy = 1;
        inputConstrain.gridx = 0;
        JLabel labelMail = new JLabel("<html><h3>E-Mail: </h3></html>");
        labelMail.setFont(TextFont.textFormFont());
        formPanel.add(labelMail, inputConstrain);

        inputConstrain.gridx = 1;
        JTextField eMail = new JTextField();
        eMail.setFont(TextFont.textFont());
        formPanel.add(eMail, inputConstrain);

        gbc.gridy = 1; // Fila
        add(formPanel, gbc);
    }
}