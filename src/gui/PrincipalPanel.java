package gui;

import javax.swing.*;
import java.awt.*;

public class PrincipalPanel extends JPanel {

    public PrincipalPanel(App app) {
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        gbc.gridx = 0; // Columna
        gbc.gridy = 0; // Fila
        gbc.weightx = 1.0; // Expandir horizontalmente
        gbc.weighty = 1.0; // Expandir verticalmente
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel panelInformation = new JLabel(TextData.readInstructions());
        panelInformation.setFont(new Font("Arial", Font.BOLD, 12));
        add(panelInformation, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 1; // Fila
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> app.nextPanel());

        add(nextButton, gbc);
    }
}
