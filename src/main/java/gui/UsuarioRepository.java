package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioRepository {
    private static final String DB_URL = "jdbc:sqlite:userData.db";

    public static Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public static void insertUser(Usuario user) {
        String sql = "INSERT INTO usuarios(name, email, password) VALUES('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "');";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User " + user.getName() + " inserted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUser(int id, String name, String email, String password) {
        String sql = "UPDATE usuarios SET name = '" + name + "', email = '" + email + "', password = '" + password + "' WHERE id = " + id + ";";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User updated.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUser(int id) {
        String sql = "DELETE FROM usuarios WHERE id = " + id + ";";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("User deleted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}