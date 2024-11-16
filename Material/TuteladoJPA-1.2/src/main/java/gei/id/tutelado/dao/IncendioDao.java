package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Incendio;

public interface IncendioDao {
    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    /* MO4.1 */ Incendio recuperaPorCodigo (String codigo);
    /* MO4.2 */ Incendio almacena (Incendio incendio);
    /* MO4.3 */ void elimina (Incendio incendio);
    /* MO4.4 */ Incendio modifica (Incendio incendio);

    // OPERACIONS POR ATRIBUTOS LAZY
    /* MO4.5 */
    // Recibe un usuario coa coleccion de entradas de log como proxy SEN INICIALIZAR
    // Devolve unha copia del usuario coa coleccion de entradas de log INICIALIZADA

    // CONSULTAS JPQL
    //* Aqui irian o resto das consultas que se piden no enunciado (non implementadas)
}
