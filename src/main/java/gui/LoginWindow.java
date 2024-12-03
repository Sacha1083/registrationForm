package gui;

import gui.model.entity.Usuario;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static java.awt.GridBagConstraints.*;

public class LoginWindow extends JFrame {
    private UsuarioController usuarioController;

    public LoginWindow(CountDownLatch latch) {
        configLoginPanel(latch);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // User Label
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = NONE;
        JLabel labelUser = new JLabel(TextData.getText("label.user"));
        labelUser.setFont(TextFont.textFormFont());
        add(labelUser, gbc);

        // User TextField
        gbc.gridx = 1;
        gbc.fill = HORIZONTAL;
        JTextField userTextField = new JTextField(20);
        add(userTextField, gbc);

        // Password Label
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = NONE;
        JLabel labelPassword = new JLabel(TextData.getText("label.password"));
        labelPassword.setToolTipText(TextData.getText("toltip.password"));
        labelPassword.setFont(TextFont.textFormFont());
        add(labelPassword, gbc);

        // Password TextField
        gbc.gridx = 1;
        gbc.fill = HORIZONTAL;
        JPasswordField passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // Exit Button
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = NONE;
        gbc.gridwidth = 1;
        gbc.anchor = CENTER;
        JButton exit = new JButton(TextData.getText("buttonExit"));
        exit.addActionListener(l -> System.exit(0));
        add(exit, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.anchor = CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton login = new JButton(TextData.getText("buttonLogin"));
        login.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        login.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                login.doClick();
            }
        });

        login.addActionListener(e -> {
            Usuario loginUsuario = usuarioController.authenticateUser(userTextField.getText(), new String(passwordField.getPassword()));

            if (loginUsuario != null) {
                JOptionPane.showMessageDialog(null, TextData.getText("welcome") + loginUsuario.getName(), "Login", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                latch.countDown();
            } else {
                JOptionPane.showMessageDialog(null, TextData.getText("incorrectLogin"), "Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(login, gbc);
    }

    private void configLoginPanel(CountDownLatch latch) {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        usuarioController = new UsuarioController();
    }
}