package com.productservice.dto.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NOT_FOUND(1001, "Record not found", HttpStatus.NOT_FOUND),
    INTERNAL(1002, "Internal Service error", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(1003, "Bad Request", HttpStatus.BAD_REQUEST),
    SUPPLIER_NOT_FOUND(1004, "Supplier not found", HttpStatus.NOT_FOUND),
    ITEM_FOUND(1005, "Item not found", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1006, "ProductRecord not found", HttpStatus.NOT_FOUND),
    ;

    @Getter
    private final int code;

    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

    /**
     * Creates a new instance of {@link ErrorCode}.
     *
     * @param code
     * @param message
     */

    private ErrorCode(int code, String message) {
        this.message = message;
        this.code = code;
        this.status = HttpStatus.BAD_REQUEST;


    }

    /**
     * Creates a new instance of {@link ErrorCode}.
     *
     * @param code
     * @param message
     * @param status
     */

    private ErrorCode(int code, String message, HttpStatus status) {
        this.message = message;
        this.code = code;
        this.status = status;
    }

    @Override
    public String toString() {
        return this.code + " " + name();
    }
}
