package controller.commands;

import exceptions.EntityOutOfLibraryException;
import model.Album;
import model.Library;
import model.OperationStatus;
import model.Track;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleSearchSinger extends Command{
    public SimpleSearchSinger(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        String search = args[1];
        Set<String> findResult = new HashSet<>();
        Track track = new Track(search, 0);
        try {
            for(Album album: library.getSingerByName(singerName).getAlbums()){
                if( album.getTracks().contains(track)){
                    findResult.add(album.getAlbumName() + " | " + track.getTrackName());
                }
            }
        } catch (EntityOutOfLibraryException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
            return response;
        }
        response.setCode(OperationStatus.SEARCH_COMPLETE.getCode());
        return response;
    }
}
