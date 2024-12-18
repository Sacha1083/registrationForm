package gui.model.service.usuarioService;

import gui.model.entity.Usuario;
import gui.model.repository.usuarioRepository.UsuarioRepository;

import java.util.Base64;
import java.util.List;

/**
 * UsuarioService class
 * <p>
 *     Service class for user-related operations. <br>
 *     Provides methods for user authentication, registration, and retrieval of user data.
 * </p>
 * @see UsuarioRepository - Repository class for user-related operations
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    /**
     * UsuarioService constructor
     * <p>
     *     Initializes the UsuarioRepository.
     * </p>
     * @see UsuarioRepository - Repository class for user-related operations
     * @since JDK21.0.5
     */
    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
    }

    /**
     * Authenticate user
     * <p>
     *     Authenticates a user by email and password.
     * </p>
     * @param email The user's email
     * @param password The user's password
     * @see UsuarioRepository#authenticateUser(String, String) - Method in UsuarioRepository class
     * @return Usuario - The authenticated user, or null if authentication fails
     * @since JDK21.0.5
     */
    @Override
    public Usuario authenticateUser(String email, String password) {
        password = Base64.getEncoder().encodeToString(password.getBytes());
        Usuario user = usuarioRepository.authenticateUser(email, password);

        // Decrypt the password
        if (user != null) {
            user.setPassword(new String(Base64.getDecoder().decode(user.getPassword())));
        } else {
            System.out.println("User not found");
        }

        return user;
    }

    /**
     * Register user
     * <p>
     *     Registers a new user.
     * </p>
     * @param user The user to be registered | (name, email, password)
     * @see UsuarioRepository#insertUser(Usuario) - Method in UsuarioRepository class
     * @return Boolean - true if registration is successful, false otherwise
     * @since JDK21.0.5
     */
    @Override
    public Boolean addUser(Usuario user) {
        // Encrypt the password using base64
        user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));

        return usuarioRepository.insertUser(user);
    }

    /**
     * Delete user
     * <p>
     *     Deletes a user by id.
     * </p>
     * @param id The id of the user to be deleted
     * @see UsuarioRepository#deleteUser(int) - Method in UsuarioRepository class
     * @return Boolean - true if deletion is successful, false otherwise
     * @since JDK21.0.5
     */
    @Override
    public Boolean deleteUser(int id) {
        return usuarioRepository.deleteUser(id);
    }

    /**
     * Get all users
     * <p>
     *     Retrieves a list of all users.
     * </p>
     * @see UsuarioRepository#getAllUsers() - Method in UsuarioRepository class
     * @return {@code List<Usuario>} - A list of all users
     * @since JDK21.0.5
     */
    @Override
    public List<Usuario> getAllUsers() {
        return usuarioRepository.getAllUsers();
    }
}
