package model;

import TransportObjects.Response;
import controller.GetCommand;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
@XmlType(propOrder = {"name", "singers"})
public class Library implements Serializable {
    private static Library instance;

    private String name;
    private Set<Singer> singers = new HashSet<>();

    public Library() {
    }

    public Library(String name){
        this.name = name;
    }

    private Library(String name, Set<Singer> singers) {
        this.name = name;
        this.singers = singers;
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }

        return instance;
    }

    public static void setAll(String name, Set<Singer> singers) {
        getInstance().setName(name);
        getInstance().setSingers(singers);
    }

    public boolean addSinger(Singer addSinger) {

        for(Singer singer: singers)
            if(singer.getSinger_name().equals(addSinger.getSinger_name()))
                return false;

        singers.add(addSinger);
        return true;
    }

    public boolean editSinger(Singer oldSinger, Singer newSinger) {
        if (deleteSinger(oldSinger))
            return addSinger(newSinger);

        return false;
    }

    public boolean deleteSinger(Singer delSinger){
        for(Singer singer: singers)
            if(singer.getSinger_name().equals(delSinger.getSinger_name())){
                singers.remove(delSinger);
                return true;
            }

        return false;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "singer")
    public Set<Singer> getSingers() {
        return singers;
    }

    public Singer getSingerByName(String singerName){
        for(Singer singer : getSingers()){
            if(singer.getSinger_name().equals(singerName))
                return singer;
        }
        return null;
    }

    public void setSingers(Set<Singer> singers) {
        this.singers = singers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(name, library.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        StringBuilder singersList = new StringBuilder("model.Library " + getName() + ": \n");

        for (Singer singer : singers) {
            singersList.append(singer.toString());
        }

        return singersList.toString();
    }

    public static void convertObjectToXml(Library library, String filePath) throws ParserConfigurationException, IOException, SAXException {
        try {
            JAXBContext context = JAXBContext.newInstance(Library.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(library, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static Library fromXmlToObject(String filePath) throws ParserConfigurationException, IOException, SAXException {
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
                    Track track = new Track(tr.getNodeValue(),
                            Long.parseLong(le.getNodeValue()));
                    album.addTrack(track);

                }
                sINGER.addAlbum(album);
            }
            sin.add(sINGER);
        }
        Library library = new Library("library", sin);
        return library;
    }
}
