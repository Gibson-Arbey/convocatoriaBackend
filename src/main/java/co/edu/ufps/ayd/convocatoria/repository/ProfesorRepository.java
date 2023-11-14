package co.edu.ufps.ayd.convocatoria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.ProfesorEntity;

@Repository
public interface ProfesorRepository extends JpaRepository<ProfesorEntity, Integer> {
    
    List<ProfesorEntity> findAllByEstado(Boolean estado);

    Optional<ProfesorEntity> findByCodigo(String codigo);

    @Query("SELECT p FROM ProfesorEntity p WHERE p.nombre LIKE %:nombre%")
    List<ProfesorEntity> findByNombreContaining(@Param("nombre") String nombre);
}
