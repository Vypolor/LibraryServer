package controller;

import TransportObjects.Response;
import model.Album;
import model.Library;
import model.Singer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DeleteCommand extends Command{
    private final String path = "src/library.xml";

    public DeleteCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(100);
        switch (parameter){
            case "singer":
                return delSinger(args[0], response);
            case "album":
                return delAlbum(args[0], args[1], response);
        }
        return null;
    }

    public Response delAlbum(String singerName, String albumName, Response response) throws IOException, SAXException, ParserConfigurationException {

        Album album = new Album(albumName);

        if(library.getSingerByName(singerName).deleteAlbum(album)){
            Library.convertObjectToXml(library, path);
            response = GetCommand.getAlbums(response, singerName);
            response.setCode(11);
        }
        else
            response.setCode(404);

        return response;
    }

    public Response delSinger(String singerName, Response response) throws IOException, SAXException, ParserConfigurationException {

        Singer singer = new Singer(singerName);

        if(library.deleteSinger(singer)){
            Library.convertObjectToXml(library, path);
            response = GetCommand.getSingers(response);
            response.setCode(10);
        }
        else {
            response.setCode(230);
        }
        return response;
    }
}
