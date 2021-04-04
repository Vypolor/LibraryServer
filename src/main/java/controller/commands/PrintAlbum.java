package controller.commands;

import exceptions.EntityOutOfLibraryException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PrintAlbum extends Command{
    public PrintAlbum(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String albumName = args[1];

        try {
            response.setAnswer(library.getSingerByName(singerName).getAlbumByName(albumName).toString());
        } catch (EntityOutOfLibraryException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
            return response;
        }
        response.setCode(OperationStatus.PRINT_TRACKS.getCode());
        return response;
    }
}
