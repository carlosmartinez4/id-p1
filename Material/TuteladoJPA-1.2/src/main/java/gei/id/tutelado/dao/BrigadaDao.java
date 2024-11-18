package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Brigada;

import java.util.List;

public interface BrigadaDao {

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    /* MO4.1 */ Brigada recuperaPorNombre (String nombre);
    /* MO4.2 */ Brigada almacena (Brigada brigada);
    /* MO4.3 */ void elimina (Brigada brigada);
    /* MO4.4 */ Brigada modifica (Brigada brigada);

    // OPERACIONS POR ATRIBUTOS LAZY
    /* MO4.5 */ Brigada restauraBomberos (Brigada brigada);
    // Recibe un usuario coa coleccion de entradas de log como proxy SEN INICIALIZAR
    // Devolve unha copia do usuario coa coleccion de entradas de log INICIALIZADA

    // CONSULTAS JPQL
    //* Aqui irian o resto das consultas que se piden no enunciado (non implementadas)
    /* MO4.6.c */
    List<Brigada> buscarBrigadasPorRescatesEnUnaLocalidad(String localidad);

}