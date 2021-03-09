package model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "album")

@XmlType(propOrder = {"album_name", "tracks"})
public class Album implements Serializable {

    private String album_name;
    private Set<Track> tracks = new HashSet<>();

    public Album() {
    }

    public Album(String album_name) {

        this.album_name = album_name;
    }

    public Album(String album_name, HashSet<Track> tracks) {
        this.album_name = album_name;
        this.tracks = tracks;
    }

    public boolean addTrack(Track addTrack) {

        for(Track track: tracks)
            if(track.getTrack_name().equals(addTrack.getTrack_name()))
                return false;

        tracks.add(addTrack);
        return true;
    }

    public boolean editTrack(Track oldTrack, Track newTrack) {
        if (deleteTrack(oldTrack))
            return addTrack(newTrack);

        return false;
    }

    public boolean deleteTrack(Track delTrack){

        for(Track track: tracks)
            if(track.getTrack_name().equals(delTrack.getTrack_name())){
                tracks.remove(delTrack);
                return true;
            }

        return false;
    }

    @XmlAttribute(name = "album_name")
    public String getAlbum_name() {
        return album_name;
    }

    public void setName(String name) {
        this.album_name = name;
    }
    @XmlElement(name = "track")
    public Set<Track> getTracks() {
        return tracks;
    }

    public void setTracks(Set<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(album_name, album.album_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(album_name);
    }

    @Override
    public String toString() {
        StringBuilder tracksList = new StringBuilder("\n");

        for (Track track : tracks) {
            tracksList.append(track.toString());
        }

        return "\n\t=============================="+"\n\tmodel.Album Name: " + getAlbum_name()
                + tracksList.toString()
                + "\n";
    }

    public static Singer fromXmlToObject(String filePath) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Album.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Singer) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // сохраняем объект в XML файл
    public static void convertObjectToXml(Singer singer, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Album.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(singer, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
