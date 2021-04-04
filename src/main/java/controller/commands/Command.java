package controller.commands;

import model.OperationStatus;
import transport.Request;
import transport.Response;

import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public abstract class Command {
    public Library library;
    public final String[] args;
    public Response response;


    public Command(Library library, String[] args) {
        this.library = library;
        this.args = args;
        this.response = new Response(OperationStatus.EMPTY_RESPONSE.getCode());
    }

    public abstract Response execute() throws ParserConfigurationException, SAXException, IOException;
}
