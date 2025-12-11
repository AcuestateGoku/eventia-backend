package cl.eventia.eventia_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.eventia.eventia_backend.exception.ResourceNotFoundException;
import cl.eventia.eventia_backend.model.Evento;
import cl.eventia.eventia_backend.model.Ticket;
import cl.eventia.eventia_backend.model.Usuario;
import cl.eventia.eventia_backend.repository.EventoRepository;
import cl.eventia.eventia_backend.repository.TicketRepository;
import cl.eventia.eventia_backend.repository.UsuarioRepository;
import cl.eventia.eventia_backend.service.TicketService; // Importante para que el descuento de stock sea seguro

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Override
    @Transactional // Si falla algo, se deshacen todos los cambios (rollback)
    public Ticket comprarTicket(Long usuarioId, Long eventoId) {
        // 1. Buscar Usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        // 2. Buscar Evento
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + eventoId));

        // 3. VALIDACIÓN DE NEGOCIO: ¿Hay stock?
        if (evento.getStock() <= 0) {
            throw new RuntimeException("¡Lo sentimos! No quedan entradas disponibles para este evento.");
        }

        // 4. Descontar Stock
        evento.setStock(evento.getStock() - 1);
        eventoRepository.save(evento); // Guardamos el evento con el nuevo stock

        // 5. Crear y Guardar Ticket
        Ticket ticket = new Ticket();
        ticket.setUsuario(usuario);
        ticket.setEvento(evento);

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket buscarPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        // Opcional: Al eliminar ticket, podrías devolver el stock (+1)
        ticketRepository.deleteById(id);
    }
}