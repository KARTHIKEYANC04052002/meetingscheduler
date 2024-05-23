package com.project.meetingscheduler.exceptions;

/**
 * Exception Class for booking exception
 *
 * @author karthikeyan
 */
public class BookingException extends Throwable {
    private String message;

    public BookingException() {
    }

    public BookingException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
