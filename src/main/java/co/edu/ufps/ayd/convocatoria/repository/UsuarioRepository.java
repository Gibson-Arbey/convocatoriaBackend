package co.edu.ufps.ayd.convocatoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.RolEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    
    Optional<UsuarioEntity> findByEmail(String email);

    Optional<UsuarioEntity> findBycodigo(String codigo);

    List<UsuarioEntity> findByRol(RolEntity rol);
}
