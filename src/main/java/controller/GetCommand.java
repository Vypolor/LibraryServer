package controller;

import TransportObjects.Response;
import model.Library;
import model.Singer;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GetCommand extends Command{
    static File file = new File("src/library.xml");

    public GetCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {

        Response response = new Response(100);
        switch (parameter){
            case "singer":
                return getSingers(response);
            case "album":
                return getAlbums(response, args[0]);
            case "track":
                return getTracks(response, args[0], args[1]);
            default:
                return null;
        }
    }
    //изменить на protected
    public static Response getTracks(Response response, String singerName, String albumName) throws ParserConfigurationException, IOException, SAXException {
        Set<String> tracks = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");

        for(int i = 0; i < singersList.getLength(); ++i){

            if(singersList.item(i).getNodeType() == Node.TEXT_NODE){
                continue;
            }
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();

            if(attributes.getNamedItem("singer_name").getNodeValue().equals(singerName)){

                NodeList albumsList = singerElem.getChildNodes();

                for(int j = 0; j < albumsList.getLength(); ++j){

                    if(albumsList.item(j).getNodeType() == Node.TEXT_NODE){
                        continue;
                    }
                    Node album = albumsList.item(j);

                    NamedNodeMap albumAttributes = album.getAttributes();

                    if(albumAttributes.getNamedItem("album_name").getNodeValue().equals(albumName)){

                        NodeList trackList = album.getChildNodes();

                        for(int k = 0; k < trackList.getLength(); ++k)
                        {
                            if(trackList.item(k).getNodeType() == Node.TEXT_NODE)
                                continue;

                            Node track = trackList.item(k);

                            NamedNodeMap trackAttributes = track.getAttributes();
                            tracks.add(trackAttributes.getNamedItem("track_name").getNodeValue());
                        }
                    }
                }
            }
        }
        response.setArgs(tracks);
        response.setCode(100);

        return response;
    }

    //изменить на protected
    public static Response getAlbums(Response response, String singerName) throws ParserConfigurationException, IOException, SAXException {
        Set<String> albums = new HashSet<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");
        for(int i = 0; i < singersList.getLength(); ++i){

            if(singersList.item(i).getNodeType() == Node.TEXT_NODE){
                continue;
            }
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();

            if(attributes.getNamedItem("singer_name").getNodeValue().equals(singerName)){

                NodeList albumsList = singerElem.getChildNodes();

                for(int j = 0; j < albumsList.getLength(); ++j){

                    if(albumsList.item(j).getNodeType() == Node.TEXT_NODE){
                        continue;
                    }
                    else {
                        Node album = albumsList.item(j);

                        NamedNodeMap albumAttributes = album.getAttributes();
                        albums.add(albumAttributes.getNamedItem("album_name").getNodeValue());
                    }
                }
            }
        }
        response.setArgs(albums);
        response.setCode(100);

        return response;
    }

    //изменить на protected
    //getting all singers name from xml
    //добавить code в Response
    public static Response getSingers(Response response) throws ParserConfigurationException, SAXException, IOException {

        Set<String> singers = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");
        for(int i = 0; i < singersList.getLength(); ++i){
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();

            singers.add(attributes.getNamedItem("singer_name").getNodeValue());
        }
        response.setArgs(singers);
        //изменить для отчета
        response.setCode(6);
        
        return response;
    }
}
