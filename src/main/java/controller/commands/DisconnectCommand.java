package controller.commands;

import controller.commands.Command;
import model.OperationStatus;
import transport.Response;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DisconnectCommand extends Command {
    public DisconnectCommand(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        response.setCode(OperationStatus.DISCONNECT.getCode());
        return response;
    }
}
