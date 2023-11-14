package co.edu.ufps.ayd.convocatoria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;


@Repository
public interface ConvocatoriaRepository extends JpaRepository<ConvocatoriaEntity, Integer> {

    Optional<ConvocatoriaEntity> findByEstado(boolean estado);

    List<ConvocatoriaEntity> findAllByEstado(Boolean estado);

}
