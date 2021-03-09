package TransportObjects;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Response implements Serializable {

    private int code;
    private Set<String> args = new HashSet<>();

    public Response(){}

    public Response(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Set<String> getArgs() {
        return args;
    }

    public void setArgs(Set<String> args) {
        this.args = args;
    }

}
