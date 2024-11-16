package gei.id.tutelado.model;

import javax.persistence.*;

@Entity
@Table(name = "Rescate")
public class Rescate extends Intervencion{
    @Column(nullable = false)
    String objetivo;
    @Column(nullable = false)
    String estado;
    @Column(nullable = false)
    String localidad;



}
