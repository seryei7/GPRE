package com.giss.gpre.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.giss.gpre.entidades.InformeSaldoMes;
import com.giss.gpre.entidades.InformeSaldoMes_Home;

@Stateless
public class InformeSaldoMesServiceBean implements InformeSaldoMesService {
    
    @PersistenceContext(unitName="gestionPresencia")
    private EntityManager em;

    @Override
    public List<InformeSaldoMes> allInformeSaldoMes() {
        List<InformeSaldoMes> listaInformes = new ArrayList<>();
        InformeSaldoMes_Home informe_home = new InformeSaldoMes_Home(em);
        listaInformes = informe_home.allInformeSaldoMes();
        
        return listaInformes;
    }
    
    @Override
    public List<InformeSaldoMes> informeByUsuario(String cdusuario) {
        List<InformeSaldoMes> listaInformes = new ArrayList<>();
        InformeSaldoMes_Home informe_home = new InformeSaldoMes_Home(em);
        listaInformes = informe_home.informeByUsuario(cdusuario);
        
        return listaInformes;
    }
    
    @Override
    public List<InformeSaldoMes> informeByFecha(String fcfichada) {
        List<InformeSaldoMes> listaInformes = new ArrayList<>();
        InformeSaldoMes_Home informe_home = new InformeSaldoMes_Home(em);
        listaInformes = informe_home.informeByFecha(fcfichada);
        
        return listaInformes;
    }
}