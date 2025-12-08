package cl.eventia.eventia_backend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.eventia.eventia_backend.dto.LoginDTO;
import cl.eventia.eventia_backend.dto.RegistroDTO;
import cl.eventia.eventia_backend.model.Usuario;
import cl.eventia.eventia_backend.repository.UsuarioRepository;
import cl.eventia.eventia_backend.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistroDTO dto) {
        Usuario u = new Usuario();
        u.setNombres(dto.getNombres());
        u.setApellidos(dto.getApellidos());
        u.setRut(dto.getRut());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setFechaNacimiento(dto.getFechaNacimiento());
        u.setGenero(dto.getGenero());
        u.setCiudad(dto.getCiudad());
        u.setCelular(dto.getCelular());
        // El rol se asignará automáticamente en el servicio como USUARIO

        try {
            Usuario creado = usuarioService.registrar(u);
            creado.setPassword(null);

            Map<String, Object> resp = new HashMap<>();
            resp.put("mensaje", "Usuario registrado con éxito como USUARIO");
            resp.put("usuario", creado);

            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            Map<String, String> err = new HashMap<>();
            err.put("error", ex.getMessage());
            return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto, HttpSession session) {
        Optional<Usuario> opt = usuarioRepository.findByEmail(dto.getEmail());
        if (opt.isEmpty()) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
        }

        Usuario usuario = opt.get();
        // Comparación en texto plano (sin hashing)
        if (!dto.getPassword().equals(usuario.getPassword())) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Credenciales inválidas");
            return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
        }

        // Guardamos datos mínimos en sesión
        session.setAttribute("usuarioId", usuario.getId());
        session.setAttribute("role", usuario.getRole() != null ? usuario.getRole().name() : "INVITADO");

        usuario.setPassword(null);
        Map<String, Object> resp = new HashMap<>();
        resp.put("mensaje", "Login exitoso");
        resp.put("usuario", usuario);

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/guest")
    public ResponseEntity<?> guestAccess() {
        // Buscar usuario invitado precargado (email: guest@eventia.local)
        Optional<Usuario> opt = usuarioRepository.findByEmail("guest@eventia.local");
        if (opt.isEmpty()) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Usuario invitado no configurado en la BD");
            return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
        }
        Usuario guest = opt.get();
        guest.setPassword(null); // No devolver contraseña
        Map<String, Object> resp = new HashMap<>();
        resp.put("mensaje", "Acceso como invitado");
        resp.put("usuario", guest);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            Map<String, String> resp = new HashMap<>();
            resp.put("autenticado", "false");
            return ResponseEntity.ok(resp);
        }
        
        Optional<Usuario> opt = usuarioRepository.findById(usuarioId);
        if (opt.isEmpty()) {
            Map<String, String> resp = new HashMap<>();
            resp.put("autenticado", "false");
            return ResponseEntity.ok(resp);
        }
        
        Usuario usuario = opt.get();
        usuario.setPassword(null);
        Map<String, Object> resp = new HashMap<>();
        resp.put("autenticado", "true");
        resp.put("usuario", usuario);
        return ResponseEntity.ok(resp);
    }

}
