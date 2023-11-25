package co.edu.ufps.ayd.convocatoria.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de entidad que representa la tabla "propuesta" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "propuesta")
public class PropuestaEntity {
    
    /* *
     * Id de la propuesta
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /* *
     * Usuario que registro la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    /* *
     * Convocatoria en la que se registro la propuesta
     */
    @Column(name = "convocatoria_id", nullable = false)
    private ConvocatoriaEntity convocatoria;

    /* *
     * Nombre de la propuesta
     */
    @Column(nullable = false, length = 255)
    @NotEmpty
    private String nombre;

    /* *
     * Evaluador asignado a la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "evaluador_id")
    private UsuarioEntity evaluador;

    /* *
     * Puntaje obtenido por la propuesta
     */
    private Integer puntaje;

    /* *
     * Tipo de la propuesta S=Semillero o M=Materia
     */
    @Column(nullable = false,length = 1)
    @NotEmpty
    private String tipo;

    /* *
     * Materia a la que pertence la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private MateriaEntity materia;

    /* *
     * Semillero a la que pertence la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "semillero_id")
    private SemilleroEntity semillero;

    /* *
     * Profesor colaborador de la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private ProfesorEntity profesor;

    /* *
     * Archivo de la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "archivo_id")
    private ArchivoEntity archivo;
}
