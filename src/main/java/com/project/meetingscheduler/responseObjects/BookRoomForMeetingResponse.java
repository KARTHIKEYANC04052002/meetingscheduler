package com.project.meetingscheduler.responseObjects;

import com.project.meetingscheduler.requestObjects.BookRoomForMeetingRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Class for BookRoomForMeetingResponse
 *
 * @author karthikeyan
 */
public class BookRoomForMeetingResponse extends BookRoomForMeetingRequest {
    private Integer meetingId;

    public BookRoomForMeetingResponse() {
        super();
    }

    public BookRoomForMeetingResponse(Integer meetingId, int bookedBy, LocalDate date, LocalTime startTime, LocalTime endTime, String meetingName, String description) {
        super(bookedBy, date, startTime, endTime, meetingName, description);
        this.meetingId = meetingId;
    }

    public BookRoomForMeetingResponse(Integer meetingId, int bookedBy, LocalDate date, LocalTime startTime, LocalTime endTime, String meetingName, String description, List<Integer> teamList, List<Integer> employeeList, List<Integer> roomList) {
        super(bookedBy, date, startTime, endTime, meetingName, description, teamList, employeeList, roomList);
        this.meetingId = meetingId;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }
}
