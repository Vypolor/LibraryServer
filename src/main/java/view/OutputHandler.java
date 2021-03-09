package view;

import java.util.HashMap;
import java.util.Map;

public class OutputHandler {

    public OutputHandler() {
    }

    private final Map<Integer, String> executeResult = new HashMap<>();

    {
        //добаваить отчет об успешных операциях
        executeResult.put(0, "Successful!");

        executeResult.put(6, "Singers successfully transferred");
        executeResult.put(7, "Singer added successfully");
        executeResult.put(8, "Album added successfully");
        executeResult.put(9, "Track added successfully");

        executeResult.put(10, "Singer deleted");
        executeResult.put(11, "Album deleted");

        executeResult.put(110, "This track is already in the album");
        executeResult.put(120, "This album is already in the library");
        executeResult.put(130, "This artist is already in the library");

        executeResult.put(210, "The specified track is not in the library");
        executeResult.put(220, "The specified album is not in the library");
        executeResult.put(230, "The specified artist is not in the library");

        executeResult.put(310, "The track with the new name is already on the album");
        executeResult.put(320, "The artist already has an album with the new name");
        executeResult.put(330, "The artist with the specified new name is already in the library");

        executeResult.put(404, "Warning: attempt to create a duplicate");

        executeResult.put(500, "Invalid value of the key");
    }

    public void errorHandler(int code, String command, String parameter, String[] args){
        System.out.println(executeResult.get(code));
    }
}
