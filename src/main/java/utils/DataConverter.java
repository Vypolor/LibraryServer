package utils;

import model.Library;
import model.Singer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class DataConverter {

    public static void convertLibraryToXml(Library library, String filePath){
        try {
            JAXBContext context = JAXBContext.newInstance(Library.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            marshaller.marshal(library, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /*public static Library fromXmlToObject(String filePath){

        for(Singer singer : )
    }*/

    public static Library readLibrary(String filePath) throws JAXBException {
        File file = new File(filePath);

        JAXBContext context = JAXBContext.newInstance(Library.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (Library) unmarshaller.unmarshal(file);
    }
}
