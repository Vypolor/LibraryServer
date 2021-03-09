package model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "track")

@XmlType(propOrder = {"track_name", "length"})
public class Track implements Serializable {

    private String track_name;
    private long length;

    public Track() {
    }

    public Track(String track_name, long length) {
        this.track_name = track_name;
        this.length = length;
    }

    @XmlAttribute(name = "track_name")
    public String getTrack_name() {
        return track_name;
    }

    @XmlAttribute
    public long getLength() {
        return length;
    }

    public void setTrack_name(String track_name) {
        this.track_name = track_name;
    }


    public void setLength(long time) {
        this.length = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(track_name, track.track_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(track_name);
    }

    @Override
    public String toString() {
        return  "\t\t=========================="
                + "\n\t\tmodel.Track Name: " + getTrack_name()
                + "\n\t\tmodel.Track Length: " + getLength()
                + "\n";
    }
}