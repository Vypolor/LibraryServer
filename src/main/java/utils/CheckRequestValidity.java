package utils;

import controller.commands.Command;
import model.OperationStatus;
import transport.Request;
import view.OutputHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class CheckRequestValidity {

    public static OperationStatus checkCommand(String command, String parameter){
        String result = "";
        Properties properties = new Properties();
        try {
            properties.load(OutputHandler.class.getResourceAsStream("/commands.properties"));
            result = properties.getProperty(String.valueOf(command));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(result == null){
            return OperationStatus.INVALID_COMMAND;
        }

        if(parameter != null) {
            if (parameter.equals("-singer") || parameter.equals("-album") || parameter.equals("-track")) {
                return OperationStatus.COMPLETE;
            } else {
                return OperationStatus.INVALID_KEY_VALUE;
            }
        }
        if(!command.equals("/save") && !command.equals("/load")
                && !command.equals("/help")
                && !command.equals("/disconnect")
                && !command.equals("/mergeload"))
            return OperationStatus.NULL_KEY_IN_REQUEST;
        else
            return OperationStatus.COMPLETE;
    }

    public static OperationStatus checkPathValidity(String path){
        String[] splitted = path.split("\\.");
        if(splitted[1].equals("xml")){
            return OperationStatus.COMPLETE;
        }
        else
            return OperationStatus.INCORRECT_FILE_RESOLUTION;
    }
}
