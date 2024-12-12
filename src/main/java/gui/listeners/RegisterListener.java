package gui.listeners;

import gui.App;
import gui.UsuarioController;
import gui.model.entity.Usuario;
import util.TextData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>RegisterListener class</h1>
 * <p>
 *     Class for managing the registration of a new user. <br>
 *     It validates the user data and registers the user in the application. <br>
 *     It also shows error messages if the data is incorrect.
 * </p>
 * @see App - The main application class
 * @see UsuarioController - Controller for managing user data using SQLite
 * @see Usuario - Entity class for user data
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class RegisterListener extends Component implements ActionListener {
    private final App app;
    private final String option;
    private final JTextField name;
    private final JTextField eMail;
    private final JPasswordField password;
    private List<Usuario> userList;
    private UsuarioController usuarioController;

    /**
     * <h1>RegisterListener constructor</h1>
     * <p>
     *     Initializes the listener for user registration.
     * </p>
     * @param app The main application class
     * @param option The option to perform: login or back
     * @param name The user's name
     * @param eMail The user's email
     * @param password The user's password
     * @see App - The main application class
     * @since JDK21.0.5
     */
    public RegisterListener(App app, String option, JTextField name, JTextField eMail, JPasswordField password) {
        System.out.println("LoginListener created");
        this.app = app;
        this.option = option;
        this.name = name;
        this.eMail = eMail;
        this.password = password;
        userList = new ArrayList<>();
        usuarioController = new UsuarioController();
    }

    /**
     * <h1>Action performed method</h1>
     * <p>
     *     Method to perform the action according to the option selected.
     * </p>
     * @param e The action event
     * @since JDK21.0.5
     */
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

    /**
     * <h1>Login method</h1>
     * <p>
     *     Method to validate the user data and register the user in the application.
     * </p>
     * @since JDK21.0.5
     */
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

        Usuario newUser = new Usuario(nameText, eMailText, passwordText);
        Boolean userAdded = usuarioController.registerUser(newUser);
        String msg;

        if (userAdded == null) {
            msg = TextData.getText("userAddError");
            System.out.println(msg);
            JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (userAdded) {
            msg = TextData.getText("userAdded");
            System.out.println(msg);
            JOptionPane.showMessageDialog(this, msg, TextData.getText("successfullyTitle"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            msg = TextData.getText("userExists");
            System.out.println(msg);
            JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Next panel if everything is correct
        System.out.println("Next panel");
        app.nextPanel();
    }
}