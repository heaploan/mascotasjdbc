package com.mycompany.mascotasjdbc.model.util;

import com.mycompany.mascotasjdbc.excepctions.CommandException;

public class Validations {
    //Comprueba la longitud del comando
    public void valComLength(String[] command, int expectedLength) throws CommandException{
        if(command.length != expectedLength){
            throw new CommandException(CommandException.WRONG_NUM_ARGS);
        }
    }

    //Comprueba un minimo y un máximo de la longitud del comando
    public void valComLength(String[] command, int min, int max) throws CommandException{
        if(command.length < min || command.length > max ) {
            throw new CommandException(CommandException.WRONG_NUM_ARGS);
        }
    }

    public void valNameLength(String command, int max) throws CommandException  {
        if(command.length() > max){
            throw new CommandException(CommandException.WRONG_NAME_LENGTH);
        }
    }

    public int valIntNum(String num) throws CommandException {
        try{
            return Integer.parseInt(num);
        } catch (NumberFormatException e){
                throw new CommandException(CommandException.WRONG_NUM_FORMAT);
        }
    }
}
