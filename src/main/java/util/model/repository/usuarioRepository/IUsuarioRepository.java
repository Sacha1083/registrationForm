package util.model.repository.usuarioRepository;

import util.model.entity.Usuario;

import java.sql.Connection;
import java.util.List;

public interface IUsuarioRepository {
    Connection getConnection();
    Usuario authenticateUser(String email, String password);
    Boolean insertUser(Usuario user);
    Boolean deleteUser(int id);
    List<Usuario> getAllUsers();
}