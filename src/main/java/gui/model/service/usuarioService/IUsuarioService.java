package gui.model.service.usuarioService;

import gui.model.entity.Usuario;

import java.util.List;

public interface IUsuarioService {
    Usuario authenticateUser(String email, String password);
    Boolean addUser(Usuario user);
    Boolean deleteUser(int id);
    List<Usuario> getAllUsers();
}
