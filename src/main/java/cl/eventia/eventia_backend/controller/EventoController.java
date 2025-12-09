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

import cl.eventia.eventia_backend.model.Evento;
import cl.eventia.eventia_backend.service.EventoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*") // Para que el Frontend no tenga problemas
public class EventoController {

    @Autowired
    private EventoService eventoService;

    // 1. CREAR EVENTO
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Evento evento) {
        Evento nuevoEvento = eventoService.crear(evento);
        
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "¡El evento '" + nuevoEvento.getTitulo() + "' ha sido creado con éxito!");
        response.put("evento", nuevoEvento);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. LISTAR TODOS
    @GetMapping
    public ResponseEntity<List<Evento>> listarTodos() {
        return ResponseEntity.ok(eventoService.listarTodos());
    }

    // 3. BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        // Si no existe, el Service lanza la excepción y el GlobalHandler responde 404
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    // 4. ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Evento evento) {
        Evento eventoActualizado = eventoService.actualizar(id, evento);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "El evento ha sido actualizado correctamente.");
        response.put("evento", eventoActualizado);

        return ResponseEntity.ok(response);
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        eventoService.eliminar(id);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "El evento con ID " + id + " fue eliminado del sistema.");

        return ResponseEntity.ok(response);
    }
}