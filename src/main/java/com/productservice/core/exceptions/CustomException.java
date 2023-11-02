package com.productservice.core.exceptions;

import com.productservice.dto.enums.ErrorCode;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class CustomException extends RuntimeException{

    private ErrorCode error;
    private Map<String,String> params = new HashMap<>();

    public CustomException(Throwable cause, ErrorCode code) {
        super(code.getMessage(), cause);
        this.error = code;
    }

    public CustomException(Throwable cause, ErrorCode code, Map<String,String> params) {
        this(cause,code);
        this.params = params;
    }

    public CustomException(ErrorCode code) {
        super(code.getMessage());
        this.error = code;
    }

    public CustomException(String message) {
        super(message);
        this.error = ErrorCode.INTERNAL;
    }


    public CustomException(ErrorCode code, Map<String,String> params) {
        this(code);
        this.params = params;
    }

    //errorcode and string message
    public CustomException(ErrorCode code, String message) {
        super(message);
        this.error = code;

    }
}
