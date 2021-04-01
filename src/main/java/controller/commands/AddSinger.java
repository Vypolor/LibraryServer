package controller.commands;

import exceptions.AttemptCreateDuplicateException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class AddSinger extends AddCommand{
    public AddSinger(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        try {
            library.addSinger(singerName);
            response.setCode(OperationStatus.SINGER_ADDED.getCode());
        } catch (AttemptCreateDuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        } catch (NullArgumentException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        }
        return response;
    }
}
