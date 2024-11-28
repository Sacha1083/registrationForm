package gui;

import gui.listeners.LoginListener;
import org.w3c.dom.Text;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class RegisterPanel extends JPanel {
    private final JTextField name;
    private final JTextField eMail;
    private final JPasswordField password;
    public RegisterPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleForm = new JLabel(TextData.getText("titleFormPanel"));
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints inputConstrain = new GridBagConstraints();
        inputConstrain.gridy = 0;
        inputConstrain.gridx = 0;
        inputConstrain.insets = new Insets(10, 10, 10, 10);
        inputConstrain.anchor = WEST;
        JLabel labelName = new JLabel(TextData.getText("label.user"));
        labelName.setFont(TextFont.textFormFont());
        formPanel.add(labelName, inputConstrain);

        // Name
        inputConstrain.gridx = 1;
        inputConstrain.fill = HORIZONTAL;
        name = new JTextField();
        name.setFont(TextFont.textFont());
        name.setPreferredSize(new Dimension(200, 30));
        formPanel.add(name, inputConstrain);

        inputConstrain.gridy = 1;
        inputConstrain.gridx = 0;
        inputConstrain.fill = NONE;
        JLabel labelMail = new JLabel(TextData.getText("labelEmail"));
        labelMail.setToolTipText(TextData.getText("toltipEmail"));
        labelMail.setToolTipText(labelMail.getToolTipText());
        labelMail.setFont(TextFont.textFormFont());
        formPanel.add(labelMail, inputConstrain);

        // E-Mail
        inputConstrain.gridx = 1;
        inputConstrain.fill = HORIZONTAL;
        eMail = new JTextField();
        eMail.setToolTipText(TextData.getText("toltipEmail"));
        eMail.setToolTipText(eMail.getToolTipText());
        eMail.setFont(TextFont.textFont());
        eMail.setPreferredSize(new Dimension(200, 30));
        formPanel.add(eMail, inputConstrain);

        // Password Label and Field
        inputConstrain.gridy = 2;
        inputConstrain.gridx = 0;
        inputConstrain.fill = NONE;
        JLabel labelPassword = new JLabel("<html><h3>Password: </h3></html>");
        labelPassword.setToolTipText(TextData.getText("toltip.password"));
        labelPassword.setToolTipText(labelPassword.getToolTipText());
        labelPassword.setFont(TextFont.textFormFont());
        formPanel.add(labelPassword, inputConstrain);

        inputConstrain.gridx = 1;
        inputConstrain.fill = HORIZONTAL;
        password = new JPasswordField();
        password.setToolTipText(TextData.getText("toltip.password"));
        password.setToolTipText(password.getToolTipText());
        password.setFont(TextFont.textFont());
        password.setPreferredSize(new Dimension(200, 30));
        formPanel.add(password, inputConstrain);

        // Clear Button
        inputConstrain.gridy = 3;
        inputConstrain.gridx = 1;
        inputConstrain.gridwidth = REMAINDER;
        inputConstrain.fill = NONE;
        JButton clearButton = new JButton(TextData.getText("buttonClear"));
        clearButton.addActionListener(e -> clearFields());
        formPanel.add(clearButton, inputConstrain);

        // Add formPanel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.fill = BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = NORTH;
        add(formPanel, gbc);

        // Buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHEAST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton(TextData.getText("button.next"));
        nextButton.addActionListener(new LoginListener(app, "login", name, eMail, password));
        nextButton.getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        nextButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                nextButton.doClick();
            }
        });
        add(nextButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHWEST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton backButton = new JButton(TextData.getText("buttonBack"));
        backButton.addActionListener(new LoginListener(app, "back", name, eMail, password));
        add(backButton, gbc);
    }

    public String getUserName() {
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