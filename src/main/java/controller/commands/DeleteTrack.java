package controller.commands;

import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DeleteTrack extends Command{
    public DeleteTrack(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String albumName = args[1];
        String trackName = args[2];
        try {
            library.getSingerByName(singerName).getAlbumByName(albumName).deleteTrack(trackName);
            response.setCode(OperationStatus.TRACK_DELETED.getCode());
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
