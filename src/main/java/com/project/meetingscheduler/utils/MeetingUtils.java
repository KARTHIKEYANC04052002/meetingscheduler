package com.project.meetingscheduler.utils;

import com.project.meetingscheduler.entities.Employee;
import com.project.meetingscheduler.entities.Meeting;
import com.project.meetingscheduler.entities.Room;
import com.project.meetingscheduler.entities.Team;
import com.project.meetingscheduler.repositories.EmployeeRepository;
import com.project.meetingscheduler.repositories.MeetingRepository;
import com.project.meetingscheduler.repositories.RoomRepository;
import com.project.meetingscheduler.requestObjects.TimeSlotRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utils package for Meeting related utilities
 *
 * @author karthikeyan
 */
@Service
public class MeetingUtils {
    public static MeetingRepository meetingRepository;
    public static EmployeeRepository employeeRepository;
    public static RoomRepository roomRepository;

    public MeetingUtils() {
    }

    @Autowired
    public MeetingUtils(MeetingRepository meetingRepository, EmployeeRepository employeeRepository, RoomRepository roomRepository) {
        this.meetingRepository = meetingRepository;
        this.employeeRepository = employeeRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Method to check the availability of the room at a given time slot
     *
     * @param timeSlotRequest {TimeSlotRequest}
     * @param roomId          {int}
     * @return
     */
    public static boolean isSlotAvailable(TimeSlotRequest timeSlotRequest, int roomId) {
        LocalDate date = timeSlotRequest.getDate();
        LocalTime startTime = timeSlotRequest.getStartTime();
        LocalTime endTime = timeSlotRequest.getEndTime();

        // Retrieve a list of all meetings for the given date
        List<Meeting> slots = meetingRepository.findAllByDate(date);

        if (slots.isEmpty()) {
            return true; // No meetings for the specified date, so the room is available.
        } else {
            // Check for overlapping meetings for the specified room and time slot
            List<Meeting> overlappingMeetings = slots.stream().filter(meeting -> meeting.getRoomList().stream().anyMatch(room -> room.getRoomId() == roomId) && !(startTime.isAfter(meeting.getEndTime()) || endTime.isBefore(meeting.getStartTime()))).collect(Collectors.toList());

            return overlappingMeetings.isEmpty();
        }
    }

    /**
     * Method to check the availability of the room at a given time slot, also check same meeting or not
     *
     * @param timeSlotRequest {TimeSlotRequest}
     * @param roomId          {int}
     * @param meetingId       {int}
     * @return
     */
    public static boolean isSlotAvailable(TimeSlotRequest timeSlotRequest, int roomId, int meetingId) {
        LocalDate date = timeSlotRequest.getDate();
        LocalTime startTime = timeSlotRequest.getStartTime();
        LocalTime endTime = timeSlotRequest.getEndTime();
        List<Meeting> overLappingMeetings;

        List<Meeting> slots = meetingRepository.findAllByDate(date);
        if (slots.isEmpty()) return true;
        else {
            overLappingMeetings = slots.stream().filter(meeting -> meeting.getRoomList().stream().anyMatch(room -> room.getRoomId() == roomId) && !(startTime.isAfter(meeting.getEndTime()) || endTime.isBefore(meeting.getStartTime()))).collect(Collectors.toList());
        }
        if (overLappingMeetings.isEmpty()) return true;
        else return overLappingMeetings.size() == 1 && overLappingMeetings.get(0).getMeetingId() == meetingId;
    }

    /**
     * Method to check the availability of the employee
     *
     * @param request    {TimeSlotRequest}
     * @param employeeId {int}
     * @return
     */
    public static boolean isEmployeeAvailable(TimeSlotRequest request, Integer employeeId) {
        LocalDate date = request.getDate();
        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();
        List<Meeting> meetingsBooked = meetingRepository.findAllByDate(date);
        Employee employee = employeeRepository.findById(employeeId).get();
        List<Team> teamsOfEmployee = employee.getTeamList();
        List<Meeting> meetingsOfEmployee;
        List<Meeting> overLappingMeetings;

        meetingsOfEmployee = meetingsBooked.stream().filter(meeting -> meeting.getTeamList().stream().anyMatch(team -> teamsOfEmployee.contains(team))).collect(Collectors.toList());
        meetingsOfEmployee.addAll(meetingsBooked.stream().filter(meeting -> meeting.getEmployeeList().contains(employee)).collect(Collectors.toList()));
        overLappingMeetings = meetingsOfEmployee.stream().filter(meeting -> !(startTime.isAfter(meeting.getEndTime()) || endTime.isBefore(meeting.getStartTime()))).collect(Collectors.toList());
        return overLappingMeetings.isEmpty();
    }

    /**
     * Method to get the nearest room available
     *
     * @param timeSlotRequest {TimeSlotRequest}
     * @param teamSize        {int}
     * @return {List<Integer>}
     */
    public static List<Integer> nextNearestRoom(TimeSlotRequest timeSlotRequest, int teamSize) {
        List<Room> roomList = roomRepository.findAll().stream().filter(room -> isSlotAvailable(timeSlotRequest, room.getRoomId())).collect(Collectors.toList());
        Integer totalSize = roomList.stream().mapToInt(Room::getRoomCapacity).sum();
        if (isRoomCapable(totalSize, teamSize)) {
            return roomList.stream().map(room -> room.getRoomId()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * Method to check whether the room capable
     *
     * @param roomCapacity {int}
     * @param teamSize     {int}
     * @return {boolean}
     */
    public static boolean isRoomCapable(int roomCapacity, int teamSize) {
        return teamSize <= roomCapacity;
    }

}
