package controller;

import TransportObjects.Response;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class EditCommand extends Command{

    public EditCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(100);
        switch (parameter){
            case "-singer":
                return editSinger(args[0], args[1], response);
            case "-album":
                return editAlbum(args[0], args[1], args[2], response);
            case "-track":
                return editTrack(args[0], args[1], args[2], args[3], response);
            default:
                response.setCode(500);
        }
        return response;
    }

    private Response editTrack(String singerName, String albumName, String oldSingerName, String newSingerName, Response response){

        if(library.getSingerByName(singerName) == null){
            response.setCode(230);
            return response;
        }

        if(library.getSingerByName(singerName).getAlbumByName(albumName) == null){
            response.setCode(220);
            return response;
        }

        if(library.getSingerByName(singerName).getAlbumByName(albumName).getTrackByName(oldSingerName) == null){
            response.setCode(210);
            return response;
        }

        if(library.getSingerByName(singerName).getAlbumByName(albumName).getTrackByName(newSingerName)!= null){
            response.setCode(110);
            return response;
        }

        library.getSingerByName(singerName).getAlbumByName(albumName).getTrackByName(oldSingerName).setTrack_name(newSingerName);
        response.setCode(7);
        return response;
    }

    private Response editAlbum(String singerName, String oldAlbumName, String newAlbumName, Response response){


        if(library.getSingerByName(singerName) == null){
            response.setCode(230);
            return response;
        }

        if(library.getSingerByName(singerName).getAlbumByName(oldAlbumName) == null){
            response.setCode(220);
            return response;
        }

        if(library.getSingerByName(singerName).getAlbumByName(newAlbumName) != null){
            response.setCode(120);
        }

        library.getSingerByName(singerName).getAlbumByName(oldAlbumName).setName(newAlbumName);
        response.setCode(8);
        return response;
    }

    private Response editSinger(String oldSingerName, String newSingerName, Response response){

        if(library.getSingerByName(oldSingerName) == null){
            response.setCode(230);
            return response;
        }

        if(library.getSingerByName(newSingerName) != null){
            response.setCode(130);
            return response;
        }

        library.getSingerByName(oldSingerName).setSinger_name(newSingerName);
        response.setCode(9);
        return response;
    }
}
