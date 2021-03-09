import TransportObjects.Response;
import controller.AddCommand;
import controller.GetCommand;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.hibernate.bytecode.enhance.internal.tracker.NoopCollectionTracker;
import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    private static ArrayList<String> singers = new ArrayList<>();
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        String filename = "src/library.xml";

        /*Track hellsBells = new Track("Hells Bells", 5_12);
        Track ShootToThrill = new Track("Shoot to Thrill", 5_17);
        Track gtdab = new Track("Giving the Dog a Bone", 3_31);
        Track lmpmliy = new Track("Let me put My Love Into You", 4_15);
        Track bInB = new Track("Back in Black", 4_15);

        Album backInBlack = new Album("Back in Black");
        backInBlack.addTrack(hellsBells);
        backInBlack.addTrack(ShootToThrill);
        backInBlack.addTrack(gtdab);
        backInBlack.addTrack(lmpmliy);
        backInBlack.addTrack(bInB);

        Track thunderstruck = new Track("Thunderstruck", 4_52);
        Track fireYourGuns = new Track("Fire Your Guns", 2_53);
        Track moneyTalks = new Track("Money Talks", 3_45);
        Track theRazorEdge = new Track("The Razors Edge", 4_22);

        Album razorEdge = new Album("The Razors Edge");
        razorEdge.addTrack(thunderstruck);
        razorEdge.addTrack(fireYourGuns);
        razorEdge.addTrack(moneyTalks);
        razorEdge.addTrack(theRazorEdge);

        Singer aCDc = new Singer("AC/DC");
        aCDc.addAlbum(backInBlack);
        aCDc.addAlbum(razorEdge);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Singer scorpions = new Singer("Scorpions");

        Album faceTheHeat = new Album("Face The Heat");

        Track alienNation = new Track("Alien Nation", 5_44);
        Track noPainNoGain = new Track("No Pain No Gain", 3_55);
        Track someoneToTouch = new Track("Someone To Touch", 4_29);
        Track underTheSameSun = new Track("Under The Same Sun", 4_51);
        Track unholyAlliance = new Track("Unholy Alliance", 5_17);

        faceTheHeat.addTrack(alienNation);
        faceTheHeat.addTrack(noPainNoGain);
        faceTheHeat.addTrack(someoneToTouch);
        faceTheHeat.addTrack(underTheSameSun);
        faceTheHeat.addTrack(unholyAlliance);

        scorpions.addAlbum(faceTheHeat);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Singer joji = new Singer("Joji");

        Album nectar = new Album("Nectar");
        Album ballads1 = new Album("BALLADS 1");
        Album inTongues = new Album("In Tongues");
        joji.addAlbum(nectar);
        joji.addAlbum(ballads1);
        joji.addAlbum(inTongues);

        Track ew = new Track("EW", 3_27);
        Track modus = new Track("MODUS", 3_27);
        Track tickTock = new Track("Tick Tock", 2_12);
        Track daylight = new Track("Daylight", 2_43);
        Track upgrade = new Track("Upgrade", 1_29);
        Track gimmieLove = new Track("Gimmie Love", 3_34);
        Track run = new Track("Run", 3_15);
        nectar.addTrack(ew);
        nectar.addTrack(modus);
        nectar.addTrack(tickTock);
        nectar.addTrack(daylight);
        nectar.addTrack(upgrade);
        nectar.addTrack(gimmieLove);
        nectar.addTrack(run);

        Track attention = new Track("ATTENTION", 2_08);
        Track slowDance = new Track("SLOW DANCING IN THE DARK", 3_29);
        Track testDrive = new Track("TEST DRIVE", 2_59);
        ballads1.addTrack(attention);
        ballads1.addTrack(slowDance);
        ballads1.addTrack(testDrive);

        Track willHe = new Track("Will He", 3_22);
        Track pills = new Track("Pills", 3_07);
        Track demons = new Track("Demons", 2_56);
        inTongues.addTrack(willHe);
        inTongues.addTrack(pills);
        inTongues.addTrack(demons);

        Library library = new Library("Library");
        library.addSinger(aCDc);
        library.addSinger(scorpions);
        library.addSinger(joji);

        Library.convertObjectToXml(library, filename);*/

        Library library1 = Library.fromXmlToObject(filename);
        System.out.println(library1.toString());
        /*String[] argument = null;
        GetCommand command = new GetCommand(library, "hello", argument);
        Response response = command.execute();*/



        //GetSingers
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");
        for(int i = 0; i < singersList.getLength(); ++i){
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();

            singers.add(attributes.getNamedItem("singer_name").getNodeValue());
        }

        for(String s: singers){
            System.out.println(s);
        }*/



        //GetAlbums
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        String element = "AC/DC";

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");
        for(int i = 0; i < singersList.getLength(); ++i){
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();
            if(singerElem.getNodeType() == Node.TEXT_NODE){
                continue;
            }
            if(attributes.getNamedItem("singer_name").getNodeValue().equals(element)){

                NodeList albumsList = singerElem.getChildNodes();

                for(int j = 0; j < albumsList.getLength(); ++j){

                    if(albumsList.item(j).getNodeType() == Node.TEXT_NODE){
                        continue;
                    }
                    else {
                        Node album = albumsList.item(j);

                        NamedNodeMap attributes2 = album.getAttributes();
                        System.out.println(attributes2.getNamedItem("album_name").getNodeValue());
                    }
                }
            }
        }*/

        //Get Tracks from Album
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        String element = "AC/DC";
        String elementalbum = "The Razors Edge";

        NodeList singersList = document.getDocumentElement().getElementsByTagName("singer");

        for(int i = 0; i < singersList.getLength(); ++i){

            if(singersList.item(i).getNodeType() == Node.TEXT_NODE){
                continue;
            }
            Node singerElem = singersList.item(i);

            NamedNodeMap attributes = singerElem.getAttributes();

            if(attributes.getNamedItem("singer_name").getNodeValue().equals(element)){

                NodeList albumsList = singerElem.getChildNodes();

                for(int j = 0; j < albumsList.getLength(); ++j){

                    if(albumsList.item(j).getNodeType() == Node.TEXT_NODE){
                        continue;
                    }
                    Node album = albumsList.item(j);

                    NamedNodeMap attributes2 = album.getAttributes();

                    if(attributes2.getNamedItem("album_name").getNodeValue().equals(elementalbum)){

                        NodeList trackList = album.getChildNodes();

                        for(int k = 0; k < trackList.getLength(); ++k)
                        {
                            if(trackList.item(k).getNodeType() == Node.TEXT_NODE)
                                continue;

                            Node track = trackList.item(k);

                            NamedNodeMap attributes3 = track.getAttributes();
                            System.out.println(attributes3.getNamedItem("track_name").getNodeValue());
                        }
                    }
                }
            }
        }*/
    }

}

