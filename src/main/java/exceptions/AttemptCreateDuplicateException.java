package exceptions;

public class AttemptCreateDuplicateException extends Exception{

    private int error_code;
    private final String msg = "";

    public AttemptCreateDuplicateException(int error_code) {
        this.error_code = error_code;
    }

    public int getError_code(){
        return error_code;
    }

}
