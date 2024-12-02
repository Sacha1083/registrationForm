package gui.model.service.usuarioService;

import gui.model.entity.Usuario;
import gui.model.repository.usuarioRepository.UsuarioRepository;

import java.util.Base64;

public class UsuarioService implements IUsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService() {
        usuarioRepository = new UsuarioRepository();
    }

    @Override
    public Usuario authenticateUser(String email, String password) {
        password = Base64.getEncoder().encodeToString(password.getBytes());
        Usuario user = usuarioRepository.authenticateUser(email, password);

        // Decrypt the password
        if (user != null) {
            user.setPassword(new String(Base64.getDecoder().decode(user.getPassword())));
        } else {
            System.out.println("User not found");
        }

        return user;
    }

    @Override
    public boolean addUser(Usuario user) {
        // Encrypt the password using base64
        user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));

        return usuarioRepository.insertUser(user);
    }

    @Override
    public boolean deleteUser(int id) {
        return usuarioRepository.deleteUser(id);
    }
}
