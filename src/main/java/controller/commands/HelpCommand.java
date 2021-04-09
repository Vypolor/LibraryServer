package controller.commands;

import controller.commands.Command;
import model.OperationStatus;
import transport.Response;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class HelpCommand extends Command {

    public HelpCommand(Library library, String[] args) {
        super(library, args);
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
                            "     ADDING" +
                            "\n---------------\n" +
                        "Add a track: /add -track \"singer name\"\"album name\"\"track name\"\"track length\" \n"+
                        "Add a album: /add -album \"singer name\"\"album name\"  \n"+
                        "Add a singer:/add -singer \"singer name\"\n"+
                                "\n---------------\n" +
                                "     DELETING" +
                                "\n---------------\n" +
                        "Delete a track: /delete -track \"singer name\"\"album name\"\"track name\" \n"+
                        "Delete a album: /delete -album \"singer name\"\"album name\"\n"+
                        "Delete a singer:/delete -singer \"singer name\"\n"+
                                "\n---------------\n" +
                                "     EDITING" +
                                "\n---------------\n" +
                        "Edit a track:  /edit -track \"singer name\"\"album name\"\"track name\"\"new track name\"\"new track length\"\n"+
                                "-------------------------------------------------------------------------------\n" +
                                "If you want the duration of the track to remain unchanged. Use a command like\n" +
                                "Edit a track:  /edit -track \"singer name\"\"album name\"\"track name\"\"new track name\" \n" +
                        "Edit a album:  /edit -album \"singer name\"\"album name\"\"new album name\" \n"+
                        "Edit a singer: /edit -singer \"singer name\"\"new singer name\"\n"+
                                "\n---------------\n" +
                                "     SAVING" +
                                "\n---------------\n" +
                                "use \"//\" to split the directory\n" +
                        "Save library to xml: /save \"path\"\n"+
                                "\n---------------\n" +
                                "     LOADING" +
                                "\n---------------\n" +
                                "use \"//\" to split the directory\n" +
                        "Load library from xml:            /load \"path to xml file\"\n"+
                                "Load library from xml with merge: /mergeload \"path to xml file\"\n" +
                                "\n---------------\n" +
                                "    SEARCHING" +
                                "\n---------------\n" +
                                "Search tracks in library: /search \"search track name\"\n" +
                                "Search singers tracks:    /search \"singer name\"\"search track name\"\n" +
                                "Search tracks in album:   /search \"singer name\"\"album name\"\"search track name\"\n" +
                                "\n---------------\n" +
                                "    PRINTING" +
                                "\n---------------\n" +
                        "Print all library:             /get\"-singer\"\n"+
                        "Print all albums by an singer: /get\"-album\"\"singer name\"\n"+
                        "Print all albums by an singer: /get\"-track\"\"singer name\"\"album name\"\n";

        response.setAnswer(help);
        response.setCode(OperationStatus.COMPLETE.getCode());
        return response;
    }

}
