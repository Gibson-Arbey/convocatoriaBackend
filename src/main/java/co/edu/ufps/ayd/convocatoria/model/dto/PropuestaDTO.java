package co.edu.ufps.ayd.convocatoria.model.dto;

import java.util.List;

import co.edu.ufps.ayd.convocatoria.model.entity.ConvocatoriaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.MateriaEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProfesorEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.ProponenteEntity;
import co.edu.ufps.ayd.convocatoria.model.entity.SemilleroEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropuestaDTO {
    private Integer id;
    private String nombre;
    private ConvocatoriaEntity convocatoria;
    private List<ProponenteEntity> proponentes;
    private Integer puntaje;
    private String tipo;
    private MateriaEntity materia;
    private SemilleroEntity semillero;
    private ProfesorEntity profesor;
    private String archivo;
}
