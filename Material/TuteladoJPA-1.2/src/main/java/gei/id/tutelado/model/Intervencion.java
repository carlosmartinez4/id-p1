package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@TableGenerator(name="xeradorIdsIntervencions", table="taboa_ids",
        pkColumnName="nome_id", pkColumnValue="idIntervencion",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)
@Entity
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)
public abstract class Intervencion {
    @Id
    @GeneratedValue(generator="xeradorIdsIntervencions")
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nivelGravedad;

    @Column(nullable = false, unique=false)
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;

    @ManyToMany
    @JoinTable (name="Brigada_Intervencion",
        joinColumns = @JoinColumn(name="id_intervencion"),
        inverseJoinColumns = @JoinColumn(name="id_brigada"))
    private Set<Brigada> brigadas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(String nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<Brigada> getBrigadas() {
        return brigadas;
    }

    public void setBrigadas(Set<Brigada> brigadas) {
        this.brigadas = brigadas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Intervencion other = (Intervencion) o;
        if (codigo == null) {
            return other.codigo == null;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
        return result;
    }
}
