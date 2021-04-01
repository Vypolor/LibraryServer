package controller.commands;

import model.Library;
import utils.TimeParser;

public abstract class AddCommand extends Command{
    public String singerName;
    public String albumName;
    public String trackName;
    public long trackLength;

    public AddCommand(Library library, String parameter, String[] args) {
        super(library, parameter, args);
        singerName = args[0];
        albumName = args[1];
        trackName = args[2];
        trackLength = TimeParser.parseLengthFromString(args[3]);
    }
}
