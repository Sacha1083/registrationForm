package gui;

import util.TextFont;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

public class FinishPanel extends JPanel {
    public FinishPanel(App app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleLabel = new JLabel("Finish");
        titleLabel.setFont(TextFont.titleFont());
        add(titleLabel, gbc);

        // Message
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = CENTER;
        JLabel messageLabel = new JLabel("Thank you for using the app!");
        messageLabel.setFont(TextFont.textFormFont());
        add(messageLabel, gbc);

        // Buttons Panel
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = SOUTHWEST;
        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(e -> app.previousPanel());
        add(previousButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = SOUTHEAST;
        JButton finishButton = new JButton("Finish");
        finishButton.addActionListener(e -> System.exit(0));
        finishButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        finishButton.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                finishButton.doClick();
            }
        });
        add(finishButton, gbc);
    }
}