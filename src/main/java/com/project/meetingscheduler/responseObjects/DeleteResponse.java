package com.project.meetingscheduler.responseObjects;

/**
 * Class for DeleteResponse
 *
 * @author karthikeyan
 */
public class DeleteResponse {
    private String message;
    private Integer statusCode;

    public DeleteResponse() {
    }

    public DeleteResponse(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

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
