package controller.commands;

import exceptions.DuplicateException;
import exceptions.NullArgumentException;
import model.Library;
import model.OperationStatus;
import org.xml.sax.SAXException;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class AddSinger extends Command{
    public AddSinger(Library library, String[] args) {
        super(library, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        String singerName = args[0];
        try {
            library.addSinger(singerName);
            response.setCode(OperationStatus.SINGER_ADDED.getCode());
        } catch (DuplicateException e) {
            //e.printStackTrace();
            response.setCode(e.getError_code());
        } catch (NullArgumentException e) {
            //e.printStackTrace();
            response.setCode(e.getCode());
        }
        return response;
    }
}
