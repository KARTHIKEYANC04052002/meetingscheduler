package com.project.meetingscheduler.responseObjects;

/**
 * Class for CancelMeetingResponse
 *
 * @author karthikeyan
 */
public class CancelMeetingResponse {
    private String message;

    public CancelMeetingResponse() {
    }

    public CancelMeetingResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
