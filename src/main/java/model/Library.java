package model;

import exceptions.DuplicateException;
import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

@XmlRootElement(name = "library")
@XmlType(propOrder = {"singers"})
public class Library implements Serializable {

    private static Library instance;

    private Set<Singer> singers = new HashSet<>();

    public Library() {
    }

    /*public Library(String name){
        this.name = name;
    }*/

    private Library(Set<Singer> singers) {
        this.singers = singers;
    }

    public static synchronized Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }

        return instance;
    }

    public void setSingersInstance(Set<Singer> singers) {
        getInstance().setSingers(singers);
    }

    public Singer addSinger(String newSinger) throws NullArgumentException, DuplicateException {

        if(newSinger == null || newSinger.equals(""))
        {
            throw new NullArgumentException();
        }

        Singer singer = new Singer(newSinger);

        if(singers.contains(singer))
        {
            throw new DuplicateException(OperationStatus.DUPLICATE_SINGER_IN_LIBRARY.getCode());
        }

        singers.add(singer);
        return singer;
    }

    public Singer editSinger(String oldSinger, String newSinger) throws EntityOutOfLibraryException, NullArgumentException, DuplicateException {
        Singer singer = new Singer(newSinger);
        if(singers.contains(singer)){
            throw new DuplicateException(OperationStatus.DUPLICATE_SINGER_IN_LIBRARY.getCode());
        }
        else {
            getSingerByName(oldSinger).setSingerName(newSinger);
            singer = getSingerByName(newSinger);
        }
        return singer;
    }

    public boolean deleteSinger(String singerName) throws NullArgumentException, EntityOutOfLibraryException {
        if(singerName == null || singerName.equals(""))
        {
            throw new NullArgumentException();
        }

        Singer singer = new Singer(singerName);

        if(singers.contains(singer))
        {
            singers.remove(singer);
            return true;
        }
        else
        {
            throw new EntityOutOfLibraryException(OperationStatus.SINGER_OUT_OF_LIBRARY.getCode());
        }
    }

    @XmlElement(name = "singer")
    public Set<Singer> getSingers() {
        return singers;
    }

    public Singer getSingerByName(String singerName) throws EntityOutOfLibraryException {
        for(Singer singer : getSingers())
        {
            if(singer.getSingerName().equals(singerName))
                return singer;
        }
        throw new EntityOutOfLibraryException(OperationStatus.SINGER_OUT_OF_LIBRARY.getCode());
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    @Override
    public int hashCode() {
        return Objects.hash(singers);
    }

    @Override
    public String toString() {
        StringBuilder singersList = new StringBuilder("Library: \n");

        for (Singer singer : singers) {
            singersList.append(singer.toString());
        }

        return singersList.toString();
    }

    public static Library fromXmlToObject(String filePath) throws ParserConfigurationException, IOException, SAXException, DuplicateException, NullArgumentException {
        Set<Singer> sin = new HashSet<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(filePath);

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");
        for(int i = 0; i < singersList.getLength(); ++i){
            if(singersList.item(i).getNodeType() == Node.TEXT_NODE)
                continue;
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();
            Singer sINGER = new Singer(attributes.getNamedItem("singer_name").getNodeValue());

            NodeList albums = singerElem.getChildNodes();

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
                    /*Track track = new Track(tr.getNodeValue(),
                            Long.parseLong(le.getNodeValue()));*/
                    album.addTrack(tr.getNodeValue(), Long.parseLong(le.getNodeValue()));

                }
                sINGER.addAlbum(album.getAlbumName());
            }
            sin.add(sINGER);
        }
        Library library = new Library(sin);
        return library;
    }
}
