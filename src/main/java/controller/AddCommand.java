package controller;

import TransportObjects.Response;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class AddCommand extends Command{
    private final String path = "src/library.xml";

    public AddCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(100);
        switch (parameter){
            case "singer":
                return addSinger(args[0], response);
            case "album":
                return addAlbum(args[0], args[1], response);
            case "track":
                return addTrack(args[0], args[1], args[2], args[3], response);
        }
        return null;
    }

    public Response addTrack(String singerName, String albumName, String newTrack, String length, Response response) throws IOException, SAXException, ParserConfigurationException {
        //добавить парсинг времени
        Track track = new Track(newTrack, length.length());

        for(Singer singer: library.getSingers()){
            for(Album album : singer.getAlbums()){
                if(album.addTrack(track) == true){
                    Library.convertObjectToXml(library, path);
                    response = GetCommand.getTracks(response, singerName, albumName);
                    response.setCode(9);
                }
                else {
                    response.setCode(404);
                }
            }
        }
        return response;
    }

    public Response addAlbum(String singerName, String newAlbumName, Response response) throws IOException, SAXException, ParserConfigurationException {

        Album album = new Album(newAlbumName);

        for(Singer singer: library.getSingers()){
            if(singer.getSinger_name().equals(singerName)) {
                if (singer.addAlbum(album) == true) {
                    Library.convertObjectToXml(library, path);
                    response = GetCommand.getAlbums(response, singerName);
                    response.setCode(8);
                }
                else
                    response.setCode(404);
            }
        }
        return response;
    }

    //Add new Singer in library
    public Response addSinger(String singerName, Response response) throws IOException, SAXException, ParserConfigurationException {

        Singer singer = new Singer(singerName);

        if(library.addSinger(singer)){
            Library.convertObjectToXml(library, path);
            response = GetCommand.getSingers(response);
            response.setCode(7);
        }
        else {
            response.setCode(404);
        }
        return response;

        //Добавление через DOM
        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));

        NodeList nodes = document.getElementsByTagName("library");

        Element singer = document.createElement("singer");
        singer.setAttribute("singer_name", singerName);

        Node n = (Element) singer;
        nodes.item(0).appendChild(singer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(document), new StreamResult(new File(path)));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch(TransformerException e) {
            e.printStackTrace();
        }

        response = GetCommand.getSingers(response);*/
    }
}
