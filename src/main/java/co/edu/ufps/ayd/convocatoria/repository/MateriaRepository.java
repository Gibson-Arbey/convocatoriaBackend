package co.edu.ufps.ayd.convocatoria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import co.edu.ufps.ayd.convocatoria.model.entity.MateriaEntity;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Integer>{

    List<MateriaEntity> findAllByEstado(Boolean estado);

    Optional<MateriaEntity> findByCodigoAndGrupo(String codigo, String grupo);

    @Query("SELECT m FROM MateriaEntity m WHERE m.nombre LIKE %:nombre%")
    List<MateriaEntity> findByNombreContaining(@Param("nombre") String nombre);
}
