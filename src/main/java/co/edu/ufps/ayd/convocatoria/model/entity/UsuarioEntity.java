package co.edu.ufps.ayd.convocatoria.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/* *
 * Clase de entidad que representa la tabla "usuario" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario", indexes = {@Index(columnList = "email", name = "index_email", unique = true),
                                    @Index(columnList = "codigo", name = "index_codigo", unique = true) })
public class UsuarioEntity {
    
    /* *
     * Id del usuario
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /* *
     * Codigo del usuario asociado a la UFPS
     */
    @Column(nullable = false, length = 10)
    @NotEmpty
    private String codigo;

    /* *
     * Email del usuario
     */
    @Column(nullable = false, length = 255)
    @NotEmpty
    private String email;

    /* *
     * contraseña encriptada del usuario
     */
    @Column(nullable = false, length = 255)
    @NotEmpty
    private String contrasenia;

    /* *
     * Estado del usuario
     */
    @Column(nullable = false)
    private Boolean estado;

    /* *
     * Rol del usuario
     */
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolEntity rol;

    /* *
     * Mapeo de las propuestas registradas del usuario
     */
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<PropuestaEntity> propuestasRegistradas;

    /* *
     * Mapeo de las propuestas asignadas del usuario
     */
    @OneToMany(mappedBy = "evaluador")
    @JsonIgnore
    private List<PropuestaEntity> propuestasAsignadas;
}
