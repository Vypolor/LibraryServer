package controller.commands;

import exceptions.AttemptCreateDuplicateException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class AddAlbum extends AddCommand{
    public AddAlbum(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        try {
            library.getSingerByName(singerName).addAlbum(albumName);
        } catch (AttemptCreateDuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        } catch (NullArgumentException e) {
            e.printStackTrace();
            response.setCode(e.getCode());
        }
        return response;
    }
}
