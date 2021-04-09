package utils;

import exceptions.EntityOutOfLibraryException;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
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
    }
}
