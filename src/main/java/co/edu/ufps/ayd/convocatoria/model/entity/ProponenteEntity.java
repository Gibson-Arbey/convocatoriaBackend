package co.edu.ufps.ayd.convocatoria.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de entidad que representa la tabla "proponente" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proponente")
public class ProponenteEntity {
    
    /* *
     * Id del proponente
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /* *
     * Codigo del estudiante asociado a la UFPS
     */
    @Column(nullable = false, length = 10)
    @NotEmpty
    private String codigo;

    /* *
     * Nombre del estudiante de la UFPS
     */
    @Column(nullable = false, length = 50)
    @NotEmpty
    private String nombre;

    /* *
     * Cedula del estudiante de la UFPS
     */
    @Column(nullable = false, length = 15)
    @NotEmpty
    private String cedula;

    /* *
     * Semestre del estudiante de la UFPS
     */
    @Column(nullable = false, length = 3)
    @NotEmpty
    private String semestre;

}
