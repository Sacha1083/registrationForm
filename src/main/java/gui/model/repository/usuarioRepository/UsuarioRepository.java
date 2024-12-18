package gui.model.repository.usuarioRepository;

import gui.model.entity.Usuario;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UsuarioRepository class
 * <p>
 *     Repository class for managing user data in the SQLite database. <br>
 *     Provides methods for user authentication, insertion, deletion, and retrieval of user data.
 * </p>
 * @see Connection - Connection to the SQLite database
 * @see PreparedStatement - SQL statement for executing queries
 * @see ResultSet - Result set of a query
 * @see Usuario - User entity class
 * @see DriverManager - Manages a list of database drivers
 * @author Sacha1083
 * @version 2.0
 * @since JDK21.0.5
 */
public class UsuarioRepository implements IUsuarioRepository {
    private final String fileResource;
    private Connection conn;

    /**
     * UsuarioRepository constructor
     * <p>
     *     Initializes the path to the SQLite database file.
     * </p>
     * @see Paths - Provides methods for converting path strings to Path objects
     * @see #fileResource - Path to the SQLite database file
     * @since JDK21.0.5
     */
    public UsuarioRepository() {
        fileResource = Paths.get(System.getProperty("user.dir"), "data", "userData.db").toString();
    }

    /**
     * Get database connection
     * <p>
     *     Establishes and returns a connection to the SQLite database.
     * </p>
     * @see DriverManager - Manages a list of database drivers
     * @see Connection - Connection to the SQLite database
     * @see SQLException - An exception that provides information on a database access error or other errors
     * @see #fileResource - Path to the SQLite database file
     * @return Connection - The connection to the SQLite database
     * @since JDK21.0.5
     */
    public Connection getConnection() {
        String DB_URL = "jdbc:sqlite:" + fileResource;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Authenticate user
     * <p>
     *     Authenticates a user by email and password.
     * </p>
     * @param email The user's email
     * @param password The user's password
     * @see #getConnection() - Get database connection
     * @see Usuario - User entity class
     * @return Usuario - The authenticated user, or null if authentication fails
     * @since JDK21.0.5
     */
    public Usuario authenticateUser(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?;";
        Usuario usuario = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            usuario = null;
        }

        return usuario;
    }

    /**
     * Insert user
     * <p>
     *     Inserts a new user into the database.
     * </p>
     * @see #getConnection() - Get database connection
     * @see Usuario - User entity class
     * @param user The user to be inserted
     * @return Boolean - true if insertion is successful, false otherwise
     * @since JDK21.0.5
     */
    public Boolean insertUser(Usuario user) {
        String sql = "INSERT INTO Users(name, email, password, registerDate) VALUES(?, ?, ?, ?);";
        Boolean insert;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getRegisterDate());

            insert = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            insert = null;
        }

        return insert;
    }

    /**
     * Delete user
     * <p>
     *     Deletes a user from the database by ID.
     * </p>
     * @see #getConnection() - Get database connection
     * @param id The ID of the user to be deleted
     * @return Boolean - true if deletion is successful, false otherwise
     * @since JDK21.0.5
     */
    public Boolean deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE id = ?;";
        Boolean delete;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            delete = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            delete = null;
        }

        return delete;
    }

    /**
     * Get all users
     * <p>
     *     Retrieves a list of all users from the database.
     * </p>
     * @see Usuario - User entity class
     * @see #getConnection() - Get database connection
     * @return {@code List<Usuario>} - A list of all users
     * @since JDK21.0.5
     */
    public List<Usuario> getAllUsers() {
        String sql = "SELECT * FROM Users;";
        List<Usuario> usuarios;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            usuarios = new ArrayList<>();
            while (rs.next()) {
                usuarios.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            usuarios = null;
        }

        return usuarios;
    }
}