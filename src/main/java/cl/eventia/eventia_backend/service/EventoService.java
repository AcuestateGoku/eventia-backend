package cl.eventia.eventia_backend.service;

import java.util.List;

import cl.eventia.eventia_backend.model.Evento;

public interface EventoService {
    Evento crear(Evento evento);
    List<Evento> listarTodos();
    Evento buscarPorId(Long id);
    Evento actualizar(Long id, Evento evento);
    void eliminar(Long id);
}