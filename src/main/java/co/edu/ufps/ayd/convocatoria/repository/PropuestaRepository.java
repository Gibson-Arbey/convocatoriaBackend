package co.edu.ufps.ayd.convocatoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;

import java.util.List;


@Repository
public interface PropuestaRepository extends JpaRepository<PropuestaEntity, Integer>{
    
    Optional<PropuestaEntity> findByNombre(String nombre);

    List<PropuestaEntity> findByUsuario(UsuarioEntity usuario);

    List<PropuestaEntity> findByConvocatoria(ConvocatoriaEntity convocatoria);

    List<PropuestaEntity> findByEvaluador(UsuarioEntity usuarioEntity);

    @Query("SELECT p FROM PropuestaEntity p LEFT JOIN FETCH p.proponentes WHERE p.id = :id")
    Optional<PropuestaEntity> findByIdWithProponentes(@Param("id") Integer id);

    List<PropuestaEntity> findByConvocatoriaOrderByPuntajeDesc(ConvocatoriaEntity convocatoria);

}
