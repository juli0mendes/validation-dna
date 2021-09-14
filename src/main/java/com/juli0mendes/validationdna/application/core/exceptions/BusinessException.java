package com.juli0mendes.validationdna.application.core.exceptions;

import com.juli0mendes.validationdna.adapter.exceptions.ErrorDetailDto;

import java.util.List;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    private List<ErrorDetailDto> details;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, List<ErrorDetailDto> details) {
        super(message);
        this.details = details;
    }

    public BusinessException(List<ErrorDetailDto> details) {
        this.details = details;
    }

    public List<ErrorDetailDto> getDetails() {
        return details;
    }
}
