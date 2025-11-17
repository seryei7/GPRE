package com.giss.gpre.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.giss.gpre.entidades.InformeSaldoMes;

@Remote
public interface InformeSaldoMesService {
    
    /** 
     * Recoge toda la lista de informes de saldo mes
     * 
     * @return
     */
    public List<InformeSaldoMes> allInformeSaldoMes();
    
    /** 
     * Recoge los informes por usuario
     * 
     * @param cdusuario
     * @return
     */
    public List<InformeSaldoMes> informeByUsuario(String cdusuario);
    
    /** 
     * Recoge los informes por fecha de fichada
     * 
     * @param fcfichada
     * @return
     */
    public List<InformeSaldoMes> informeByFecha(String fcfichada);
}