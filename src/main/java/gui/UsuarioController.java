package gui;

import gui.model.entity.Usuario;
import gui.model.service.usuarioService.UsuarioService;

import java.util.List;

/**
 * <h1>UsuarioController class</h1>
 * <p>
 *     Controller class for managing user data using the UsuarioService. <br>
 *     Provides methods for user authentication, registration, and retrieval of user data.
 * </p>
 * @see UsuarioService - Service class for user-related operations
 * @autor Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class UsuarioController {
    private final UsuarioService usuarioService;

    /**
     * <h1>UsuarioController constructor</h1>
     * <p>
     *     Initializes the UsuarioService.
     * </p>
     * @see UsuarioService - Service class for user-related operations
     * @since JDK21.0.5
     */
    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    /**
     * <h1>Authenticate user</h1>
     * <p>
     *     Authenticates a user by email and password.
     * </p>
     * @param email The user's email
     * @param password The user's password
     * @see UsuarioService#authenticateUser(String, String) - Method in UsuarioService class
     * @return Usuario - The authenticated user, or null if authentication fails
     * @since JDK21.0.5
     */
    public Usuario authenticateUser(String email, String password) {
        return usuarioService.authenticateUser(email, password);
    }

    /**
     * <h1>Register user</h1>
     * <p>
     *     Registers a new user.
     * </p>
     * @param user The user to be registered | (name, email, password)
     * @see UsuarioService#addUser(Usuario) - Method in UsuarioService class
     * @return Boolean - true if registration is successful, false otherwise
     * @since JDK21.0.5
     */
    public Boolean registerUser(Usuario user) {
        return usuarioService.addUser(user);
    }

    /**
     * <h1>Get all users</h1>
     * <p>
     *     Retrieves a list of all users.
     * </p>
     * @see UsuarioService#getAllUsers() - Method in UsuarioService class
     * @return List<Usuario> - A list of all users
     * @since JDK21.0.5
     */
    public List<Usuario> getAllUsers() {
        return usuarioService.getAllUsers();
    }
}