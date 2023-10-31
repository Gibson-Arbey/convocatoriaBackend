package co.edu.ufps.ayd.convocatoria.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario", indexes = {@Index(columnList = "email", name = "index_email", unique = true),
                                    @Index(columnList = "codigo", name = "index_codigo", unique = true) })
public class UsuarioEntity {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(nullable = false)
    private String codigo;

    @Column(nullable = false, length = 255)
    @NotEmpty
    private String email;

    @Column(nullable = false, length = 255)
    @NotEmpty
    private String contrasenia;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolEntity rol;

    @Override
    public String toString() {
        return "UsuarioEntity [id=" + id + ", codigo=" + codigo + ", email=" + email + ", contrasenia=" + contrasenia
                + ", estado=" + estado + ", rol=" + rol + "]";
    }
}
