package controller;

import TransportObjects.Response;

import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public abstract class Command {
    public Library library;
    public final String parameter;
    public final String[] args;


    public Command(Library library, String parameter, String[] args) {
        this.library = library;
        this.parameter = parameter;
        this.args = args;
    }

    public abstract Response execute() throws ParserConfigurationException, SAXException, IOException;
}
