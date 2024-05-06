/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mascotasjdbc;

import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.Propietario;
import com.mycompany.mascotasjdbc.persistence.MascotasDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfontana
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MascotasDAO mascotasDAO = new MascotasDAO();
            System.out.println("Insertando propietario...");
            try {
                mascotasDAO.insertarPropietario(new Propietario("Juan", "Badalona"));
                System.out.println("Propietario insertado en la BBDD.");
            } catch (MascotaException ex) {
                System.out.println(ex.getMessage());
            }
            ArrayList<Propietario> propis = mascotasDAO.allPropietarios();
            for (Propietario p : propis) {
                System.out.println(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
