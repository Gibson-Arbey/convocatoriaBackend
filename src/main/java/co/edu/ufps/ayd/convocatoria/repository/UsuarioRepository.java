package co.edu.ufps.ayd.convocatoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    
    Optional<UsuarioEntity> findByEmail(String email);
}
