package gei.id.tutelado.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name="Rescate.recuperaPorCodigo",
                query="SELECT i, r FROM Intervencion i JOIN Rescate r ON i.id = r.id WHERE i.codigo=:codigo")
})

@Entity
@DiscriminatorValue("RES")
@Table(name="Rescate")
@PrimaryKeyJoinColumn(name="id_intervencion")
public class Rescate extends Intervencion{

    @Column(nullable = false)
    String objetivo;

    @Column(nullable = false)
    String estado;

    @Column(nullable = false)
    String localidad;

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
}
