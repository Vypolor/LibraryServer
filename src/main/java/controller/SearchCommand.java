package controller;

import controller.commands.Command;
import exceptions.EntityOutOfLibraryException;
import transport.Response;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCommand extends Command {
    public SearchCommand(Library library, String parameter, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(800);
        if(args[0].contains("*")|| args[0].contains("?"))
            return patternSearch(args[0], response);

        return response;
    }

    private Response patternSearch(String search, Response response){

        String strPattern = "^" + search
                .replaceAll("\\?", ".")
                .replaceAll("\\*", ".") + "$";

        Set<String> findResult = new HashSet<>();

        Pattern p = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        for(Singer singer: library.getSingers()){
            for (Album album: singer.getAlbums()){
                for(Track track: album.getTracks()){
                    matcher = p.matcher(track.getTrackName());
                    if(matcher.find()){
                        findResult.add(singer.getSingerName() + " | " + album.getAlbumName() + " | " + track.getTrackName());
                    }
                }
            }
        }
        response.setArgs(findResult);
        response.setCode(506);
        return response;
    }

    /*private Response simpleSearch(String search, Response response) throws EntityOutOfLibraryException {
        Set<String> findResult = new HashSet<>();
        Track track;
        for(Singer singer: library.getSingers()){
            for(Album album: singer.getAlbums()){
                if((track = album.getTrackByName(search)) != null){
                    findResult.add(singer.getSingerName() + " | " + album.getAlbumName() + " | " + track.getTrackName());
                }
            }
        }
        response.setArgs(findResult);
        response.setCode(506);
        return response;
    }*/

}
