package cl.eventia.eventia_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- Importante
import org.springframework.stereotype.Service;

import cl.eventia.eventia_backend.model.Usuario;
import cl.eventia.eventia_backend.repository.UsuarioRepository;
import cl.eventia.eventia_backend.security.JwtUtil; // <--- Importante
import cl.eventia.eventia_backend.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder; // Para encriptar

    @Autowired
    private JwtUtil jwtUtil; // Para generar el token

    @Override
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByRut(usuario.getRut())) {
            throw new RuntimeException("El RUT ya está registrado");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El Email ya está registrado");
        }
        
        // ENCRIPTAR PASS ANTES DE GUARDAR
        // Así en la BD queda algo como "$2a$10$XyZ..." ilegible
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
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
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente == null) return null;

        usuarioExistente.setNombres(usuarioModificado.getNombres());
        usuarioExistente.setApellidos(usuarioModificado.getApellidos());
        usuarioExistente.setCelular(usuarioModificado.getCelular());
        usuarioExistente.setCiudad(usuarioModificado.getCiudad());
        usuarioExistente.setGenero(usuarioModificado.getGenero());
        
        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario login(String email, String password) {
        // 1. Buscar usuario
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

        // 2. Validar contraseña (BCrypt)
        // matches(pass_plana, pass_encriptada_bd)
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // 3. Generar Token y asignarlo al usuario (para devolverlo al front)
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());
        usuario.setToken(token);

        return usuario; 
    }
}