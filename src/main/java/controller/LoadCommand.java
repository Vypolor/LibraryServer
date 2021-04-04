package controller;

import controller.commands.Command;
import transport.Response;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class LoadCommand extends Command {
    public LoadCommand(Library library, String parameter, String[] args) {
        super(library, args);
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
        File file = new File("src/library.xml");
        /*DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        try {
            Document document = docBuilder.parse(new InputSource(new StringReader(libString)));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source src = new DOMSource(document);
            Result dest = new StreamResult(file);
            transformer.transform(src, dest);
            Library.instance = Library.fromXmlToObject(libString);
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
        }*/
        response.setCode(507);
        //Library.instance = Library.fromXmlToObject("src/library.xml");
        return response;
    }
}
