import TransportObjects.Request;
import TransportObjects.Response;
import controller.InputHandler;
import model.Library;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

class Server extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Library library;
    private int id;

    public Server(Socket s, int userId) throws IOException {
        socket = s;
        oos = new ObjectOutputStream(
                socket.getOutputStream());

        ois = new ObjectInputStream(
                socket.getInputStream());
        library = Library.getInstance();
        id = userId;
        start();
    }

    public void run(){
        try{
            int code = 0;
            do {
                Request request = (Request) ois.readObject();
                System.out.println("================================");
                System.out.println("User #" + id +":");
                InputHandler inputHandler = new InputHandler(request);
                Response response = inputHandler.performRequest();
                code = response.getCode();
                oos.writeObject(response);
                oos.flush();
            }while (code != 700);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class MultiServer {
    static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        ServerSocket s = new ServerSocket(PORT);
        int id = 0;
        try{
            while (true){
                Socket socket = s.accept();
                try {
                    new Server(socket, id);
                    ++id;
                }
                catch (IOException e){
                    socket.close();
                }
            }
        }
        finally {
            s.close();
        }
    }
}

   /* public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(8000)) {
            new Thread(()->{

            }).start();
            System.out.println("Server started!");

            Library library = Library.getInstance();
            //Request request = Request.getInstance();
            Socket socket = server.accept();

            ObjectOutputStream oos =
                    new ObjectOutputStream(
                            socket.getOutputStream());

            ObjectInputStream ois =
                    new ObjectInputStream(
                            socket.getInputStream());
            while (true) {

                Request request = (Request) ois.readObject();

                InputHandler inputHandler = new InputHandler(request);
                Response response = inputHandler.performRequest();
                oos.writeObject(response);
                oos.flush();

            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/

