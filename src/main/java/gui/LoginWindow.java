package gui;

import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.*;

public class LoginWindow extends JWindow {
    public static JPanel getLoginWindow() {
        String users = Paths.get(System.getProperty("user.dir"), "user.dad").toString();
        List<Usuario> userList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(users);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    userList.add((Usuario) ois.readObject());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                } catch (IOException e) {
                    if (e.getMessage().contains("EOFException")) {
                        break;
                    } else {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JPanel content = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // User Label
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.fill = NONE;
        JLabel labelUser = new JLabel("<html><h3>User: </h3></html>");
        labelUser.setFont(TextFont.textFormFont());
        content.add(labelUser, gbc);

        // User TextField
        gbc.gridx = 1;
        gbc.fill = HORIZONTAL;
        JTextField userTextField = new JTextField(20);
        content.add(userTextField, gbc);

        // Password Label
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = NONE;
        JLabel labelPassword = new JLabel("<html><h3>Password: </h3></html>");
        labelPassword.setToolTipText("La contraseña debe tener entre 8 y 16 caracteres, al menos un dígito, una minúscula, una mayúscula y un carácter no alfanumérico.");
        labelPassword.setFont(TextFont.textFormFont());
        content.add(labelPassword, gbc);

        // Password TextField
        gbc.gridx = 1;
        gbc.fill = HORIZONTAL;
        JPasswordField passwordField = new JPasswordField(20);
        content.add(passwordField, gbc);

        // Exit Button
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = NONE;
        gbc.gridwidth = 1;
        gbc.anchor = CENTER;
        JButton exit = new JButton("Exit");
        exit.addActionListener(l -> System.exit(0));
        content.add(exit, gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.anchor = CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        JButton login = new JButton("Login");
        login.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "clickButton");
        login.getActionMap().put("clickButton", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                login.doClick();
            }
        });

        login.addActionListener(e -> {
            // Add login action here
        });

        content.add(login, gbc);

        return content;
    }
}