
package com.mycompany.mascotasjdbc.model;

/**
 *
 * @author mfontana
 */
public class Propietario {
    private String nombre;
    private String poblacion;

    public Propietario() {
    }

    public Propietario(String nombre, String poblacion) {
        this.nombre = nombre;
        this.poblacion = poblacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPoblacion() {
        return poblacion;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Poblacion: " + poblacion;
    }
    
    
    
}
