package controller;

import TransportObjects.Response;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.xml.sax.SAXException;
import utils.TimeParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class AddCommand extends Command{

    public AddCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(100);
        switch (parameter){
            case "-singer":
                return addSinger(args[0], response);
            case "-album":
                return addAlbum(args[0], args[1], response);
            case "-track":
                return addTrack(args[0], args[1], args[2], args[3], response);
            default:
                response.setCode(500);
        }
        return response;
    }

    private Response addTrack(String singerName, String albumName, String newTrack, String length, Response response){
        /*Track track = new Track(newTrack, length.length());

        for(Singer singer: library.getSingers()){
            for(Album album : singer.getAlbums()){
                if(album.addTrack(track) == true){
                    Library.convertObjectToXml(library, path);
                    //response = GetCommand.getTracks(response, singerName, albumName);
                    response.setCode(9);
                }
                else {
                    response.setCode(404);
                }
            }
        }
        return response;*/
        if (library.getSingerByName(singerName) == null) {
            response.setCode(230);
            return response;
        }


        if (library.getSingerByName(singerName).getAlbumByName(albumName) == null) {
            response.setCode(220);
            return response;
        }

        Track track = new Track(newTrack, TimeParser.parseLength(length));
        response.setCode(1);
        if (!library.getSingerByName(singerName).getAlbumByName(albumName).addTrack(track)) {
            response.setCode(110);
            return response;
        }
        return response;
    }

    private Response addAlbum(String singerName, String newAlbumName, Response response){

        Album album = new Album(newAlbumName);
        response.setCode(2);

        if(library.getSingerByName(singerName) == null) {
            response.setCode(230);
            return response;
        }

        if(!library.getSingerByName(singerName).addAlbum(album)) {
            response.setCode(120);
            return response;
        }
        return response;
    }

    //Add new Singer in library
    private Response addSinger(String singerName, Response response){
        response.setCode(3);
        Singer singer = new Singer(singerName);

        if(!library.addSinger(singer)) {
            response.setCode(330);
            return response;
        }

        return response;

        //==============================================================================================================
        //Добавление через DOM
        //DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        /*DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));

        NodeList nodes = document.getElementsByTagName("library");

        //Element singer = document.createElement("singer");
        //singer.setAttribute("singer_name", singerName);

        Node n = (Element) singer;
        //nodes.item(0).appendChild(singer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();*/
        /*try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(path)));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch(TransformerException e) {
            e.printStackTrace();
        }*/

        //response = GetCommand.getSingers(response);
    }
}
