package gui.model.service.usuarioService;

import gui.model.entity.Usuario;

public interface IUsuarioService {
    Usuario authenticateUser(String email, String password);
    boolean addUser(Usuario user);
    boolean deleteUser(int id);
}
