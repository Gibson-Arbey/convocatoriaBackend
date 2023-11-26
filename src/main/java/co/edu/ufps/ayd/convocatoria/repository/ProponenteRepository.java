package co.edu.ufps.ayd.convocatoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;


@Repository
public interface ProponenteRepository extends JpaRepository<ProponenteEntity, Integer>{
    
    Optional<ProponenteEntity> findByCodigo(String codigo);

    Optional<ProponenteEntity> findByNombre(String nombre);
}
