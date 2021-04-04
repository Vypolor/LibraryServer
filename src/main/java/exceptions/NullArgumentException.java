package exceptions;

import model.OperationStatus;

public class NullArgumentException extends Exception{

    private final int code = OperationStatus.ARGUMENT_IS_NULL.getCode();

    public NullArgumentException() {
        super();
    }

    public int getCode() {
        return code;
    }
}
