package gui;

import javax.swing.*;
import java.awt.*;

public class PrincipalPanel extends JPanel {

    public PrincipalPanel(App app) {
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel titleForm = new JLabel(TextData.readTitlePrincipalPanel());
        titleForm.setFont(TextFont.titleFont());
        add(titleForm, gbc);

        // Instructions
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;  // horizontal
        gbc.weighty = 1.0;  // vertical
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Only expand horizontally
        gbc.anchor = GridBagConstraints.CENTER; // center the content
        JLabel panelInformation = new JLabel(TextData.readInstructions());
        panelInformation.setFont(TextFont.textFont());
        panelInformation.setHorizontalAlignment(SwingConstants.CENTER);
        add(panelInformation, gbc);

        // Button configuration
        gbc.gridy = 2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> app.nextPanel());
        add(nextButton, gbc);
    }
}