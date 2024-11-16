package gei.id.tutelado.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@TableGenerator(name="generadorIdsIncendio", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idIncendio",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)
@Entity
@Table(name = "Incendio")
public class Incendio extends Intervencion{

    @Column(nullable = false)
    double supQuemada;

    @ElementCollection
    @CollectionTable(name = "localidadesAfectadas", joinColumns = @JoinColumn(name = "localidades_id"))
    @Column(name = "localidad", nullable = false)
    Set<String> localidadesAfectadas;

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
