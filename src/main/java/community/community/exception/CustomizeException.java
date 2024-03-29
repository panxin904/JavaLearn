package community.community.exception;

/**
 * Created by Coder on 2023/10/10 & 9:23.
 **/
public class CustomizeException extends RuntimeException{
    private String message;
    private Integer code;

    public CustomizeException(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }
    public Integer getCode(){
        return code;
    }
}
