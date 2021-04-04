package controller.commands;

import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PrintLibrary extends Command{
    public PrintLibrary(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        response.setAnswer(library.toString());
        response.setCode(OperationStatus.PRINT_SINGERS.getCode());

        return response;
    }
}
