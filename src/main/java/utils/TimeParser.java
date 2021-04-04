package utils;

public class TimeParser {
    public static String parseLength(String trackName, long time){
        long now = time;
        String sHour = "";
        String sMinutes = "";
        String seconds = "";
        int hour = (int)time/3600;
        if(hour > 9){
            sHour = Integer.toString(hour);
        }
        else {
            sHour = "0" + hour;
        }
        now = time - (3600*hour);
        int minutes = (int)time/60;
        if(minutes > 9){
            sMinutes = Integer.toString(minutes);
        }
        else {
            sMinutes = "0" + minutes;
        }
        now = time - (60*minutes);
        if(now <= 9){
            seconds = "0" + now;
        }
        else
            seconds = Long.toString(now);
        return  "\t\t=========================="
                + "\n\t\tTrack Name: " + trackName
                + "\n\t\tTrack Length: " + sHour + ":" + sMinutes
                + ":" + seconds
                + "\n";
    }

    public static long parseLengthFromString(String length){
        String[] splitted = length.split(":");

        return Long.parseLong(splitted[0]) * 3600
                + Long.parseLong(splitted[1]) * 60
                + Long.parseLong(splitted[2]);

    }
}
