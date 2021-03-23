package controller;

import TransportObjects.Response;
import model.Album;
import model.Library;
import model.Singer;
import model.Track;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelpCommand extends Command{

    public HelpCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(800);
        return printList(response);
    }

    private Response printList(Response response){
        String help =
                "\nthe possible commands:" +
                "\n---------------\n" +
                "Add a track: /add -track \"singer name\"\"album name\"\"track name\"\"track length\" \n"+
                "Add a album: /add -album \"singer name\"\"album name\"  \n"+
                "Add a singer:/add -singer \"singer name\"\n"+
                "Delete a track: /delete -track \"singer name\"\"album name\"\"track name\" \n"+
                "Delete a album: /delete -album \"singer name\"\"album name\"\n"+
                "Delete a singer:/delete -singer \"singer name\"\n"+
                "Edit a track: /edit -track \"singer name\"\"album name\"\"track name\"\"new track name\" \n"+
                "Edit a album: /edit -album \"singer name\"\"album name\"\"new album name\" \n"+
                "Edit a singer: /edit -singer \"singer name\"\"new singer name\"\n"+
                "Save library to xml: /save\"path\"\n"+
                "Load library from xml: /load\"path\"\n"+
                "Print all library: /get\"-singer\"\n"+
                "Print all albums by an singer: /get\"-album\"\"singer name\"\n"+
                "Print all albums by an singer: /get\"-track\"\"singer name\"\"album name\"\n";
        System.out.println(help);
        response.setAnswer(help);
        response.setCode(0);
        return response;
    }

}
