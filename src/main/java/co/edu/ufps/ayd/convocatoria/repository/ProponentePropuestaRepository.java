package co.edu.ufps.ayd.convocatoria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponentePropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;

@Repository
public interface ProponentePropuestaRepository extends JpaRepository<ProponentePropuestaEntity, Integer>{
    
    List<ProponentePropuestaEntity> findByProponente(ProponenteEntity proponente);

    List<ProponentePropuestaEntity> findByPropuesta(PropuestaEntity propuesta);
}
