package controller.commands;

import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;
import utils.DataConverter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class SimpleLoadCommand extends Command{
    public SimpleLoadCommand(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String path = args[0];
        try {
            DataConverter.fromXmlToObject(path);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        response.setCode(OperationStatus.LOAD_COMPLETE.getCode());
        return response;
    }
}
