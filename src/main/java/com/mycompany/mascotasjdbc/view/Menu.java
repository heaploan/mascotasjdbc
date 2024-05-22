package com.mycompany.mascotasjdbc.view;


import com.mycompany.mascotasjdbc.excepctions.CommandException;
import com.mycompany.mascotasjdbc.excepctions.MascotaException;

import java.sql.SQLException;

public class Menu {
    InputData input = new InputData();
    Options op = new Options();

    public void menu(){
        String option;
        do{
            String[] command = input.askStr().trim().split(" ");
            op.setCommand(command);
            option = command[0].toLowerCase();
            try{
                switch(option){
                    case "addowner":
                        op.agregarPropietario();
                        break;
                    case "addpet":
                        op.agregarMascota();
                        break;
                    case "petqty":
                        op.mascotasqty();
                        break;
                    case "ownerlist":
                        op.mostrarPropietarios();
                        break;
                    case "deleteowner":
                        op.eliminarPropietario();
                        break;
                    case "deletepet":
                        op.eliminarMascota();
                        break;
                    case "exit":
                        System.out.println("Saliendo...");
                        break;
                    default:
                        throw new CommandException(CommandException.WRONG_OPERATION);
                }
            } catch (CommandException | MascotaException e){
                System.out.println(e.getMessage());
            } catch (SQLException e){
                System.out.println("Error con la bbdd: " + e.getMessage());
            }
        } while(!option.equals("exit"));
    }
}
