
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

    public Mascota(String nombre, Date nacimiento, Propietario propietario) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.propietario = propietario;
    }
    
    
}
