package cl.eventia.eventia_backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.eventia.eventia_backend.exception.ResourceNotFoundException;
import cl.eventia.eventia_backend.model.Evento;
import cl.eventia.eventia_backend.repository.EventoRepository;
import cl.eventia.eventia_backend.service.EventoService;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public Evento crear(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    @Override
    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con ID: " + id));
    }

    @Override
    public Evento actualizar(Long id, Evento eventoModificado) {
        Evento eventoExistente = buscarPorId(id); // Reusamos el método que ya lanza excepción si no existe

        // Actualizamos los datos
        eventoExistente.setTitulo(eventoModificado.getTitulo());
        eventoExistente.setDescription(eventoModificado.getDescription());
        eventoExistente.setFechaEvento(eventoModificado.getFechaEvento());
        eventoExistente.setUbicacion(eventoModificado.getUbicacion());
        eventoExistente.setPrecio(eventoModificado.getPrecio());
        eventoExistente.setStock(eventoModificado.getStock());
        eventoExistente.setCategoria(eventoModificado.getCategoria());

        return eventoRepository.save(eventoExistente);
    }

    @Override
    public void eliminar(Long id) {
        Evento evento = buscarPorId(id); // Verificamos que exista antes de borrar
        eventoRepository.delete(evento);
    }
}