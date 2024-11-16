package gei.id.tutelado.model;

import javax.persistence.*;

@TableGenerator(name="generadorIdsRescate", table="tabla_ids",
        pkColumnName="nombre_id", pkColumnValue="idRescate",
        valueColumnName="ultimo_valor_id",
        initialValue=0, allocationSize=1)
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
