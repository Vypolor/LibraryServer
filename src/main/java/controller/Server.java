package controller;

import model.Library;
import org.xml.sax.SAXException;
import transport.Request;
import transport.Response;

import javax.xml.parsers.ParserConfigurationException;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

class Server extends Thread {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Library library;
    private int id;

    public Server(Socket socket, int userId) throws IOException {
        this.socket = socket;
        oos = new ObjectOutputStream(
                socket.getOutputStream());

        ois = new ObjectInputStream(
                socket.getInputStream());
        id = userId;
    }

    public void run() {
        try {
            int code = 0;
            do {
                try {
                    Request request = (Request) ois.readObject();
                    System.out.println("================================");
                    System.out.println("User #" + id + ":");
                    InputHandler inputHandler = new InputHandler(request);
                    Response response = inputHandler.performRequest();
                    code = response.getCode();
                    oos.writeObject(response);
                    oos.flush();
                } catch (EOFException exception) {
                    break;
                }
            } while (code != 700);
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
            System.err.println("ERROR: INVALID SHUTDOWN" + "USER #" + id);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
