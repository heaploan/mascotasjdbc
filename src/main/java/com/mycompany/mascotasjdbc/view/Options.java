package com.mycompany.mascotasjdbc.view;

import com.mycompany.mascotasjdbc.excepctions.CommandException;
import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.Propietario;
import com.mycompany.mascotasjdbc.model.util.Validations;
import com.mycompany.mascotasjdbc.persistence.MascotasDAO;
import com.mycompany.mascotasjdbc.view.messages.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Options {
    private String[] command;
    private Validations vl = new Validations();
    private Message msg = new Message();
    private MascotasDAO md = new MascotasDAO();

    public void setCommand(String[] command){
        this.command = command;
    }

    public void agregarPropietario() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command,3);
        String nombre = command[1];
        String poblacion = command[2];
        vl.valNameLength(nombre,20);
        vl.valNameLength(poblacion,20);
        Propietario p = new Propietario(nombre,poblacion);
        md.insertarPropietario(p);
        System.out.println(msg.getMessage(Message.OWNER_SUCCESFULY_ADDED));
    }

    public void agregarMascota() throws CommandException, SQLException, MascotaException{
        vl.valComLength(command, 4);
        String nombre = command[1];
        String fecha_nacimiento = command[2];
        String propietario = command[3];


    }

    public void mostrarPropietarios() throws CommandException, SQLException {
        vl.valComLength(command, 1);
        ArrayList<Propietario> p = md.allPropietarios();
        for(Propietario propietario : p){
            System.out.println(propietario);
        }
    }
}
