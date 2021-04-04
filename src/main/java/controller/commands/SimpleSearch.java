package controller.commands;

import model.*;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleSearch extends Command{
    public SimpleSearch(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String search = args[0];
        Set<String> findResult = new HashSet<>();
        Track track = new Track(search, 0);
        for(Singer singer: library.getSingers()){
            for(Album album: singer.getAlbums()){
                if( album.getTracks().contains(track)){
                    findResult.add(singer.getSingerName() + " | " + album.getAlbumName() + " | " + track.getTrackName());
                }
            }
        }
        response.setArgs(findResult);
        response.setCode(OperationStatus.SEARCH_COMPLETE.getCode());
        return response;
    }
}
