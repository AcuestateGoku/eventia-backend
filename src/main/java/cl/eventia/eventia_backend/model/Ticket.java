package cl.eventia.eventia_backend.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private LocalDateTime fechaCompra;

    // RELACIÓN 1: Muchos tickets pertenecen a un Usuario
    @ManyToOne(fetch = FetchType.EAGER) // EAGER para que al pedir el ticket, traiga los datos del usuario
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // RELACIÓN 2: Muchos tickets pertenecen a un Evento
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    // --- CONSTRUCTORES ---
    public Ticket() {}

    public Ticket(Long id, Usuario usuario, Evento evento) {
        this.id = id;
        this.usuario = usuario;
        this.evento = evento;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }

    // --- AUDITORÍA ---
    @PrePersist
    protected void onCreate() {
        fechaCompra = LocalDateTime.now();
    }

    // --- MÉTODOS DE OBJETO ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", fechaCompra=" + fechaCompra +
                '}';
    }
}