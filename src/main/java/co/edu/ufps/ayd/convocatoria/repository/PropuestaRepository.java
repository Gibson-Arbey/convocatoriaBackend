package co.edu.ufps.ayd.convocatoria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.ayd.convocatoria.model.entity.PropuestaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.UsuarioEntity;

import java.util.List;


@Repository
public interface PropuestaRepository extends JpaRepository<PropuestaEntity, Integer>{
    
    Optional<PropuestaEntity> findByNombre(String nombre);

    List<PropuestaEntity> findByUsuario(UsuarioEntity usuario);
}
