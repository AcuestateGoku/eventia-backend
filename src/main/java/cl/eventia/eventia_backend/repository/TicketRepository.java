package cl.eventia.eventia_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.eventia.eventia_backend.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Aquí podríamos agregar: "Buscar tickets por usuario"
    // List<Ticket> findByUsuarioId(Long usuarioId);
}