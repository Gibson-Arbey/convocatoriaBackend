package co.edu.ufps.ayd.convocatoria.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase de entidad que representa la tabla "proponentePropuesta" en la base de datos.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "proponentePropuesta")
public class ProponentePropuestaEntity {
    
    /* *
     * Id del proponentePropuesta
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    
    /* *
     * Proponente registrado en la propuesta
     */
    @ManyToOne
    @JoinColumn(name = "proponente_id", nullable = false)
    private ProponenteEntity proponente;

    /* *
     * propuesta registrada
     */
    @ManyToOne
    @JoinColumn(name = "propuesta_id", nullable = false)
    private PropuestaEntity propuesta;

    public ProponentePropuestaEntity(PropuestaEntity propuesta, ProponenteEntity proponente) {
        this.proponente = proponente;
        this.propuesta = propuesta;
    }


}
