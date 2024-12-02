package gui.model.repository.usuarioRepository;

import gui.model.entity.Usuario;

import java.sql.Connection;

public interface IUsuarioRepository {
    Connection getConnection();
    Usuario authenticateUser(String email, String password);
    boolean insertUser(Usuario user);
    boolean deleteUser(int id);
}