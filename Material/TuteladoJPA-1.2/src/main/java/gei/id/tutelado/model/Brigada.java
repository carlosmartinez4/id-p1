package gei.id.tutelado.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

@TableGenerator(name="xeradorIdsBrigadas", table="taboa_ids",
        pkColumnName="nome_id", pkColumnValue="idBrigada",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries({
        @NamedQuery(name="Brigada.recuperaPorNombre",
                query="SELECT b FROM Brigada b where b.nombre=:nombre")
})

@Entity
public class Brigada {
    @Id
    @GeneratedValue(generator="xeradorIdsBrigadas")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String localidad;

    @OneToMany (mappedBy="brigada", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.REMOVE} )
    private Set<Bombero> bomberos = new HashSet<>();
    // NOTA: necesitamos @OrderBy, ainda que a colección estea definida como LAZY, por se nalgun momento accedemos á propiedade DENTRO de sesión.
    // Garantimos así que cando Hibernate cargue a colección, o faga na orde axeitada na consulta que lanza contra a BD

    @ManyToMany
    @JoinTable(name="Brigada_Intervencion",
        joinColumns = @JoinColumn(name="id_brigada"),
        inverseJoinColumns = @JoinColumn(name="id_intervencion"))
    private Set<Intervencion> intervenciones = new HashSet<>();

    // Metodo de conveniencia para asegurarno3s de que actualizamos os dous extremos da asociación ao mesmo tempo
    public void addBombero(Bombero bombero) {
        if (bombero.getBrigada() != null)
            throw new RuntimeException ("");
        bombero.setBrigada(this);
        this.bomberos.add(bombero);
    }

    // Metodo de conveniencia para asegurarnos de que actualizamos os dous extremos da asociación ao mesmo tempo
    public void delBombero(Bombero bombero) {
        if (!this.bomberos.contains(bombero))
            throw new RuntimeException ("");
        if (!bombero.getBrigada().equals(this))
            throw new RuntimeException ("");
        bombero.setBrigada(null);
        this.bomberos.remove(bombero);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Set<Bombero> getBomberos() {
        return bomberos;
    }

    public void setBomberos(Set<Bombero> bomberos) {
        this.bomberos = bomberos;
    }

    public Set<Intervencion> getIntervenciones() {
        return intervenciones;
    }

    public void setIntervenciones(Set<Intervencion> intervenciones) {
        this.intervenciones = intervenciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brigada other = (Brigada) o;
        if (nombre == null) {
            return other.nombre == null;
        } else if (!nombre.equals(other.nombre))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Brigada{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", localidad='" + localidad + '\'' +
                ", bomberos=" + bomberos +
                ", intervenciones=" + intervenciones +
                '}';
    }
}
