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

public class AddAlbum extends Command{
    public AddAlbum(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String albumName = args[1];
        try {
            library.getSingerByName(singerName).addAlbum(albumName);
            response.setCode(OperationStatus.ALBUM_ADDED.getCode());
        } catch (DuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        } catch (NullArgumentException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        } catch (EntityOutOfLibraryException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        }
        return response;
    }
}
