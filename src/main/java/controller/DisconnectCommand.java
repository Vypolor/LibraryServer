package controller;

import TransportObjects.Response;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DisconnectCommand extends Command{
    public DisconnectCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(700);
        return response;
    }
}
