package com.project.meetingscheduler.services.interfaces;

import com.project.meetingscheduler.exceptions.BookingException;
import com.project.meetingscheduler.requestObjects.BookRoomForMeetingRequest;
import com.project.meetingscheduler.requestObjects.TimeSlotRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingNameRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingRoomRequest;
import com.project.meetingscheduler.responseObjects.BookRoomForMeetingResponse;
import com.project.meetingscheduler.responseObjects.CancelMeetingResponse;

/**
 * Service Interface class for Meeting
 *
 * @author karthikeyan
 */
public interface MeetingServiceInterface {
    public BookRoomForMeetingResponse getMeetingById(Integer meetingId) throws BookingException;

    public BookRoomForMeetingResponse bookRoomForMeeting(BookRoomForMeetingRequest bookRoomForMeetingRequest) throws BookingException;

    public CancelMeetingResponse cancelMeeting(Integer meetingId) throws BookingException;

    public BookRoomForMeetingResponse updateMeetingTime(Integer meetingId, TimeSlotRequest newTimeSlot) throws BookingException;

    public BookRoomForMeetingResponse updateMeetingName(Integer meetingId, UpdateMeetingNameRequest updateMeetingNameRequest) throws BookingException;

    public BookRoomForMeetingResponse updateMeetingRoom(Integer meetingId, UpdateMeetingRoomRequest updateMeetingRoomRequest) throws BookingException;

    public BookRoomForMeetingResponse addEmployeeToMeeting(int meetingId, int employeeId) throws BookingException;

    public BookRoomForMeetingResponse removeEmployeeFromMeeting(int meetingId, int employeeId) throws BookingException;
}
