package model;

import exceptions.AttemptCreateDuplicateException;
import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "singer")
@XmlType(propOrder = {"singerName", "albums"})
public class Singer implements Serializable {

    private String singerName;
    private Set<Album> albums = new HashSet<>();

    public Singer() {
    }

    public Singer(String singer_name) {
        this.singerName = singer_name;
    }

    public Singer(String singer_name, Set<Album> albums) {
        this.singerName = singer_name;
        this.albums = albums;
    }

    public Album addAlbum(String newAlbum) throws NullArgumentException, AttemptCreateDuplicateException {

        if(newAlbum == null || newAlbum.equals(""))
        {
            throw new NullArgumentException();
        }

        Album album = new Album(newAlbum);

        if(albums.contains(album))
        {
            throw new AttemptCreateDuplicateException(OperationStatus.DUPLICATE_ALBUM_IN_SINGER.getCode());
        }

        albums.add(album);
        return album;
    }

    public Album editAlbum(String oldAlbum, String newAlbum) throws EntityOutOfLibraryException, NullArgumentException, AttemptCreateDuplicateException {
        if (deleteAlbum(oldAlbum))
        {
            return addAlbum(newAlbum);
        }

        return null;
    }

    public boolean deleteAlbum(String albumName) throws NullArgumentException, EntityOutOfLibraryException {

        if(albumName == null || albumName.equals(""))
        {
            throw new NullArgumentException();
        }

        Album album = new Album(albumName);

        if(albums.contains(album))
        {
            albums.remove(album);
            return true;
        }
        else
        {
            throw new EntityOutOfLibraryException(OperationStatus.ALBUM_OUT_OF_SINGER.getCode());
        }
    }

    @XmlAttribute(name = "singer_name")
    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    @XmlElement(name = "album")
    public Set<Album> getAlbums() {
        return albums;
    }

    public Album getAlbumByName(String albumName){
        for(Album album: getAlbums())
            if(album.getAlbumName().equals(albumName))
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
        return Objects.equals(singerName, singer.singerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(singerName);
    }

    @Override
    public String toString() {
        StringBuilder albumsList = new StringBuilder();

        for (Album album : albums) {
            albumsList.append(album.toString());
        }

        return "=================================="+"\nSinger: " + getSingerName()
                + albumsList.toString();
    }
}