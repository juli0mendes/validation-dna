package com.juli0mendes.validationdna.adapter.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 8944049575548466645L;

    private int statusCode;

    private String message;

    private List<ErrorDetailDto> details;

    public ErrorDto(int statusCode, String message, List<ErrorDetailDto> details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    public ErrorDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorDto() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ErrorDetailDto> getDetails() {
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        return this.details;
    }

    @Override
    public String toString() {
        return "ErrorDTO [statusCode=" + statusCode + ", message=" + message + ", details=" + details + "]";
    }
}
