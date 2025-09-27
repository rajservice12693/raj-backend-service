package com.raj.jewellers.exception;

import com.raj.jewellers.enums.HttpStatusEnum;

public class CustomException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final HttpStatusEnum errorCode;

    public CustomException(final HttpStatusEnum errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(final HttpStatusEnum errorCode,
            final String detailedMsg) {
        super(detailedMsg);
        this.errorCode = errorCode;
    }

    public HttpStatusEnum getErrorCode() {
        return this.errorCode;
    }
}
