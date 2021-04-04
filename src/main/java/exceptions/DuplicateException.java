package exceptions;

public class DuplicateException extends Exception{

    private int error_code;
    private final String msg = "";

    public DuplicateException(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code(){
        return error_code;
    }

}
