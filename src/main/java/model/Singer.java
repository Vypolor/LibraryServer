package model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "singer")
@XmlType(propOrder = {"singer_name", "albums"})
public class Singer implements Serializable {

    private String singer_name;
    private Set<Album> albums = new HashSet<>();

    public Singer() {
    }

    public Singer(String singer_name) {
        this.singer_name = singer_name;
    }

    public Singer(String singer_name, Set<Album> albums) {
        this.singer_name = singer_name;
        this.albums = albums;
    }

    public boolean addAlbum(Album addAlbum) {

        for(Album album: albums)
            if(album.getAlbum_name().equals(addAlbum.getAlbum_name()))
                return false;

        albums.add(addAlbum);
        return true;
    }

    public boolean editAlbum(Album oldAlbum, Album newAlbum) {
        if (deleteAlbum(oldAlbum))
            return addAlbum(newAlbum);

        return false;
    }

    public boolean deleteAlbum(Album delAlbum) {

        for(Album album: albums)
            if(album.getAlbum_name().equals(delAlbum.getAlbum_name())){
                albums.remove(delAlbum);
                return true;
            }

        return false;
    }

    @XmlAttribute(name = "singer_name")
    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }

    @XmlElement(name = "album")
    public Set<Album> getAlbums() {
        return albums;
    }

    public Album getAlbumByName(String albumName){
        for(Album album: getAlbums())
            if(album.getAlbum_name().equals(albumName))
                return album;

        return null;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Singer singer = (Singer) o;
        return Objects.equals(singer_name, singer.singer_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(singer_name);
    }

    @Override
    public String toString() {
        StringBuilder albumsList = new StringBuilder();

        for (Album album : albums) {
            albumsList.append(album.toString());
        }

        return "=================================="+"\nSinger: " + getSinger_name()
                + albumsList.toString();
    }
}