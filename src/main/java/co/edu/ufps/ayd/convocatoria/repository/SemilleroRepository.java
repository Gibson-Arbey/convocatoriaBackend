package co.edu.ufps.ayd.convocatoria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.SemilleroEntity;

@Repository
public interface SemilleroRepository extends JpaRepository<SemilleroEntity, Integer> {
    
    List<SemilleroEntity> findAllByEstado(Boolean estado);

    Optional<SemilleroEntity> findByCodigo(String codigo);

    @Query("SELECT s FROM SemilleroEntity s WHERE s.nombre LIKE %:nombre%")
    List<SemilleroEntity> findByNombreContaining(@Param("nombre") String nombre);
}
