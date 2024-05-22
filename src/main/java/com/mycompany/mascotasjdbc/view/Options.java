package com.mycompany.mascotasjdbc.view;

import com.mycompany.mascotasjdbc.excepctions.CommandException;
import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.ContadorMascotasTO;
import com.mycompany.mascotasjdbc.model.Mascota;
import com.mycompany.mascotasjdbc.model.Propietario;
import com.mycompany.mascotasjdbc.model.util.Validations;
import com.mycompany.mascotasjdbc.persistence.MascotasDAO;
import com.mycompany.mascotasjdbc.view.messages.Message;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Options {
    private String[] command;
    private Validations vl = new Validations();
    private Message msg = new Message();
    private MascotasDAO md = new MascotasDAO();
    private Propietario p = new Propietario();

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
        System.out.println(msg.getMessage(Message.OWNER_SUCCESSFULLY_ADDED));
    }

    public void agregarMascota() throws CommandException, SQLException, MascotaException{
        vl.valComLength(command, 4);
        String nombre = command[1];
        vl.valNameLength(nombre,20);
        String fecha_nacimiento = command[2];
        String propietario = command[3];
        vl.valNameLength(propietario,20);
        vl.valNameLength(propietario,20);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formatter.parse(fecha_nacimiento);
        } catch (ParseException e){
            System.out.println("Error en la fecha, por favor usa dd/mm/yyyy");
        }
        Mascota m = new Mascota(nombre,fecha,md.getPropietario(propietario));
        md.insertarMascota(m);
        System.out.println(msg.getMessage(Message.PET_SUCCESSFULLY_ADDED));
    }

    public void mostrarPropietarios() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command, 1);
        ArrayList<Propietario> p = md.allPropietarios();
        for(Propietario propietario : p){
            System.out.println(propietario);
        }
        if(p.isEmpty()){
            throw new MascotaException("No hay propietarios en la base de datos");
        }
    }

    public void mostrarMascotas() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command, 1);
        ArrayList<Mascota> m = md.allMascotas();
        for(Mascota mascota : m){
            System.out.println(mascota);
        }
        if(m.isEmpty()){
            throw new MascotaException("No hay mascotas en la base de datos");
        }
    }

    public void mascotasqty() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command, 1);
        ArrayList<ContadorMascotasTO> c = md.getQtyByPropietario();
        for(ContadorMascotasTO contador : c){
            System.out.println(contador);
        }
    }

    public void eliminarPropietario() throws CommandException,SQLException{
        vl.valComLength(command,2);
        String nombre = command[1];
        md.delPropietario(nombre);
    }

    public void eliminarMascota() throws CommandException,SQLException{
        vl.valComLength(command,2);
        String nombre = command[1];
        md.delMascota(nombre);
    }
}
