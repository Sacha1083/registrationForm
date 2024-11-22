package gui;

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
    private final CountDownLatch latch;

    public LoginWindow(CountDownLatch latch) {
        this.latch = latch;
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(getLoginWindow());
    }

    public JPanel getLoginWindow() {
        String users = Paths.get(System.getProperty("user.dir"), "user.dad").toString();
        List<Usuario> userList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(users);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                try {
                    userList.add((Usuario) ois.readObject());
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: " + e.getMessage());
                    break;
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("User List:");
        userList.forEach(System.out::println);

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
            boolean found = false;
            for (Usuario user : userList) {
                if (user.getName().equals(userTextField.getText()) && user.getPassword().equals(new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Welcome " + user.getName(), "Login", JOptionPane.INFORMATION_MESSAGE);
                    found = true;
                    setVisible(false);
                    latch.countDown();
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(null, "Incorrect username or password", "Login", JOptionPane.ERROR_MESSAGE);
            }
        });

        content.add(login, gbc);

        return content;
    }
}