package gui;

import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.awt.GridBagConstraints.*;

public class PrincipalPanel extends JPanel {

    public PrincipalPanel(App app) {
        JPanel principalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new BorderLayout());

        add(Menu.getMenu(app), BorderLayout.NORTH);

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleForm = new JLabel(TextData.getText("title.instructions"));
        titleForm.setFont(TextFont.titleFont());
        principalPanel.add(titleForm, gbc);

        // Instructions
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = HORIZONTAL;
        gbc.anchor = CENTER;
        JLabel panelInformation = new JLabel(TextData.getText("instructions.html"));
        panelInformation.setFont(TextFont.textFont());
        panelInformation.setHorizontalAlignment(SwingConstants.CENTER);
        principalPanel.add(panelInformation, gbc);

        // Button configuration
        gbc.gridy = 2;
        gbc.gridwidth = REMAINDER;
        gbc.anchor = SOUTHEAST;
        gbc.fill = NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton(TextData.getText("button.next"));
        nextButton.addActionListener(e -> app.nextPanel());
        nextButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        nextButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                nextButton.doClick();
            }
        });
        principalPanel.add(nextButton, gbc);
        add(principalPanel, BorderLayout.CENTER);
    }
}