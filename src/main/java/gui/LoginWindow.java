package gui;

import util.controller.UsuarioController;
import util.model.entity.Usuario;
import util.BackupData;
import util.TextData;
import util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

import static java.awt.GridBagConstraints.*;

/**
 * CoginWindow class
 * <p>
 *     Class for creating the login window of the application. <br>
 *     It allows the user to log in with their email and password. <br>
 *     The user can exit the application or log in.
 * </p>
 * @author Sacha1083
 * @version 2.0
 * @see JFrame - Top-level container for the application window
 * @see UsuarioController - Controller for the Usuario entity
 * @see BackupData - Class for working
 * @see BackupData#downloadData() - Downloads the data from the S3 bucket
 * @see TextData - Class for working with the text data
 * @see TextFont - Class for working with the text font
 * @since JDK21.0.5
 */
public class LoginWindow extends JFrame {
    private UsuarioController usuarioController;

    /**
     * CoginWindow constructor
     * <p>
     *     Creates the login window with the user and password fields. <br>
     *     The user can exit the application or log in.
     * </p>
     * <p>
     *     If the database does not exist, the user is prompted to download the data. <br>
     *     If the download is successful, the user can log in. <br>
     *     Data is downloaded from the S3 bucket, by Storj.io.
     * </p>
     * @param latch The countdown latch for the login window
     * @see CountDownLatch - Synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes
     * @see UsuarioController - Controller for the Usuario entity
     * @see BackupData - Class for working with the S3 bucket
     * @see BackupData#downloadData() - Downloads the data from the S3 bucket
     * @since JDK21.0.5
     */
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
            Path filePath = Paths.get(System.getProperty("user.dir"), "data", "userData.db");
            boolean databaseExists = filePath.toFile().exists();
            if (!databaseExists) {
                if (filePath.getParent().toFile().exists() || filePath.getParent().toFile().mkdirs()) {
                    int option = JOptionPane.showConfirmDialog(null, TextData.getText("downloadData"), "Download", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        if (BackupData.downloadData()) {
                            JOptionPane.showMessageDialog(null, TextData.getText("downloadSuccess"), "Download", JOptionPane.INFORMATION_MESSAGE);
                            databaseExists = true;
                        } else {
                            JOptionPane.showMessageDialog(null, TextData.getText("downloadError"), "Download", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, TextData.getText("errorData"), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (databaseExists) {
                Usuario loginUsuario = usuarioController.authenticateUser(userTextField.getText(), new String(passwordField.getPassword()));

                if (loginUsuario != null) {
                    JOptionPane.showMessageDialog(null, TextData.getText("welcome") + loginUsuario.getName(), "Login", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);
                    latch.countDown();
                } else {
                    JOptionPane.showMessageDialog(null, TextData.getText("incorrectLogin"), "Login", JOptionPane.ERROR_MESSAGE);
                    userTextField.setText("");
                    passwordField.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha podido iniciar sesión porque no hay base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(login, gbc);
    }

    /**
     * ConfigLoginPanel method
     * <p>
     *     Configures the login window with the user and password fields. <br>
     *     The user can exit the application or log in.
     * </p>
     * @param latch The countdown latch for the login window
     * @see CountDownLatch - Synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes
     * @since JDK21.0.5
     */
    private void configLoginPanel(CountDownLatch latch) {
        setTitle("Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        usuarioController = new UsuarioController();
    }
}