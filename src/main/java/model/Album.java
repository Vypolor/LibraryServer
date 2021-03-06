package model;

import exceptions.DuplicateException;
import exceptions.EntityOutOfLibraryException;
import exceptions.NullArgumentException;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "album")

@XmlType(propOrder = {"albumName", "tracks"})
public class Album implements Serializable {

    @XmlAttribute(name = "album_name")
    private String albumName;
    private Set<Track> tracks = new HashSet<>();

    public Album() {
    }

    public Album(String albumName) {

        this.albumName = albumName;
    }

    public Album(String albumName, HashSet<Track> tracks) {
        this.albumName = albumName;
        this.tracks = tracks;
    }

    public Track addTrack(String newTrack, long length) throws NullArgumentException, DuplicateException {

        if(newTrack == null || newTrack.equals("") || (length == 0))
        {
            throw new NullArgumentException();
        }

        Track track = new Track(newTrack, length);

        if(tracks.contains(track))
        {
            throw new DuplicateException(OperationStatus.DUPLICATE_TRACK_IN_ALBUM.getCode());
        }

        tracks.add(track);
        return track;
    }

    public Track editTrack(String oldTrack, String newTrack, long newTrackLength) throws EntityOutOfLibraryException
            , NullArgumentException, DuplicateException {

        Track track = new Track(newTrack, newTrackLength);
        if(tracks.contains(track)){
            throw new DuplicateException(OperationStatus.DUPLICATE_TRACK_IN_ALBUM.getCode());
        }
        else {
            getTrackByName(oldTrack).setTrackName(newTrack);
            getTrackByName(newTrack).setLength(newTrackLength);
            track = getTrackByName(newTrack);
        }
        return track;
    }

    public boolean deleteTrack(String trackName) throws NullArgumentException, EntityOutOfLibraryException {
        if(trackName == null || trackName.equals(""))
        {
            throw new NullArgumentException();
        }

        int length = 0;
        Track track = new Track(trackName, 0);

        if(tracks.contains(track))
        {
            tracks.remove(track);
            return true;
        }
        else
        {
            throw new EntityOutOfLibraryException(OperationStatus.TRACK_OUT_OF_ALBUM.getCode());
        }
    }


    public String getAlbumName() {
        return albumName;
    }

    public void setName(String name) {
        this.albumName = name;
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
        return Objects.equals(albumName, album.albumName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(albumName);
    }

    @Override
    public String toString() {
        StringBuilder tracksList = new StringBuilder("\n");

        for (Track track : tracks) {
            tracksList.append(track.toString());
        }

        return "\n\t=============================="+"\n\tAlbum Name: " + getAlbumName()
                + tracksList.toString()
                + "\n";
    }

    public Track getTrackByName(String trackName) throws EntityOutOfLibraryException {
        for(Track track: getTracks())
        {
            if (track.getTrackName().equals(trackName))
                return track;
        }

        throw new EntityOutOfLibraryException(OperationStatus.TRACK_OUT_OF_ALBUM.getCode());
    }

}
