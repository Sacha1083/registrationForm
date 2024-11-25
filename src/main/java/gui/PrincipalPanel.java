package gui;

import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.awt.GridBagConstraints.*;

public class PrincipalPanel extends JPanel {

    public PrincipalPanel(App app) {
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleForm = new JLabel(TextData.readTitlePrincipalPanel());
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Instructions
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = HORIZONTAL;
        gbc.anchor = CENTER;
        JLabel panelInformation = new JLabel(TextData.readInstructions());
        panelInformation.setFont(TextFont.textFont());
        panelInformation.setHorizontalAlignment(SwingConstants.CENTER);
        add(panelInformation, gbc);

        Locale locale = new Locale("es");
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        // Button configuration
        gbc.gridy = 2;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHEAST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton(bundle.getString("button.submit"));
        nextButton.addActionListener(e -> app.nextPanel());
        nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        nextButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                nextButton.doClick();
            }
        });
        add(nextButton, gbc);
    }
}