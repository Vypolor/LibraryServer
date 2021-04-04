package controller;

import controller.commands.*;
import model.OperationStatus;
import transport.Request;
import transport.Response;
import model.Library;
import org.xml.sax.SAXException;
import utils.CheckRequestValidity;
import view.OutputHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class InputHandler {

    private Request request;
    private final Map<String, Class<? extends Command>> commands = new HashMap<>();
    {
        commands.put("/add-singer", AddSinger.class);
        commands.put("/add-album", AddAlbum.class);
        commands.put("/add-track", AddTrack.class);

        commands.put("/delete-singer", DeleteSinger.class);
        commands.put("/delete-album", DeleteAlbum.class);
        commands.put("/delete-track", DeleteTrack.class);

        commands.put("/edit-singer", EditSinger.class);
        commands.put("/edit-album", EditAlbum.class);
        commands.put("/edit-track", EditTrack.class);

        commands.put("/get-singer", PrintLibrary.class);
        commands.put("/get-album", PrintSinger.class);
        commands.put("/get-track", PrintAlbum.class);

        commands.put("/help", HelpCommand.class);
        commands.put("/disconnect", DisconnectCommand.class);
        commands.put("/load", LoadCommand.class);
        commands.put("/save", SaveCommand.class);
        commands.put("/search-singer", SimpleSearch.class);
        commands.put("/search-album", SimpleSearchSinger.class);
        commands.put("/search-track", SimpleSearch.class);
    }

    public InputHandler(Request request){
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public Response performRequest() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException {

        Response response = new Response();
        int validRequestStatus = CheckRequestValidity.checkCommand(request.getCommand(), request.getParameter());

        if(validRequestStatus == OperationStatus.COMPLETE.getCode())
        {
            String executeCommandName = "";
            if(request.getParameter() == null){
                executeCommandName = request.getCommand();
            }
            else {
                executeCommandName = request.getCommand()+request.getParameter();
            }
            response = findCommand(executeCommandName).execute();

            OutputHandler oh = new OutputHandler();
            sendCode(response.getCode(), oh);
        }
        else
        {
            response.setCode(validRequestStatus);
        }
        return response;

    }

    public void sendCode(int code, OutputHandler outputHandler) throws IOException {
        outputHandler.errorHandler(code, getRequest().getCommand(), getRequest().getParameter(), getRequest().getArgs());
    }

    private Command findCommand(String executeCommandName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return commands.get(executeCommandName)
                .getDeclaredConstructor(Library.class, String[].class)
                .newInstance(Library.getInstance(), getRequest().getArgs());
    }
}
