package controller.commands;

import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;
import utils.DataConverter;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MergeLoadCommand extends Command{
    public MergeLoadCommand(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String path = args[0];
        try {
            DataConverter.fromXmlToObjectWithMerge(path);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        response.setCode(OperationStatus.MERGE_LOAD_COMPLETE.getCode());
        return response;
    }
}
