package com.giss.gpre.ejb;

import java.util.List;

import javax.ejb.Local;

import com.giss.gpre.datos.DatosUsuarioSituacion;

/**
 * Interfaz del servicio para consultas de situación de usuarios
 * Proporciona métodos para obtener información detallada de usuarios
 * combinando datos de PERSONAS, HISTORICOSITUACIONES, UNIDADES, EMPRESAS, etc.
 * 
 * @author GISS
 */
@Local
public interface UsuarioSituacionService {

    /**
     * Obtiene la situación completa de los usuarios para un usuario específico
     * Combina información de múltiples tablas según los permisos del usuario
     * 
     * @param cdUsuario Código de usuario (DNI) para filtrar la consulta
     * @return Lista de DatosUsuarioSituacion con la información completa
     */
    List<DatosUsuarioSituacion> obtenerSituacionUsuarios(String cdUsuario);
    
}
