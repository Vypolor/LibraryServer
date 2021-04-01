package model;

import utils.TimeParser;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement(name = "track")

@XmlType(propOrder = {"trackName", "length"})
public class Track implements Serializable {

    private String trackName;
    private long length = 0;

    public Track() {
    }

    public Track(String track_name, long length) {
        this.trackName = track_name;
        this.length = length;
    }

    @XmlAttribute(name = "track_name")
    public String getTrackName() {
        return trackName;
    }

    @XmlAttribute
    public long getLength() {
        return length;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setLength(long time) {
        this.length = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(trackName, track.trackName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackName);
    }

    @Override
    public String toString() {
        return TimeParser.parseLength(this.getTrackName(), this.getLength());
    }
}