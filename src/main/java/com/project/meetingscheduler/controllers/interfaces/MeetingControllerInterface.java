package com.project.meetingscheduler.controllers.interfaces;

import com.project.meetingscheduler.exceptions.BookingException;
import com.project.meetingscheduler.requestObjects.BookRoomForMeetingRequest;
import com.project.meetingscheduler.requestObjects.TimeSlotRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingNameRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingRoomRequest;
import com.project.meetingscheduler.responseObjects.BookRoomForMeetingResponse;
import com.project.meetingscheduler.responseObjects.CancelMeetingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controller Interface for defining Meeting related methods
 *
 * @author karthikeyan
 */
public interface MeetingControllerInterface {
    public ResponseEntity<BookRoomForMeetingResponse> getMeeting(@PathVariable Integer meetingId) throws BookingException;

    public ResponseEntity<BookRoomForMeetingResponse> bookRoomForMeeting(@RequestBody BookRoomForMeetingRequest bookRoomForMeetingRequest) throws BookingException;

    public ResponseEntity<CancelMeetingResponse> cancelMeeting(@PathVariable int meetingId) throws BookingException;

    public ResponseEntity<BookRoomForMeetingResponse> updateMeetingTime(@PathVariable int meetingId, @RequestBody TimeSlotRequest newTimeSlot) throws BookingException;

    public ResponseEntity<BookRoomForMeetingResponse> updateMeetingName(@PathVariable int meetingId, @RequestBody UpdateMeetingNameRequest updateMeetingNameRequest) throws BookingException;

    public ResponseEntity<BookRoomForMeetingResponse> updateMeetingRoom(@PathVariable int meetingId, @RequestBody UpdateMeetingRoomRequest updateMeetingRoomRequest) throws BookingException;

    public ResponseEntity<BookRoomForMeetingResponse> addEmployeeToMeeting(@PathVariable int meetingId, @PathVariable int employeeId) throws BookingException;

    public ResponseEntity<BookRoomForMeetingResponse> removeEmployeeFromMeeting(@PathVariable int meetingId, @PathVariable int employeeId) throws BookingException;
}