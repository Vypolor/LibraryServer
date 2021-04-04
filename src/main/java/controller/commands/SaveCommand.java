package controller.commands;

import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;
import utils.CheckRequestValidity;
import utils.DataConverter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SaveCommand extends Command{
    public SaveCommand(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String path = args[0];
        int validityStatus = CheckRequestValidity.checkPathValidity(path);
        if(validityStatus == OperationStatus.COMPLETE.getCode()){
            File file = new File(path);
            DataConverter.convertLibraryToXml(library, path);
            response.setCode(OperationStatus.SAVE_COMPLETE.getCode());
        }
        else {
            response.setCode(validityStatus);
        }
        return response;
    }
}
