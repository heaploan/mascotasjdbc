
package com.mycompany.mascotasjdbc.model;

/**
 *
 * @author mfontana
 */

// Esta clase es para almacenar el número de mascotas por propietario

public class ContadorMascotasTO {
    private String propietario;
    private int cantidad;

    public ContadorMascotasTO(String propietario, int cantidad) {
        this.propietario = propietario;
        this.cantidad = cantidad;
    }

    public String getPropietario() {
        return propietario;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return  "Propietario: " + propietario + ", cantidad: " + cantidad;
    }
}
