package com.project.meetingscheduler.controllers;


import com.project.meetingscheduler.controllers.interfaces.MeetingControllerInterface;
import com.project.meetingscheduler.exceptions.BookingException;
import com.project.meetingscheduler.repositories.MeetingRepository;
import com.project.meetingscheduler.requestObjects.BookRoomForMeetingRequest;
import com.project.meetingscheduler.requestObjects.TimeSlotRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingNameRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingRoomRequest;
import com.project.meetingscheduler.responseObjects.BookRoomForMeetingResponse;
import com.project.meetingscheduler.responseObjects.CancelMeetingResponse;
import com.project.meetingscheduler.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class to handle meeting related endpoints
 * The class has methods to create meeting, view meeting, delete meeting, update meeting info like meeting time, meeting name and meeting room, adding or removing an employee from meeting.
 *
 * @author karthikeyan
 */

@RestController
@RequestMapping("/meeting")
public class MeetingController implements MeetingControllerInterface {

    @Autowired
    private MeetingRepository meetingRepository;
    private MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity<BookRoomForMeetingResponse> getMeeting(Integer meetingId) throws BookingException {
        return ResponseEntity.ok(meetingService.getMeetingById(meetingId));
    }

    @PostMapping("/book")
    public ResponseEntity<BookRoomForMeetingResponse> bookRoomForMeeting(BookRoomForMeetingRequest request) throws BookingException {
        return ResponseEntity.ok(meetingService.bookRoomForMeeting(request));
    }


    @DeleteMapping("/{meetingId}")
    public ResponseEntity<CancelMeetingResponse> cancelMeeting(int meetingId) throws BookingException {
        return ResponseEntity.ok(meetingService.cancelMeeting(meetingId));
    }

    @PatchMapping("/{meetingId}/time")
    public ResponseEntity<BookRoomForMeetingResponse> updateMeetingTime(int meetingId, TimeSlotRequest updateTimeSlotRequest) throws BookingException {
        return ResponseEntity.ok(meetingService.updateMeetingTime(meetingId, updateTimeSlotRequest));
    }

    @PatchMapping("/{meetingId}/name")
    public ResponseEntity<BookRoomForMeetingResponse> updateMeetingName(int meetingId, UpdateMeetingNameRequest updateMeetingNameRequest) throws BookingException {
        return ResponseEntity.ok(meetingService.updateMeetingName(meetingId, updateMeetingNameRequest));
    }

    @PatchMapping("/{meetingId}/room")
    public ResponseEntity<BookRoomForMeetingResponse> updateMeetingRoom(int meetingId, UpdateMeetingRoomRequest updateMeetingRoomRequest) throws BookingException {
        return ResponseEntity.ok(meetingService.updateMeetingRoom(meetingId, updateMeetingRoomRequest));
    }

    @PatchMapping("/{meetingId}/emp/{employeeId}")
    public ResponseEntity<BookRoomForMeetingResponse> addEmployeeToMeeting(int meetingId, int employeeId) throws BookingException {
        return ResponseEntity.ok(meetingService.addEmployeeToMeeting(meetingId, employeeId));
    }

    @DeleteMapping("/{meetingId}/emp/{employeeId}")
    public ResponseEntity<BookRoomForMeetingResponse> removeEmployeeFromMeeting(int meetingId, int employeeId) throws BookingException {
        return ResponseEntity.ok(meetingService.removeEmployeeFromMeeting(meetingId, employeeId));
    }
}
