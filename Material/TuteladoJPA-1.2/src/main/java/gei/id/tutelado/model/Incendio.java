package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NamedQueries({
        @NamedQuery(name="Incendio.recuperaPorCodigo",
                query="SELECT i FROM Intervencion i JOIN Incendio r ON i.id = r.id WHERE i.codigo=:codigo")
})

@Entity
@DiscriminatorValue("INC")
@Table(name="Incendio")
@PrimaryKeyJoinColumn(name="id_intervencion")
public class Incendio extends Intervencion{

    @Column(nullable = false)
    double supQuemada;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "localidadesAfectadas", joinColumns = @JoinColumn(name = "idLocalidad"))
    @Column(name = "localidad", nullable = false)
    Set<String> localidadesAfectadas = new HashSet<>();

    public double getSupQuemada() {
        return supQuemada;
    }

    public void setSupQuemada(double supQuemada) {
        this.supQuemada = supQuemada;
    }

    public Set<String> getLocalidadesAfectadas() {
        return localidadesAfectadas;
    }

    public void setLocalidadesAfectadas(Set<String> localidadesAfectadas) {
        this.localidadesAfectadas = localidadesAfectadas;
    }


}
