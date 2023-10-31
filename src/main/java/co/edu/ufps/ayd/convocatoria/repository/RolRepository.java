package co.edu.ufps.ayd.convocatoria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.RolEntity;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Integer> {
    
    RolEntity findByNombre(String nombre);
}
