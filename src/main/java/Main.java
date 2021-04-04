import exceptions.DuplicateException;
import exceptions.NullArgumentException;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.xml.sax.SAXException;
import utils.DataConverter;
import view.OutputHandler;

import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Main {
    private static ArrayList<String> singers = new ArrayList<>();
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XMLStreamException, DuplicateException, NullArgumentException {

        String filename = "src/library.xml";

        Album backInBlack = new Album("Back in Black");
        backInBlack.addTrack("hellsBells", 230);
        backInBlack.addTrack("ShootToThrill", 243);
        backInBlack.addTrack("gtdab", 340);
        backInBlack.addTrack("lmpmliy", 180);
        backInBlack.addTrack("bInB", 120);


        Album razorEdge = new Album("The Razors Edge");
        razorEdge.addTrack("thunderstruck", 200);
        razorEdge.addTrack("fireYourGuns", 143);
        razorEdge.addTrack("moneyTalks", 128);
        razorEdge.addTrack("theRazorEdge", 132);

        Singer aCDc = new Singer("AC/DC");
        aCDc.addAlbum("Back in Black");
        aCDc.addAlbum("The Razors Edge");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Singer scorpions = new Singer("Scorpions");

        Album faceTheHeat = new Album("Face The Heat");

        faceTheHeat.addTrack("Alien Nation", 5_44);
        faceTheHeat.addTrack("No Pain No Gain", 3_55);
        faceTheHeat.addTrack("Someone To Touch", 4_29);
        faceTheHeat.addTrack("Under The Same Sun", 4_51);
        faceTheHeat.addTrack("Unholy Alliance", 5_17);

        scorpions.addAlbum("Face The Heat");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Singer joji = new Singer("Joji");

        Album nectar = new Album("Nectar");
        Album ballads1 = new Album("BALLADS 1");
        Album inTongues = new Album("In Tongues");
        joji.addAlbum("Nectar");
        joji.addAlbum("BALLADS 1");
        joji.addAlbum("In Tongues");

        nectar.addTrack("EW", 3_27);
        nectar.addTrack("MODUS", 3_27);
        nectar.addTrack("Tick Tock", 2_12);
        nectar.addTrack("Daylight", 2_43);
        nectar.addTrack("Upgrade", 1_29);
        nectar.addTrack("Gimmie Love", 3_34);
        nectar.addTrack("Run", 3_15);

        ballads1.addTrack("ATTENTION", 2_08);
        ballads1.addTrack("SLOW DANCING IN THE DARK", 3_29);
        ballads1.addTrack("TEST DRIVE", 2_59);

        inTongues.addTrack("Will He", 3_22);
        inTongues.addTrack("Pills", 3_07);
        inTongues.addTrack("Demons", 2_56);

        Library library = new Library();
        library.addSinger("AC/DC");
        library.addSinger("Scorpions");
        library.addSinger("Joji");

        DataConverter.convertLibraryToXml(library, filename);

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
        String string = "/edit";
        String result = "";
        Properties properties = new Properties();
        try {
            properties.load(OutputHandler.class.getResourceAsStream("/commands.properties"));
            String resultMessage = properties.getProperty(String.valueOf(string));
            System.out.println(resultMessage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(properties.getProperty(string));
        }
        String str = "-singer";
        String r = string+str;
        System.out.println(r);
    }

}

