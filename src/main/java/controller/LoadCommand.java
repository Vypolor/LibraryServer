package controller;

import TransportObjects.Response;
import model.Library;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class LoadCommand extends Command{
    public LoadCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
    }

    @Override
    public Response execute() throws ParserConfigurationException, SAXException, IOException {
        Response response = new Response(800);
        load(args[0], response);
        return response;
    }

    /*private Response loadPath(String path, Response response) throws IOException, SAXException, ParserConfigurationException {
        Library.instance = Library.fromXmlToObject(path);
        response.setCode(507);
        return response;
    }*/

    private Response load(String libString, Response response) throws ParserConfigurationException, IOException, SAXException {
        File file = new File("src/tmp.xml");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        try {
            Document document = docBuilder.parse(new InputSource(new StringReader(libString)));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source src = new DOMSource(document);
            Result dest = new StreamResult(file);
            transformer.transform(src, dest);
            Library.instance = Library.fromXmlToObject("src/tmp.xml");
            file.delete();
            System.out.println(library.toString());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        response.setCode(507);
        return response;
    }
}
