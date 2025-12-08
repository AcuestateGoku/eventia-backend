package cl.eventia.eventia_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.eventia.eventia_backend.model.Role;
import cl.eventia.eventia_backend.model.Usuario;
import cl.eventia.eventia_backend.repository.UsuarioRepository;
import cl.eventia.eventia_backend.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario registrar(Usuario usuario) {
        // Validaciones de negocio
        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new RuntimeException("El email es obligatorio");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El Email ya está registrado");
        }

        if (usuario.getPassword() == null || usuario.getPassword().length() < 6) {
            throw new RuntimeException("La contraseña debe tener al menos 6 caracteres");
        }

        // Asignar rol por defecto como USUARIO (no INVITADO)
        if (usuario.getRole() == null) {
            usuario.setRole(Role.USUARIO);
        }

        // Nota: No se aplica hashing; la contraseña se guarda tal cual (texto plano)
        // (Seguridad reducida — recomendado cambiar en futuro)
        // usuario.setPassword(usuario.getPassword()); // ya está en el objeto

        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuarioModificado) {
        // Buscamos si existe el usuario original
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        
        if (usuarioExistente == null) {
            return null; // O lanzar excepción
        }

        // Actualizamos los campos que permitimos cambiar
        // (No cambiamos el RUT ni el Email para no romper reglas de negocio)
        usuarioExistente.setNombres(usuarioModificado.getNombres());
        usuarioExistente.setApellidos(usuarioModificado.getApellidos());
        usuarioExistente.setCelular(usuarioModificado.getCelular());
        usuarioExistente.setCiudad(usuarioModificado.getCiudad());
        usuarioExistente.setGenero(usuarioModificado.getGenero());
        
        // Guardamos (Hibernate detecta que ya tiene ID y hace UPDATE en vez de INSERT)
        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}