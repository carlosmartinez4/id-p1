package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Intervencion;

import java.util.List;

public interface IntervencionDao {
    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    /* MO4.1 */ Intervencion buscarPorCodigo (String codigo);
    /* MO4.2 */ Intervencion almacena (Intervencion intervencion);
    /* MO4.3 */ void elimina (Intervencion intervencion);
    /* MO4.4 */ Intervencion modifica (Intervencion intervencion);

    // CONSULTAS JPQL;
    /* MO4.5 */ int countRescatePorLocalidad(String localidad);

}
