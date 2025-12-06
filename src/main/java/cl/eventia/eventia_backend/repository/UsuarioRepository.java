package cl.eventia.eventia_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.eventia.eventia_backend.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Métodos de Spring Data JPA
    // Solo con declararlos, Spring crea el SQL automáticamente

    // Para buscar si el usuario existe al hacer Login
    Optional<Usuario> findByEmail(String email);

    // Para evitar registros duplicados
    boolean existsByRut(String rut);

    boolean existsByEmail(String email);
}
