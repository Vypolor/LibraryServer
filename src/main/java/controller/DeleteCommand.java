package controller;

import TransportObjects.Response;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DeleteCommand extends Command{

    public DeleteCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(100);
        switch (parameter){
            case "-singer":
                return delSinger(args[0], response);
            case "-album":
                return delAlbum(args[0], args[1], response);
            case "-track":
                return delTrack(args[0], args[1], args[2], response);
            default:
                response.setCode(505);
        }
        return null;
    }

    private Response delTrack(String singerName, String albumName, String trackName, Response response){
        response.setCode(4);
        if(library.getSingerByName(singerName) == null) {
            response.setCode(230);
            return response;
        }

        if(library.getSingerByName(singerName).getAlbumByName(albumName) == null) {
            response.setCode(220);
            return response;
        }

        Track track = library.getSingerByName(singerName).getAlbumByName(albumName).getTrackByName(trackName);

        if(!library.getSingerByName(singerName).getAlbumByName(albumName).deleteTrack(track)) {
            response.setCode(210);
            return response;
        }

        return response;
    }

    private Response delAlbum(String singerName, String albumName, Response response){

        response.setCode(5);

        if(library.getSingerByName(singerName) == null) {
            response.setCode(230);
            return response;
        }

        Album album = library.getSingerByName(singerName).getAlbumByName(albumName);

        if(!library.getSingerByName(singerName).deleteAlbum(album)) {
            response.setCode(220);
            return response;
        }

        return response;
    }

    private Response delSinger(String singerName, Response response){

        Singer singer = new Singer(singerName);
        response.setCode(6);

        if(!library.deleteSinger(singer)) {
            response.setCode(230);
            return response;
        }

        return response;
    }
}
