package gui;

import gui.model.entity.Usuario;
import gui.model.service.usuarioService.UsuarioService;

import java.util.List;

public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    public Usuario authenticateUser(String email, String password) {
        return usuarioService.authenticateUser(email, password);
    }

    public Boolean registerUser(Usuario user) {
        return usuarioService.addUser(user);
    }

    public List<Usuario> getAllUsers() {
        return usuarioService.getAllUsers();
    }
}