package utils;

public class TimeParser {
    public static long parseLength(String length){
        String[] splitted = length.split(":");

        return Long.parseLong(splitted[0]) *3600
                + Long.parseLong(splitted[1]) * 60
                + Long.parseLong(splitted[2]);
    }
}
