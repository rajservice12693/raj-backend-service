package com.raj.jewellers.enums;

import org.springframework.http.HttpStatus;

public enum HttpStatusEnum {

    OK(HttpStatus.OK, "API called successfully !!!"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request !!!");

    private final String message;

    private final HttpStatus httpCode;

    HttpStatusEnum(final HttpStatus httpStatus, final String message) {
        this.message = message;
        this.httpCode = httpStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpCode;
    }

}
