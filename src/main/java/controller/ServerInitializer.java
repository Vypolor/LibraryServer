package controller;

import model.Library;

import java.io.*;
import java.net.ServerSocket;

public class ServerInitializer {

    private static Library library;
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {

        System.out.println("Server started!");
        library = Library.getInstance();

        ConnectionListener connectionListener = new ConnectionListener(new ServerSocket(PORT));
        connectionListener.start();
    }
}