package controller.commands;

import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DeleteAlbum extends Command{
    public DeleteAlbum(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String albumName = args[1];
        try {
            library.getSingerByName(singerName).deleteAlbum(albumName);
            response.setCode(OperationStatus.ALBUM_DELETED.getCode());
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
