import TransportObjects.Request;
import TransportObjects.Response;
import controller.InputHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class Server {

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000))
        {
            System.out.println("Server started!");
            while(true)
                try (
                        Socket socket = server.accept();

                        ObjectOutputStream oos =
                                new ObjectOutputStream(
                                        socket.getOutputStream());

                        ObjectInputStream ois =
                                new ObjectInputStream(
                                        socket.getInputStream());)
                {
                    Request request = (Request) ois.readObject();
                    InputHandler inputHandler = new InputHandler(request);
                    Response response = inputHandler.performRequest();
                    oos.writeObject(response);
                    oos.flush();
                    /*String request = reader.readLine();
                    String response = "Hello from server: " + request.length();
                    System.out.println(response);*/
                    //отправка библиотеки
                    /*writer.write(response);
                    writer.newLine();
                    writer.flush();*/

                } catch (IOException e){
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
