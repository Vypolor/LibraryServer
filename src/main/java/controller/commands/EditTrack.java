package controller.commands;

import exceptions.DuplicateException;
import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;
import utils.TimeParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EditTrack extends Command{
    public EditTrack(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String albumName = args[1];
        String trackName = args[2];
        String newTrackName = args[3];
        long length = 0;
        if(args[4] == null)
        {
            try {
                length = library.getSingerByName(singerName)
                        .getAlbumByName(albumName).getTrackByName(trackName).getLength();
            } catch (EntityOutOfLibraryException e) {
                response.setCode(e.getCode());
                return response;
            }
        }
        else
        {
            length = TimeParser.parseLengthFromString(args[4]);
        }

        try {
            library.getSingerByName(singerName).getAlbumByName(albumName).editTrack(trackName, newTrackName, length);
            response.setCode(OperationStatus.TRACK_EDITED.getCode());
        } catch (EntityOutOfLibraryException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        } catch (NullArgumentException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        } catch (DuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        }
        return response;
    }
}
