package model;

public enum OperationStatus {

    EMPTY_RESPONSE(0),

    TRACK_ADDED(1),
    ALBUM_ADDED(2),
    SINGER_ADDED(3),

    TRACK_DELETED(4),
    ALBUM_DELETED(5),
    SINGER_DELETED(6),

    TRACK_EDITED(7),
    ALBUM_EDITED(8),
    SINGER_EDITED(9),

    PRINT_SINGERS(10),
    PRINT_ALBUMS(11),
    PRINT_TRACKS(12),

    TRACK_OUT_OF_ALBUM(210),
    ALBUM_OUT_OF_SINGER(220),
    SINGER_OUT_OF_LIBRARY(230),

    DUPLICATE_TRACK_IN_ALBUM(310),
    DUPLICATE_ALBUM_IN_SINGER(320),
    DUPLICATE_SINGER_IN_LIBRARY(330),

    INVALID_PATH(404),
    INVALID_KEY_VALUE(500),

    SAVE_COMPLETE(505),
    SEARCH_COMPLETE(506),
    LOAD_COMPLETE(507),

    DISCONNECT(700),
    INVALID_COMMAND(808),

    ARGUMENT_IS_NULL(1000);

    private int code;

    OperationStatus(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}