package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Bombero;
import gei.id.tutelado.model.Brigada;
import gei.id.tutelado.model.Intervencion;

import java.util.List;

public interface BomberoDao {

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    /* MO4.1 */ Bombero buscarPorNSS (String nss);
    /* MO4.2 */ Bombero almacena (Bombero bombero);
    /* MO4.3 */ void elimina (Bombero bombero);
    /* MO4.4 */ Bombero modifica (Bombero bombero);

    // CONSULTAS JPQL
    /* MO4.6.a */ List<Bombero> buscarBomberosPorBrigada(Brigada b);
    /* MO4.6.b */ List<Bombero> buscarBomberosPorIntervencion(Intervencion i);
}
