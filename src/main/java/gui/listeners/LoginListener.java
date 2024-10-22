package gui.listeners;

import gui.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginListener extends Component implements ActionListener {
    private final App app;
    private final String option;
    private final JTextField name;
    private final JTextField eMail;
    private final JPasswordField password;

    public LoginListener(App app, String option, JTextField name, JTextField eMail, JPasswordField password) {
        this.app = app;
        this.option = option;
        this.name = name;
        this.eMail = eMail;
        this.password = password;
    }

    // Method to perform the action
    @Override
    public void actionPerformed(ActionEvent e) {
        if (option.equals("login")) {
            login();
        } else if (option.equals("back")) {
            app.previousPanel();
        } else {
            System.out.println("❗ Invalid option ❗");
        }
    }

    // Method to validate the login
    private void login() {
        // Get data
        String nameText = name.getText();
        String eMailText = eMail.getText();
        String passwordText = new String(password.getPassword());

        // Validations
        if (nameText.isEmpty() || eMailText.isEmpty() || passwordText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!eMailText.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            JOptionPane.showMessageDialog(this, "Invalid e-mail format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!passwordText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,16}$")) {
            JOptionPane.showMessageDialog(this, "Invalid password format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Next panel if everything is correct
        app.nextPanel();
    }
}