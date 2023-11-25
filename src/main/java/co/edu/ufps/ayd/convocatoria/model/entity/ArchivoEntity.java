package co.edu.ufps.ayd.convocatoria.model.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de entidad que representa la tabla "archivo" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "archivo")
public class ArchivoEntity {

    /* *
     * Id del archivo
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    /* *
     * Nombre del archivo
     */
    @Column(nullable = false)
    private String nombre;

    /* *
     * Url del archivo
     */
    @Column(nullable = false)
    private String url;

    /* *
     * Mapeo de las archivos registrados en propuestas
     */
    @OneToMany(mappedBy = "archivo")
    private List<PropuestaEntity> propuestas;
}
