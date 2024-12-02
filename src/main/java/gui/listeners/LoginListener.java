package gui.listeners;

import gui.view.App;
import gui.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginListener extends Component implements ActionListener {
    private final App app;
    private final String option;
    private final JTextField name;
    private final JTextField eMail;
    private final JPasswordField password;
    private List<Usuario> userList;

    public LoginListener(App app, String option, JTextField name, JTextField eMail, JPasswordField password) {
        System.out.println("LoginListener created");
        this.app = app;
        this.option = option;
        this.name = name;
        this.eMail = eMail;
        this.password = password;
        userList = new ArrayList<>();
    }

    // Method to perform the action
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action performed");
        if (option.equals("login")) {
            System.out.println("Login");
            login();
        } else if (option.equals("back")) {
            System.out.println("Back");
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
            System.out.println("All fields are required");
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!eMailText.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.println("Invalid e-mail format");
            JOptionPane.showMessageDialog(this, "Invalid e-mail format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!passwordText.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,16}$")) {
            System.out.println("Invalid password format");
            JOptionPane.showMessageDialog(this, "Invalid password format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        readUsersFile();

        Usuario nuevoUsuario = new Usuario(nameText, eMailText, passwordText);
        if (userList.contains(nuevoUsuario)) {
            System.out.println("User already exists");
            JOptionPane.showMessageDialog(this, "User already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Paths.get(System.getProperty("user.dir"), "user.dad").toString(), false));
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(nuevoUsuario);
                for (Usuario user : userList) {
                    oos.writeObject(user);
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // Next panel if everything is correct
        System.out.println("Next panel");
        app.nextPanel();
    }

    private void readUsersFile() {
        String users = Paths.get(System.getProperty("user.dir"), "user.dad").toString();
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
    }
}