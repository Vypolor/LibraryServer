package controller;

import TransportObjects.Response;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class SaveCommand extends Command{
    public SaveCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(808);
        saveToFile(args[0],response);
        return response;
    }

    private Response saveToFile(String path, Response response) throws IOException, SAXException, ParserConfigurationException
    {
        Library.convertObjectToXml(library, path);
        response.setCode(505);
        return response;
    }
}
