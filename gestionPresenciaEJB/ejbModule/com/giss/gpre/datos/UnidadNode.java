package com.giss.gpre.datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Nodo para representar la estructura jerárquica de unidades
 * 
 * @author GISS
 */
public class UnidadNode implements Serializable {
    
    private static final long serialVersionUID = 123456789L;
    private DatosUnidades datos;
    private List<UnidadNode> hijos;
    
    public UnidadNode(DatosUnidades datos) {
        this.datos = datos;
        this.hijos = new ArrayList<>();
    }
    
    public void addHijo(UnidadNode hijo) {
        this.hijos.add(hijo);
    }
    
    public DatosUnidades getDatos() {
        return datos;
    }
    
    public void setDatos(DatosUnidades datos) {
        this.datos = datos;
    }
    
    public List<UnidadNode> getHijos() {
        return hijos;
    }
    
    public void setHijos(List<UnidadNode> hijos) {
        this.hijos = hijos;
    }
    
    public boolean tieneHijos() {
        return hijos != null && !hijos.isEmpty();
    }
}
