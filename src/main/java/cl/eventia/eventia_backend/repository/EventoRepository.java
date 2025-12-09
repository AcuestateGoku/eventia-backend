package cl.eventia.eventia_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.eventia.eventia_backend.model.Categoria;
import cl.eventia.eventia_backend.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    
    // Solo con escribir esto, Spring crea el SQL para filtrar por categoría
    List<Evento> findByCategoria(Categoria categoria);
}