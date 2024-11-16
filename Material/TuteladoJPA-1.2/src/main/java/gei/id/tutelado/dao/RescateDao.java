package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Rescate;

public interface RescateDao {
    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    /* MO4.1 */ Rescate recuperaPorCodigo (String codigo);
    /* MO4.2 */ Rescate almacena (Rescate rescate);
    /* MO4.3 */ void elimina (Rescate rescate);
    /* MO4.4 */ Rescate modifica (Rescate rescate);

    // OPERACIONS POR ATRIBUTOS LAZY
    /* MO4.5 */
    // Recibe un usuario coa coleccion de entradas de log como proxy SEN INICIALIZAR
    // Devolve unha copia do usuario coa coleccion de entradas de log INICIALIZADA

    // CONSULTAS JPQL
    //* Aqui irian o resto das consultas que se piden no enunciado (non implementadas)
}
