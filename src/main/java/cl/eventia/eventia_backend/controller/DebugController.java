package cl.eventia.eventia_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.eventia.eventia_backend.service.UsuarioService;

/**
 * Endpoints de depuración/QA para probar la aplicación localmente.
 * NO dejar en producción sin controles.
 */
@RestController
@RequestMapping("/api/debug")
@CrossOrigin(origins = "*")
public class DebugController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/users")
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

}
