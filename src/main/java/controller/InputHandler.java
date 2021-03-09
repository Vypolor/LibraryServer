package controller;

import TransportObjects.Request;
import TransportObjects.Response;
import model.Library;
import org.xml.sax.SAXException;
import view.OutputHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


public class InputHandler {

    private Request request;
    private final Map<String, Class<? extends Command>> commands = new HashMap<>();
    private String path = "src/library.xml";
    {
        commands.put("get", GetCommand.class);
        commands.put("add", AddCommand.class);
        commands.put("delete", DeleteCommand.class);
    }

    public InputHandler(Request request){
        this.request = request;
    }

    public Request getRequest() {
        return request;
    }

    public Response performRequest() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException {
        Response response = invokeCommand().execute();

        OutputHandler oh = new OutputHandler();
        sendCode(response.getCode(), oh);

        return response;
    }

    public void sendCode(int code, OutputHandler outputHandler){
        outputHandler.errorHandler(code, getRequest().getCommand(), getRequest().getParameter(), getRequest().getArgs());
    }

    private Command invokeCommand() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, SAXException, ParserConfigurationException {
        return commands.get(getRequest().getCommand())
                .getDeclaredConstructor(Library.class, String.class, String[].class)
                .newInstance(Library.fromXmlToObject(path), getRequest().getParameter(), getRequest().getArgs());
    }
}
