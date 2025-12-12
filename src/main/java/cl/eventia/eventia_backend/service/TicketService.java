package cl.eventia.eventia_backend.service;

import cl.eventia.eventia_backend.model.Ticket;

public interface TicketService {
    Ticket comprarTicket(Long usuarioId, Long eventoId);
    Ticket buscarPorId(Long id);
    void eliminar(Long id); // Anular compra
}