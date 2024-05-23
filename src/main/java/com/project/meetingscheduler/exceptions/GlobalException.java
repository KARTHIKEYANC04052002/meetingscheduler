package com.project.meetingscheduler.exceptions;

/**
 * Exception Class for global exception
 *
 * @author karthikeyan
 */
public class GlobalException extends Throwable {
    private String message;
    private Integer statusCode;

    public GlobalException() {
    }

    public GlobalException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
