package gui.model.repository.usuarioRepository;

import gui.model.entity.Usuario;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository implements IUsuarioRepository{
    private final String fileResource;
    private Connection conn;

    public UsuarioRepository() {
        fileResource = Paths.get(System.getProperty("user.dir"), "data", "userData.db").toString();
    }

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

    public Boolean insertUser(Usuario user) {
        String sql = "INSERT INTO Users(name, email, password) VALUES(?, ?, ?);";
        Boolean insert;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());

            insert = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            insert = null;
        }

        return insert;
    }

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