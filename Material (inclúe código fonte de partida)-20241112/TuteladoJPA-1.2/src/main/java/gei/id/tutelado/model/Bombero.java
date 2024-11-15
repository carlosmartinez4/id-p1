package gei.id.tutelado.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableGenerator(name="generadorIdsBombero", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idBombero",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)

@NamedQueries({
        @NamedQuery(name="Bombero.buscarPorNSS",
                query="SELECT b FROM Bombero b where b.nss=:nss")
})

@Entity
public class Bombero {
    @Id
    @GeneratedValue(generator = "generadorIdsBombero")
    private Long id;

    @Column(unique = true, nullable = false)
    private String nss;

    @Column(unique = false, nullable = false)
    private String nombre;

    @Column(unique = false, nullable = false)
    private LocalDateTime fechaHoraAlta;

    @Column(unique = false, nullable = false)
    private LocalDate fechaNacimiento;

    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, unique = false)
    private Brigada brigada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaHoraAlta() {
        return fechaHoraAlta;
    }

    public void setFechaHoraAlta(LocalDateTime fechaHoraAlta) {
        this.fechaHoraAlta = fechaHoraAlta;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Brigada getBrigada() {
        return brigada;
    }

    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bombero other = (Bombero) o;
        if (nss == null) {
            return other.nss == null;
        } else if (!nss.equals(other.nss))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nss == null) ? 0 : nss.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Bombero{" +
                "id=" + id +
                ", nss='" + nss + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaHoraAlta=" + fechaHoraAlta +
                ", fechaNacimiento=" + fechaNacimiento +
                ", brigada=" + brigada +
                '}';
    }
}