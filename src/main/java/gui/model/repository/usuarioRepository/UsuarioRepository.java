package gui.model.repository.usuarioRepository;

import gui.model.entity.Usuario;

import java.sql.*;

public class UsuarioRepository implements IUsuarioRepository{
    private static Connection conn = null;
    private final String DB_URL = "jdbc:sqlite:src/main/resources/userData.db";

    public Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Connection to SQLite has been established.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
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
                usuario = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            usuario = null;
        }

        return usuario;
    }

    public boolean insertUser(Usuario user) {
        String sql = "INSERT INTO Users(name, email, password) VALUES(?, ?, ?);";
        boolean insert;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());

            insert = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            insert = false;
        }

        return insert;
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE id = ?;";
        boolean delete;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            delete = pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            delete = false;
        }

        return delete;
    }
}