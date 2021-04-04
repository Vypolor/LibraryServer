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

public class AddTrack extends Command{
    public AddTrack(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String albumName = args[1];
        String trackName = args[2];
        long trackLength = 0;
        if(args[3] == null)
        {
            response.setCode(OperationStatus.MISSING_TRACK_LENGTH.getCode());
            return response;
        }
        else
        {
            trackLength = TimeParser.parseLengthFromString(args[3]);
        }
        try {
            library.getSingerByName(singerName).getAlbumByName(albumName).addTrack(trackName, trackLength);
            response.setCode(OperationStatus.TRACK_ADDED.getCode());
        } catch (DuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        } catch (NullArgumentException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        } catch (EntityOutOfLibraryException e) {
            response.setCode(e.getCode());
        }
        return response;
    }
}
