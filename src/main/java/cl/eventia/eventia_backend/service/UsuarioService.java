package cl.eventia.eventia_backend.service;

import java.util.List;

import cl.eventia.eventia_backend.model.Usuario;

public interface UsuarioService {
    // Solo definimos los métodos disponibles
    Usuario registrar(Usuario usuario);

    List<Usuario> listarTodos();

    Usuario buscarPorId(Long id);

    Usuario actualizar(Long id, Usuario usuario);

    void eliminar(Long id);
}