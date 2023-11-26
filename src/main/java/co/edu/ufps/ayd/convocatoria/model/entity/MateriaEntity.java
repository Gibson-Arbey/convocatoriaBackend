package co.edu.ufps.ayd.convocatoria.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* *
 * Clase de entidad que representa la tabla "materia" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "materia")
public class MateriaEntity {
    
    /* *
     * Id de la materia asociada a la ufps
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /* *
     * Codigo de la materia asociada a la UFPS
     */
    @Column(nullable = false, length = 10)
    @NotEmpty
    private String codigo;

    /* *
     * Nombre de la materia asociada a la UFPS
     */
    @Column(nullable = false, length = 50)
    @NotEmpty
    private String nombre;

    /* *
     * Grupo de la materia asociada a la UFPS
     */
    @Column(nullable = false, length = 1)
    @NotEmpty
    private String grupo;

    /* *
     * Estado de la materia asociada a la UFPS
     */
    @Column(nullable = false)
    private Boolean estado;

    /* *
     * Mapeo de las materias registradas en propuestas
     */
    @JsonIgnore
    @OneToMany(mappedBy = "materia")
    private List<PropuestaEntity> propuestas;
}
