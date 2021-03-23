package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class OutputHandler {

    FileInputStream in = new FileInputStream("src/main/java/codes.properties");
    public OutputHandler() throws FileNotFoundException {
    }


    public void errorHandler(int code, String command, String parameter, String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(in);
        String resultMessage = properties.getProperty(String.valueOf(code));
        System.out.println(resultMessage);
    }
}
