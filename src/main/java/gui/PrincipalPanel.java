package gui;

import util.Menu;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.*;

/**
 * PrincipalPanel class
 * <p>
 *     Panel for the main menu of the application. <br>
 *     It displays the application title and instructions for the user. <br>
 *     The user can proceed to the next panel.
 * </p>
 * @see View - The main application class
 * @see Menu - Panel for the application menu
 * @see TextData - Class for managing text data
 * @see TextFont - Class for managing text fonts
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class PrincipalPanel extends JPanel {

    /**
     * PrincipalPanel constructor
     * <p>
     *     Initializes the main menu panel.
     * </p>
     * @param view The main application class
     * @see View - The main application class
     * @since JDK21.0.5
     */
    public PrincipalPanel(View view) {
        JPanel principalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new BorderLayout());
        setName("PrincipalPanel");

        add(util.Menu.getMenu(view, this), BorderLayout.NORTH);

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
        nextButton.addActionListener(e -> view.nextPanel());
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