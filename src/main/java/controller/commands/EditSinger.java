package controller.commands;

import exceptions.DuplicateException;
import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EditSinger extends Command{
    public EditSinger(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String newSingerName = args[1];

        try {
            library.editSinger(singerName, newSingerName);
            response.setCode(OperationStatus.SINGER_EDITED.getCode());
        } catch (NullArgumentException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        } catch (DuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        } catch (EntityOutOfLibraryException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        }
        return response;
    }
}
