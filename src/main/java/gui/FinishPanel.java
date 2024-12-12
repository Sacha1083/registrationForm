package gui;

import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

/**
 * <h1>FinishPanel class</h1>
 * <p>
 *     Panel for the final screen of the application. <br>
 *     It displays a message to the user and allows them to exit the application.
 * </p>
 * @see App - The main application class
 * @see TextData - Class for managing text data
 * @see TextFont - Class for managing text fonts
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class FinishPanel extends JPanel {

    /**
     * <h1>FinishPanel constructor</h1>
     * <p>
     *     Initializes the final screen panel.
     * </p>
     * @param app The main application class
     * @see App - The main application class
     * @since JDK21.0.5
     */
    public FinishPanel(App app) {
        setLayout(new GridBagLayout());
        setName("FinishPanel");
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = NORTH;
        JLabel titleLabel = new JLabel(TextData.getText("finishTitle"));
        titleLabel.setFont(TextFont.titleFont());
        add(titleLabel, gbc);

        // Message
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = CENTER;
        JLabel messageLabel = new JLabel(TextData.getText("finishMessage"));
        messageLabel.setFont(TextFont.textFormFont());
        add(messageLabel, gbc);

        // Buttons Panel
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = SOUTHWEST;
        JButton previousButton = new JButton(TextData.getText("buttonBack"));
        previousButton.addActionListener(e -> app.previousPanel());
        add(previousButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = SOUTHEAST;
        JButton finishButton = new JButton(TextData.getText("btnFinish"));
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