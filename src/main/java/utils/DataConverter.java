package utils;

import exceptions.DuplicateException;
import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataConverter {

    public static void convertLibraryToXml(Library library, String filePath){
        try {
            JAXBContext context = JAXBContext.newInstance(Library.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(library, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void fromXmlToObject(String filePath) throws JAXBException {
        Library.getInstance().setSingersInstance(readLibrary(filePath).getSingers());
    }

    public static void fromXmlToObjectWithMerge(String filePath) throws JAXBException {
        Library newInstance = readLibrary(filePath);

        Set<Singer> currentSingers = Library.getInstance().getSingers();
        for(Singer singer: newInstance.getSingers()){
            if(!currentSingers.contains(singer)){
                currentSingers.add(singer);
            }
            else {
                Set<Album> singerAlbums = null;
                try {
                    singerAlbums = Library.getInstance().getSingerByName(singer.getSingerName()).getAlbums();
                } catch (EntityOutOfLibraryException e) {
                    e.printStackTrace();
                }
                try {
                    for(Album album: newInstance.getSingerByName(singer.getSingerName()).getAlbums()){
                        if(!singerAlbums.contains(album.getAlbumName())){
                            singerAlbums.add(album);
                        }
                        else {
                            Set<Track> albumTracks = Library.getInstance().getSingerByName(singer.getSingerName())
                                    .getAlbumByName(album.getAlbumName()).getTracks();
                            for(Track track: newInstance.getSingerByName(singer.getSingerName())
                                    .getAlbumByName(album.getAlbumName())
                                    .getTracks()){
                                if(!albumTracks.contains(track.getTrackName())){
                                    albumTracks.add(track);
                                }
                            }
                        }
                    }
                } catch (EntityOutOfLibraryException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Library readLibrary(String filePath) throws JAXBException {
        File file = new File(filePath);

        JAXBContext context = JAXBContext.newInstance(Library.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Library) unmarshaller.unmarshal(file);
        /*Set<Singer> sin = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(filePath);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");
        for(int i = 0; i < singersList.getLength(); ++i){
            if(singersList.item(i).getNodeType() == Node.TEXT_NODE)
                continue;
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();
            Singer sINGER = new Singer(attributes.getNamedItem("singer_name").getNodeValue());

            NodeList albums = singerElem.getChildNodes();
            Set<Album> albms = new HashSet<>();

            for(int j = 0; j < albums.getLength(); ++j){
                Node albumElem = albums.item(j);
                if(albumElem.getNodeType() == Node.TEXT_NODE)
                    continue;
                //Node albumElem = albums.item(j);

                NamedNodeMap albumAttributes = albumElem.getAttributes();
                Node al = albumAttributes.getNamedItem("album_name");

                Album album = new Album(al.getNodeValue());

                NodeList tracks = albumElem.getChildNodes();
                for(int k = 0; k < tracks.getLength(); ++k){
                    Node trackElem = tracks.item(k);
                    if(trackElem.getNodeType() == Node.TEXT_NODE)
                        continue;

                    NamedNodeMap tracksAttributes = trackElem.getAttributes();

                    Node tr = tracksAttributes.getNamedItem("track_name");
                    Node le = tracksAttributes.getNamedItem("length");
                    try {
                        album.addTrack(tr.getNodeValue(), Long.parseLong(le.getNodeValue()));
                    } catch (NullArgumentException e) {
                        e.printStackTrace();
                    } catch (DuplicateException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    sINGER.addAlbum(album.getAlbumName());
                } catch (NullArgumentException e) {
                    e.printStackTrace();
                } catch (DuplicateException e) {
                    e.printStackTrace();
                }
            }
            sin.add(sINGER);
        }
        Library.getInstance().setSingersInstance(sin);
        return Library.getInstance();*/
    }
}
