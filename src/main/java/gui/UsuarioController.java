package gui;

import gui.model.entity.Usuario;
import gui.model.service.usuarioService.UsuarioService;

public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController() {
        usuarioService = new UsuarioService();
    }

    public static void main(String[] args) {
        UsuarioService usuarioService1 = new UsuarioService();
        System.out.println(usuarioService1.authenticateUser("cargoncas@alu.edu.gva.es", "Carlos2024."));
        /*
        Usuario usuario = new Usuario("Carlos", "cargoncas@alu.edu.gva.es", "Carlos2024.");
        System.out.println(usuarioService1.addUser(usuario));
        */
    }
}
