package exceptions;

public class EntityOutOfLibraryException extends Exception{

    private int code;

    public EntityOutOfLibraryException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
