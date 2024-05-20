
package com.mycompany.mascotasjdbc.model;

import java.util.Date;

/**
 *
 * @author mfontana
 */
public class Mascota {
    private String nombre;
    private Date nacimiento;
    private Propietario propietario;

    public Mascota() {
    }
    
    

    public Mascota(String nombre, Date nacimiento, Propietario propietario) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.propietario = propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
    
    
    
}
