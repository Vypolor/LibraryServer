package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener {

    private ServerSocket serverSocket;

    public ConnectionListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() throws IOException {
        int id = 1;
        try{
            while (true){
                Socket socket = serverSocket.accept();
                try {
                    Server newConnection = new Server(socket, id);
                    System.out.println("User #" + id + " connected");
                    newConnection.start();
                    ++id;
                }
                catch (IOException e){
                    socket.close();
                }
            }
        }
        finally {
            serverSocket.close();
        }
    }
}
