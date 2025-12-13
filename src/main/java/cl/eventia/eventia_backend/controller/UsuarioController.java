package cl.eventia.eventia_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.eventia.eventia_backend.model.Usuario;
import cl.eventia.eventia_backend.security.JwtUtil;
import cl.eventia.eventia_backend.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") // Permite que React (puerto 3000) se conecte
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;



    // REGISTRAR (POST)
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.registrar(usuario);

        // Creamos un "Mapa" para diseñar nuestra respuesta JSON
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "¡El usuario ha sido registrado con éxito!");
        response.put("usuario", nuevoUsuario); // Incluimos los datos creados

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @SuppressWarnings("unused")
    @Autowired
    private JwtUtil jwtUtil;

    //LOGIN (POST)
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody Map<String, String> body) {
        try {
            String email = body.get("email");
            String password = body.get("password");

            Usuario usuario = usuarioService.login(email, password);

            // Respuesta para el frontend (usamos el token que ya viene del servicio)
            Map<String, Object> response = new HashMap<>();
            response.put("token", usuario.getToken()); // <--- Directo del usuario
            response.put("rol", usuario.getRol().name());
            response.put("email", usuario.getEmail());
            response.put("usuario_id", usuario.getId()); // Útil para el front

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }
    }

    // LISTAR TODOS (GET)
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // BUSCAR POR ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        if (usuario == null) {
            // Esto activará el ResourceNotFoundException del Handler
            throw new cl.eventia.eventia_backend.exception.ResourceNotFoundException(
                    "Usuario no encontrado con ID: " + id);
        }
        return ResponseEntity.ok(usuario);
    }

    // ACTUALIZAR (PUT)
   @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.actualizar(id, usuario);
        
        if (usuarioActualizado != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "El usuario ha sido actualizado correctamente.");
            response.put("usuario", usuarioActualizado);
            
            return ResponseEntity.ok(response);
        } else {
            // Aquí personalizamos el error 404 también
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se pudo actualizar: El usuario con ID " + id + " no existe.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    // ELIMINAR (DELETE)
   @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        // Primero verificamos si existe para dar un mensaje preciso
        if (usuarioService.buscarPorId(id) != null) {
            usuarioService.eliminar(id);
            
            // Respuesta de Éxito
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "¡El usuario con ID " + id + " ha sido eliminado permanentemente del sistema!");
            
            return ResponseEntity.ok(response);
        } else {
            // Respuesta de Error
            Map<String, String> error = new HashMap<>();
            error.put("error", "No se puede eliminar: El usuario con ID " + id + " no existe.");
            
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

}
