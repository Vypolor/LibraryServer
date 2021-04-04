package controller.commands;

import exceptions.EntityOutOfLibraryException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class PrintSinger extends Command{
    public PrintSinger(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        try {
            response.setAnswer(library.getSingerByName(singerName).toString());
        } catch (EntityOutOfLibraryException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
            return response;
        }
        response.setCode(OperationStatus.PRINT_ALBUMS.getCode());
        return response;
    }
}
