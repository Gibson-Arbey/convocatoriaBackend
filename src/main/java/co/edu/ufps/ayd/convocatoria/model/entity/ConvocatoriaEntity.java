package co.edu.ufps.ayd.convocatoria.model.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de entidad que representa la tabla "convocatoria" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "convocatoria")
public class ConvocatoriaEntity {

    /* *
     * Id de la convocatoria
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /* *
     * Nombre de la convocatoria
     */
    @Column(nullable = false)
    private String nombre;

    /* *
     * Fecha de inicio de inscripciones de la convocatoria
     */
    @Column(name = "fechaInicio",nullable = false)
    @NotNull(message = "La fecha de inicio de inscripciones no puede estar vacía")
    private Date fechaInicio;

    /* *
     * Fecha de inicio de inscripciones de la convocatoria
     */
    @Column(name = "fechaFin",nullable = false)
    @NotNull(message = "La fecha de fin de inscripciones no puede estar vacía")
    private Date fechaFin;

    /* *
     * Fecha de inicio de inscripciones de la convocatoria
     */
    @Column(name = "fechaResultados",nullable = false)
    @NotNull(message = "La fecha de presentación de resultados no puede estar vacía")
    private Date fechaResultados;

    /* *
     * Estado actual de la convocatoria
     */
    @Column(nullable = false)
    private Boolean estado;

    /* *
     * Mapeo de las propuestas registradas en la convocatoria
     */
    @OneToMany(mappedBy = "convocatoria")
    @JsonIgnore
    private List<PropuestaEntity> propuestas;
}
