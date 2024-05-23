package com.mycompany.mascotasjdbc.view;

// Local imports
import com.mycompany.mascotasjdbc.excepctions.CommandException;
import com.mycompany.mascotasjdbc.excepctions.MascotaException;
import com.mycompany.mascotasjdbc.model.ContadorMascotasTO;
import com.mycompany.mascotasjdbc.model.Mascota;
import com.mycompany.mascotasjdbc.model.Propietario;
import com.mycompany.mascotasjdbc.model.util.Validations;
import com.mycompany.mascotasjdbc.persistence.PerroDAO;
import com.mycompany.mascotasjdbc.persistence.PropietarioDAO;
import com.mycompany.mascotasjdbc.view.messages.Message;

// Java imports
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Options {
    private String[] command;
    private Validations vl = new Validations();
    private Message msg = new Message();
    private PerroDAO perD = new PerroDAO();
    private PropietarioDAO prD = new PropietarioDAO();
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
        prD.insertarPropietario(p);
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
        Mascota m = new Mascota(nombre,fecha,prD.getPropietario(propietario));
        perD.insertarMascota(m);
        System.out.println(msg.getMessage(Message.PET_SUCCESSFULLY_ADDED));
    }

    public void mostrarPropietarios() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command, 1);
        ArrayList<Propietario> p = prD.allPropietarios();
        for(Propietario propietario : p){
            System.out.println(propietario);
        }
        if(p.isEmpty()){
            throw new MascotaException("No hay propietarios en la base de datos");
        }
    }

    public void mostrarMascotas() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command, 1);
        ArrayList<Mascota> m = perD.allMascotas();
        for(Mascota mascota : m){
            System.out.println(mascota);
        }
        if(m.isEmpty()){
            throw new MascotaException("No hay mascotas en la base de datos");
        }
    }

    public void mascotasqty() throws CommandException, SQLException, MascotaException {
        vl.valComLength(command, 1);
        ArrayList<ContadorMascotasTO> c = prD.getQtyByPropietario();
        for(ContadorMascotasTO contador : c){
            System.out.println(contador);
        }
    }

    public void eliminarPropietario() throws CommandException,SQLException{
        vl.valComLength(command,2);
        String nombre = command[1];
        prD.delPropietario(nombre);
    }

    public void eliminarMascota() throws CommandException,SQLException{
        vl.valComLength(command,2);
        String nombre = command[1];
        perD.delMascota(nombre);
    }
}
