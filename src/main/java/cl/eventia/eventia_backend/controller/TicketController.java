package cl.eventia.eventia_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.eventia.eventia_backend.model.Ticket;
import cl.eventia.eventia_backend.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // POST: Comprar Ticket
    // Esperamos un JSON como: { "usuarioId": 1, "eventoId": 1 }
    // Usaremos un Map para recibir esos dos datos simples sin crear un DTO complejo
    @PostMapping("/compra")
    public ResponseEntity<?> comprar(@RequestBody Map<String, Long> request) {
        Long usuarioId = request.get("usuarioId");
        Long eventoId = request.get("eventoId");

        Ticket nuevoTicket = ticketService.comprarTicket(usuarioId, eventoId);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "¡Compra exitosa! Ticket generado.");
        response.put("ticketId", nuevoTicket.getId());
        response.put("evento", nuevoTicket.getEvento().getTitulo());
        response.put("comprador", nuevoTicket.getUsuario().getNombres());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    // Aquí podrías agregar GET para ver ticket por ID...
}