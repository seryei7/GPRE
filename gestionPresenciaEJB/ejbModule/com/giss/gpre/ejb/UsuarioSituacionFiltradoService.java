package com.giss.gpre.ejb;

import java.util.List;

import javax.ejb.Local;

import com.giss.gpre.datos.DatosUsuarioSituacion;

/**
 * Interfaz del servicio para consultas de situación de usuarios con filtros adicionales
 * Excluye ADMONPUBLICA y al propio usuario consultor de los resultados
 * Usa DISTINCT para eliminar duplicados
 * 
 * @author GISS
 */
@Local
public interface UsuarioSituacionFiltradoService {

    /**
     * Obtiene la situación de usuarios aplicando filtros específicos:
     * - Excluye empresas con código 'ADMONPUBLICA'
     * - Excluye al propio usuario que realiza la consulta
     * - Elimina duplicados con DISTINCT
     * 
     * @param cdUsuario Código de usuario (DNI) para filtrar la consulta
     * @return Lista de DatosUsuarioSituacion con la información completa filtrada
     */
    List<DatosUsuarioSituacion> obtenerSituacionUsuariosFiltrado(String cdUsuario);
    
}
