package util.controller;

import util.model.entity.Usuario;
import util.model.service.usuarioService.UsuarioService;

import java.util.List;

/**
 * UsuarioController class
 * <p>
 *     Controller class for managing user data using the UsuarioService. <br>
 *     Provides methods for user authentication, registration, and retrieval of user data.
 * </p>
 * @see UsuarioService - Service class for user-related operations
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class UsuarioController {
    private final UsuarioService usuarioService;

    /**
     * UsuarioController constructor
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
     * Authenticate user
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
     * Register user
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
     * Get all users
     * <p>
     *     Retrieves a list of all users.
     * </p>
     * @see UsuarioService#getAllUsers() - Method in UsuarioService class
     * @return {@code List<Usuario>} - A list of all users
     * @since JDK21.0.5
     */
    public List<Usuario> getAllUsers() {
        return usuarioService.getAllUsers();
    }
}