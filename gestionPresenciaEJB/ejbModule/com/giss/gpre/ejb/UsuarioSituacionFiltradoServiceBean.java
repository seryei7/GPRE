package com.giss.gpre.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.giss.gpre.datos.DatosUsuarioSituacion;

/**
 * Implementación del servicio para consultas de situación de usuarios con filtros
 * Ejecuta query nativa con DISTINCT que excluye ADMONPUBLICA y al usuario consultor
 * 
 * @author GISS
 */
@Stateless
public class UsuarioSituacionFiltradoServiceBean implements UsuarioSituacionFiltradoService {

    @PersistenceContext(unitName = "gestionPresencia")
    private EntityManager em;

    @Override
    public List<DatosUsuarioSituacion> obtenerSituacionUsuariosFiltrado(String cdUsuario) {
        List<DatosUsuarioSituacion> resultado = new ArrayList<>();

        try {
            // Query nativa que combina información de múltiples tablas
            // DISTINCT: Elimina duplicados
            // Excluye: ADMONPUBLICA y al propio usuario consultor
            String sql = "SELECT DISTINCT PERSONAS.APELLIDO1, PERSONAS.APELLIDO2, PERSONAS.NOMBRE, " +
                    "PERSONAS.IDDNI, PERSONAS.CDTARJETA, " +
                    "HISTORICOSITUACIONES.ENTI_GES_EP, HISTORICOSITUACIONES.PROV_EP, " +
                    "HISTORICOSITUACIONES.CEN_GES_EP, UNIDADES.DSUNIDAD, " +
                    "HISTORICOSITUACIONES.CDEMPRESA, EMPRESAS.NOMBRE, CONTRATOS.DSCONTRATO, " +
                    "CONTRATOS.DSCONTRATORED, HISTORICOSITUACIONES.NTCODDEN, " +
                    "HISTORICOSITUACIONES.CDCODDEN, CODDEN.DSCODDEN, CODDEN_EMP.DSCODDEN " +
                    "FROM PERSONAS, UNIDADES, GRUPOUSUARIOS, EMPRESAS, CONTRATOS, CODDEN, " +
                    "HISTORICOSITUACIONES, CODDEN_EMP, AREAS_TRABAJO_SEL " +
                    "WHERE GRUPOUSUARIOS.CDUSUARIO = :cdUsuario " +
                    "AND HISTORICOSITUACIONES.CDEMPRESA <> 'ADMONPUBLICA' " +
                    "AND HISTORICOSITUACIONES.ENTI_GES_EP = GRUPOUSUARIOS.ENTI_GES_EP " +
                    "AND HISTORICOSITUACIONES.PROV_EP = GRUPOUSUARIOS.PROV_EP " +
                    "AND HISTORICOSITUACIONES.CEN_GES_EP = GRUPOUSUARIOS.CEN_GES_EP " +
                    "AND HISTORICOSITUACIONES.CDDNI = GRUPOUSUARIOS.CDDNI " +
                    "AND GRUPOUSUARIOS.CDDNI <> :cdUsuario " +
                    "AND (HISTORICOSITUACIONES.FCFIN >= TO_CHAR(SYSDATE,'YYYYMMDD') " +
                    "     OR HISTORICOSITUACIONES.FCFIN = '99991231') " +
                    "AND HISTORICOSITUACIONES.FCINICIO <= TO_CHAR(SYSDATE,'YYYYMMDD') " +
                    "AND HISTORICOSITUACIONES.ENTI_GES_EP = AREAS_TRABAJO_SEL.ENTI_GES_EP " +
                    "AND HISTORICOSITUACIONES.PROV_EP = AREAS_TRABAJO_SEL.PROV_EP " +
                    "AND HISTORICOSITUACIONES.CEN_GES_EP = AREAS_TRABAJO_SEL.CEN_GES_EP " +
                    "AND AREAS_TRABAJO_SEL.IDUSUARIO = :cdUsuario " +
                    "AND HISTORICOSITUACIONES.ENTI_GES_EP = UNIDADES.ENTI_GES_EP " +
                    "AND HISTORICOSITUACIONES.PROV_EP = UNIDADES.PROV_EP " +
                    "AND HISTORICOSITUACIONES.CEN_GES_EP = UNIDADES.CEN_GES_EP " +
                    "AND HISTORICOSITUACIONES.CDCENTRO = UNIDADES.CDCENTRO " +
                    "AND HISTORICOSITUACIONES.CDUNIDAD = UNIDADES.IDUNIDAD " +
                    "AND HISTORICOSITUACIONES.CDEMPRESA = EMPRESAS.IDCIFNIF " +
                    "AND HISTORICOSITUACIONES.ENTI_GES_EP = CONTRATOS.ENTI_GES_EP(+) " +
                    "AND HISTORICOSITUACIONES.PROV_EP = CONTRATOS.PROV_EP(+) " +
                    "AND HISTORICOSITUACIONES.CEN_GES_EP = CONTRATOS.CEN_GES_EP(+) " +
                    "AND HISTORICOSITUACIONES.CDCONTRATO = CONTRATOS.IDCONTRATO(+) " +
                    "AND HISTORICOSITUACIONES.NTCODDEN = CODDEN.NTCODDEN(+) " +
                    "AND HISTORICOSITUACIONES.CDCODDEN = CODDEN.IDCODDEN(+) " +
                    "AND HISTORICOSITUACIONES.ENTI_GES_EP = CODDEN_EMP.ENTI_GES_EP(+) " +
                    "AND HISTORICOSITUACIONES.PROV_EP = CODDEN_EMP.PROV_EP(+) " +
                    "AND HISTORICOSITUACIONES.CEN_GES_EP = CODDEN_EMP.CEN_GES_EP(+) " +
                    "AND HISTORICOSITUACIONES.NTCODDEN = CODDEN_EMP.NTCODDEN(+) " +
                    "AND HISTORICOSITUACIONES.CDCODDEN = CODDEN_EMP.IDCODDEN(+) " +
                    "AND HISTORICOSITUACIONES.CDDNI = PERSONAS.IDDNI";

            Query query = em.createNativeQuery(sql);
            query.setParameter("cdUsuario", cdUsuario);

            @SuppressWarnings("unchecked")
            List<Object[]> resultList = query.getResultList();

            // Mapear los resultados al DTO
            for (Object[] row : resultList) {
                DatosUsuarioSituacion datos = new DatosUsuarioSituacion(
                    (String) row[0],  // apellido1
                    (String) row[1],  // apellido2
                    (String) row[2],  // nombre
                    (String) row[3],  // iddni
                    (String) row[4],  // cdtarjeta
                    convertToBigDecimal(row[5]),  // entiGesEp
                    convertToBigDecimal(row[6]),  // provEp
                    convertToBigDecimal(row[7]),  // cenGesEp
                    (String) row[8],  // dsunidad
                    (String) row[9],  // cdempresa
                    (String) row[10], // nombreEmpresa
                    (String) row[11], // dscontrato
                    (String) row[12], // dscontratored
                    convertToBigDecimal(row[13]), // ntcodden
                    convertToBigDecimal(row[14]), // cdcodden
                    (String) row[15], // dscodden
                    (String) row[16]  // dscoddenEmp
                );
                resultado.add(datos);
            }

        } catch (Exception e) {
            // Log del error para debugging
            System.err.println("Error al obtener situación filtrada de usuarios: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }

    /**
     * Método auxiliar para convertir Object a BigDecimal de forma segura
     */
    private BigDecimal convertToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof Number) {
            return BigDecimal.valueOf(((Number) value).doubleValue());
        }
        return null;
    }
}
