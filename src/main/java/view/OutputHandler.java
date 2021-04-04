package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class OutputHandler {

    //private final String file = "src/main/java/codes.properties";
    //FileInputStream in;
    public OutputHandler() {
        //this.in = (FileInputStream) OutputHandler.class.getResourceAsStream("codes.properties");
    }



    public void errorHandler(int code, String command, String parameter, String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(OutputHandler.class.getResourceAsStream("/codes.properties"));
        String resultMessage = properties.getProperty(String.valueOf(code));
        System.out.println(resultMessage);
    }
}
