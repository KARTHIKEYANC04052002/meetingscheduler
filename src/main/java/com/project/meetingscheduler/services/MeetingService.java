package com.project.meetingscheduler.services;

import com.project.meetingscheduler.constants.constants;
import com.project.meetingscheduler.entities.Employee;
import com.project.meetingscheduler.entities.Meeting;
import com.project.meetingscheduler.entities.Room;
import com.project.meetingscheduler.entities.Team;
import com.project.meetingscheduler.exceptions.BookingException;
import com.project.meetingscheduler.repositories.EmployeeRepository;
import com.project.meetingscheduler.repositories.MeetingRepository;
import com.project.meetingscheduler.repositories.RoomRepository;
import com.project.meetingscheduler.repositories.TeamRepository;
import com.project.meetingscheduler.requestObjects.BookRoomForMeetingRequest;
import com.project.meetingscheduler.requestObjects.TimeSlotRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingNameRequest;
import com.project.meetingscheduler.requestObjects.UpdateMeetingRoomRequest;
import com.project.meetingscheduler.responseObjects.BookRoomForMeetingResponse;
import com.project.meetingscheduler.responseObjects.CancelMeetingResponse;
import com.project.meetingscheduler.services.interfaces.MeetingServiceInterface;
import com.project.meetingscheduler.utils.MeetingUtils;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for Meeting
 *
 * @author karthikeyan
 */
@Service
public class MeetingService implements MeetingServiceInterface {
    @Autowired
    EntityManager entityManager;
    private MeetingRepository meetingRepository;
    private EmployeeRepository employeeRepository;
    private RoomRepository roomRepository;
    private TeamRepository teamRepository;


    public MeetingService() {
    }

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, EmployeeRepository employeeRepository, RoomRepository roomRepository, TeamRepository teamRepository) {
        this.meetingRepository = meetingRepository;
        this.employeeRepository = employeeRepository;
        this.roomRepository = roomRepository;
        this.teamRepository = teamRepository;
    }

    public BookRoomForMeetingResponse getMeetingById(Integer meetingId) throws BookingException {
        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            List<Integer> employeeList = meeting.getEmployeeList().stream().map(employee -> employee.getEmployeeId()).toList();
            List<Integer> roomList = meeting.getRoomList().stream().map(room -> room.getRoomId()).toList();
            List<Integer> teamList = meeting.getTeamList().stream().map(team -> team.getTeamId()).toList();
            return new BookRoomForMeetingResponse(meeting.getMeetingId(), meeting.getBookedBy().getEmployeeId(), meeting.getDate(), meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingName(), meeting.getDescription(), teamList, employeeList, roomList);
        }
        throw new BookingException(constants.NO_MEETING_FOUND + meetingId);
    }

    /**
     * Method to book room for the meeting
     * The method checks all the possible conditions to create a meeting and creates the meeting if all the condition applies, otherwise throws the relavant error
     *
     * @param request {BookRoomForMeetingRequest}
     * @return {BookRoomForMeetingResponse}
     * @throws BookingException
     */
    @Transactional
    public BookRoomForMeetingResponse bookRoomForMeeting(BookRoomForMeetingRequest request) throws BookingException {
        // Create a timeslot object from the data the requestObjects
        TimeSlotRequest timeSlotRequest = new TimeSlotRequest(request.getDate(), request.getStartTime(), request.getEndTime());

        String exceptionMessage = "";
        boolean hasException = false;

        // Find the employee who creating the meeting
        if (!employeeRepository.existsById(request.getBookedBy())) {
            exceptionMessage += constants.ID_NOT_EXISTS + request.getBookedBy();
            throw new BookingException(exceptionMessage);
        }

        Employee bookedBy = employeeRepository.findById(request.getBookedBy()).get();
        List<Integer> nonExistentRooms = new ArrayList<>();
        List<Integer> nonExistentTeams = new ArrayList<>();
        List<Integer> nonExistentEmployees = new ArrayList<>();

        Set<Integer> attendees = new HashSet<>();

        int totalRoomCapacity = 0;
        int totalAttendees = 0;

        // Code to check whether the input data is valid or not
        // Checking the existence of the rooms
        for (Integer roomId : request.getRoomList()) {
            if (!roomRepository.existsById(roomId)) {
                nonExistentRooms.add(roomId);
            }
        }
        // Checking the existence of the teams
        for (Integer teamId : request.getTeamList()) {
            if (!teamRepository.existsById(teamId)) {
                nonExistentTeams.add(teamId);
            }
        }
        // Checking the existence of the employees
        for (Integer employeeId : request.getEmployeeList()) {
            if (!employeeRepository.existsById(employeeId)) {
                nonExistentEmployees.add(employeeId);
            }
        }

        if (!nonExistentRooms.isEmpty()) {
            exceptionMessage += constants.ROOM_NOT_EXISTS + nonExistentRooms + ", ";
            hasException = true;
        }
        if (!nonExistentTeams.isEmpty()) {
            exceptionMessage += constants.TEAM_NOT_EXISTS + nonExistentTeams + ", ";
            hasException = true;
        }
        if (!nonExistentEmployees.isEmpty()) {
            exceptionMessage += constants.EMPLOYEE_NOT_EXISTS + nonExistentEmployees + ", ";
            hasException = true;
        }

        if (hasException) {
            throw new BookingException(exceptionMessage);
        }

        // Code to check the attendees and the availability of the rooms
        attendees.add(request.getBookedBy());
        attendees.addAll(request.getEmployeeList());

        for (Integer roomId : request.getRoomList()) {
            Room room = roomRepository.findById(roomId).get();
            totalRoomCapacity += room.getRoomCapacity();
        }
        for (Integer teamId : request.getTeamList()) {
            Team team = teamRepository.findById(teamId).get();
            attendees.addAll(team.getEmployeeList().stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList()));
        }

        totalAttendees = attendees.size();

        if (!MeetingUtils.isRoomCapable(totalRoomCapacity, totalAttendees)) {
            List<Integer> availableRooms = MeetingUtils.nextNearestRoom(timeSlotRequest, totalAttendees);
            throw new BookingException(constants.ROOM_NOT_CAPABLE + "TotalCapacity:" + totalRoomCapacity + ", TotalAttendees:" + totalAttendees + ", AvailableRooms:" + availableRooms);
        }

        // Checking for overlapping meetings for the selected rooms, teams and employees
        List<Integer> occupiedRooms = new ArrayList<>();
        List<Integer> occupiedEmployees = new ArrayList<>();

        for (Integer roomId : request.getRoomList()) {
            if (!MeetingUtils.isSlotAvailable(timeSlotRequest, roomId)) {
                occupiedRooms.add(roomId);
            }
        }
        for (Integer employeeId : attendees.stream().toList()) {
            if (!MeetingUtils.isEmployeeAvailable(timeSlotRequest, employeeId)) {
                occupiedEmployees.add(employeeId);
            }
        }

        if (!occupiedRooms.isEmpty()) {
            exceptionMessage += constants.ROOM_OCCUPIED + occupiedRooms + ", ";
            hasException = true;
        }
        if (!occupiedEmployees.isEmpty()) {
            exceptionMessage += constants.EMPLOYEE_OCCUPIED + occupiedEmployees + ", ";
            hasException = true;
        }

        if (hasException) {
            throw new BookingException(exceptionMessage);
        }

        Meeting newMeeting = new Meeting(request.getMeetingName(), request.getDescription(), request.getDate(), request.getStartTime(), request.getEndTime(), bookedBy);

        for (Integer roomId : request.getRoomList()) {
            newMeeting.addRoom(roomRepository.findById(roomId).get());
        }
        for (Integer teamId : request.getTeamList()) {
            newMeeting.addTeam(teamRepository.findById(teamId).get());
        }
        for (Integer employeeId : request.getEmployeeList()) {
            newMeeting.addEmployee(employeeRepository.findById(employeeId).get());
        }

        entityManager.persist(newMeeting);

        return new BookRoomForMeetingResponse(newMeeting.getMeetingId(), request.getBookedBy(), request.getDate(), request.getStartTime(), request.getEndTime(), request.getMeetingName(), request.getDescription(), request.getTeamList(), request.getEmployeeList(), request.getRoomList());
    }

    /**
     * Method to cancel the meeting
     * The method checks the existence of the meeting and deletes the meeting before 30 minutes of the start time of the meeting
     *
     * @param meetingId {Integer}
     * @return CancelMeetingResponse
     * @throws BookingException
     */
    @Transactional
    public CancelMeetingResponse cancelMeeting(Integer meetingId) throws BookingException {

        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            long minutesUntilMeetingStart = ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.of(meeting.getDate(), meeting.getStartTime()));
            if (minutesUntilMeetingStart < 30) {
                meetingRepository.deleteById(meetingId);
                return new CancelMeetingResponse(constants.MEETING_CANCELLED);
            }
            throw new BookingException(constants.MEETING_CANCEL_RESTRICTED);
        }
        throw new BookingException(constants.NO_MEETING_FOUND + meetingId);
    }

    /**
     * Method to update the meeting time
     * The method checks the existence of the meeting and the availability of the timeslot requested and updates the meeting time if the slot is available
     *
     * @param meetingId       {Integer}
     * @param timeSlotRequest {TimeSlotRequest}
     * @return {BookRoomForMeetingResponse}
     * @throws BookingException
     */
    @Transactional
    public BookRoomForMeetingResponse updateMeetingTime(Integer meetingId, TimeSlotRequest timeSlotRequest) throws BookingException {
        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            boolean slotAvailable = true;
            for (Room room : meeting.getRoomList()) {
                if (slotAvailable && !MeetingUtils.isSlotAvailable(timeSlotRequest, room.getRoomId(), meetingId)) {
                    slotAvailable = false;
                }
            }
            if (slotAvailable) {
                meeting.setDate(timeSlotRequest.getDate());
                meeting.setStartTime(timeSlotRequest.getStartTime());
                meeting.setEndTime(timeSlotRequest.getEndTime());
                meetingRepository.save(meeting);
                List<Integer> employeeList = meeting.getEmployeeList().stream().map(employee1 -> employee1.getEmployeeId()).toList();
                List<Integer> roomList = meeting.getRoomList().stream().map(room -> room.getRoomId()).toList();
                List<Integer> teamList = meeting.getTeamList().stream().map(team -> team.getTeamId()).toList();
                return new BookRoomForMeetingResponse(meeting.getMeetingId(), meeting.getBookedBy().getEmployeeId(), meeting.getDate(), meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingName(), meeting.getDescription(), teamList, employeeList, roomList);
            }
            throw new BookingException(constants.NO_SLOT_FOUND);
        }
        throw new BookingException(constants.NO_MEETING_FOUND + meetingId);
    }

    /**
     * Method to update the Meeting Name
     * The method checks the existence of the meeting and updates the meeting name
     *
     * @param meetingId                {Integer}
     * @param updateMeetingNameRequest {UpdateMeetingNameRequest}
     * @return {BookRoomForMeetingResponse}
     * @throws BookingException
     */
    @Transactional
    public BookRoomForMeetingResponse updateMeetingName(Integer meetingId, UpdateMeetingNameRequest updateMeetingNameRequest) throws BookingException {
        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            meeting.setMeetingName(updateMeetingNameRequest.getMeetingName());
            meetingRepository.save(meeting);
            List<Integer> employeeList = meeting.getEmployeeList().stream().map(employee1 -> employee1.getEmployeeId()).toList();
            List<Integer> roomList = meeting.getRoomList().stream().map(room -> room.getRoomId()).toList();
            List<Integer> teamList = meeting.getTeamList().stream().map(team -> team.getTeamId()).toList();
            return new BookRoomForMeetingResponse(meeting.getMeetingId(), meeting.getBookedBy().getEmployeeId(), meeting.getDate(), meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingName(), meeting.getDescription(), teamList, employeeList, roomList);
        }
        throw new BookingException(constants.NO_MEETING_FOUND + meetingId);

    }

    /**
     * Method to update the meeting room
     * The method checks the existence of the meeting and updates the meeting room if the meeting room is available at the particular timeslot
     *
     * @param meetingId                {Integer}
     * @param updateMeetingRoomRequest {UpdateMeetingRoomRequest}
     * @return {BookRoomForMeetingResponse}
     * @throws BookingException
     */
    @Transactional
    public BookRoomForMeetingResponse updateMeetingRoom(Integer meetingId, UpdateMeetingRoomRequest updateMeetingRoomRequest) throws BookingException {
        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            TimeSlotRequest timeSlotRequest = new TimeSlotRequest(meeting.getDate(), meeting.getStartTime(), meeting.getEndTime());
            List<Integer> nonExistentRooms = new ArrayList<>();
            Set<Integer> attendees = new HashSet<>();
            int totalRoomCapacity = 0;
            int totalAttendees = 0;

            // Code to check whether the input data is valid or not
            // Checking the existence of the rooms
            for (Integer roomId : updateMeetingRoomRequest.getRoomList()) {
                if (!roomRepository.existsById(roomId)) {
                    nonExistentRooms.add(roomId);
                }
            }
            if (!nonExistentRooms.isEmpty()) {
                throw new BookingException(constants.ROOM_NOT_EXISTS + nonExistentRooms);
            }

            // Code to check the attendees and the availability of the rooms
            List<Integer> employeeList = meeting.getEmployeeList().stream().map(employee1 -> employee1.getEmployeeId()).toList();
            List<Integer> teamList = meeting.getTeamList().stream().map(team -> team.getTeamId()).toList();

            attendees.add(meeting.getBookedBy().getEmployeeId());
            attendees.addAll(employeeList);

            for (Integer roomId : updateMeetingRoomRequest.getRoomList()) {
                Room room = roomRepository.findById(roomId).get();
                totalRoomCapacity += room.getRoomCapacity();
            }
            for (Integer teamId : teamList) {
                Team team = teamRepository.findById(teamId).get();
                attendees.addAll(team.getEmployeeList().stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList()));
            }

            totalAttendees = attendees.size();

            if (!MeetingUtils.isRoomCapable(totalRoomCapacity, totalAttendees)) {
                List<Integer> availableRooms = MeetingUtils.nextNearestRoom(timeSlotRequest, totalAttendees);
                throw new BookingException(constants.ROOM_NOT_CAPABLE + "TotalCapacity:" + totalRoomCapacity + ", TotalAttendees:" + totalAttendees + ", AvailableRooms:" + availableRooms);
            }

            List<Integer> occupiedRooms = new ArrayList<>();
            for (Integer roomId : updateMeetingRoomRequest.getRoomList()) {
                if (!MeetingUtils.isSlotAvailable(timeSlotRequest, roomId)) {
                    occupiedRooms.add(roomId);
                }
            }
            if (!occupiedRooms.isEmpty()) {
                throw new BookingException(constants.ROOM_OCCUPIED + occupiedRooms);
            }

            meeting.setRoomList(new ArrayList<>());

            for (Integer roomId : updateMeetingRoomRequest.getRoomList()) {
                meeting.addRoom(roomRepository.findById(roomId).get());
            }

            meetingRepository.save(meeting);
            return new BookRoomForMeetingResponse(meeting.getMeetingId(), meeting.getBookedBy().getEmployeeId(), meeting.getDate(), meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingName(), meeting.getDescription(), teamList, employeeList, updateMeetingRoomRequest.getRoomList());
        }
        throw new BookingException(constants.NO_MEETING_FOUND + meetingId);
    }

    /**
     * Method to add employee to the meeting
     * The method checks the existence of the meeting and availability of the employee and adds the employee to the meeting
     *
     * @param meetingId  {int}
     * @param employeeId {int}
     * @return {BookRoomForMeetingResponse}
     * @throws BookingException
     */
    @Transactional
    public BookRoomForMeetingResponse addEmployeeToMeeting(int meetingId, int employeeId) throws BookingException {
        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if (employee.isPresent()) {
                TimeSlotRequest timeSlotRequest = new TimeSlotRequest(meeting.getDate(), meeting.getStartTime(), meeting.getEndTime());
                if (MeetingUtils.isEmployeeAvailable(timeSlotRequest, employeeId)) {
                    meeting.addEmployee(employee.get());
                    List<Integer> employeeList = meeting.getEmployeeList().stream().map(employee1 -> employee1.getEmployeeId()).toList();
                    List<Integer> roomList = meeting.getRoomList().stream().map(room -> room.getRoomId()).toList();
                    List<Integer> teamList = meeting.getTeamList().stream().map(team -> team.getTeamId()).toList();
                    return new BookRoomForMeetingResponse(meeting.getMeetingId(), meeting.getBookedBy().getEmployeeId(), meeting.getDate(), meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingName(), meeting.getDescription(), teamList, employeeList, roomList);
                } else {
                    throw new BookingException(constants.EMPLOYEE_OCCUPIED + employeeId);
                }
            } else {
                throw new BookingException(constants.EMPLOYEE_NOT_EXISTS + employeeId);
            }
        }
        throw new BookingException(constants.NO_MEETING_FOUND + meetingId);
    }

    /**
     * Method to remove employee from the meeting
     * The method checks the existence of the meeting and removes the employee from the meeting if the employee is in a collab meeting
     *
     * @param meetingId  {int}
     * @param employeeId {int}
     * @return {BookRoomForMeetingResponse}
     * @throws BookingException
     */
    @Transactional
    public BookRoomForMeetingResponse removeEmployeeFromMeeting(int meetingId, int employeeId) throws BookingException {
        if (meetingRepository.existsById(meetingId)) {
            Meeting meeting = meetingRepository.findById(meetingId).get();
            Optional<Employee> employee = employeeRepository.findById(employeeId);
            if (employee.isPresent()) {
                if (meeting.getEmployeeList().contains(employee.get())) {
                    meeting.setEmployeeList(meeting.getEmployeeList().stream().filter(employee1 -> employee1.equals(employee)).toList());
                    List<Integer> employeeList = meeting.getEmployeeList().stream().map(employee1 -> employee1.getEmployeeId()).toList();
                    List<Integer> roomList = meeting.getRoomList().stream().map(room -> room.getRoomId()).toList();
                    List<Integer> teamList = meeting.getTeamList().stream().map(team -> team.getTeamId()).toList();
                    return new BookRoomForMeetingResponse(meeting.getMeetingId(), meeting.getBookedBy().getEmployeeId(), meeting.getDate(), meeting.getStartTime(), meeting.getEndTime(), meeting.getMeetingName(), meeting.getDescription(), teamList, employeeList, roomList);
                } else {
                    throw new BookingException(constants.EMPLOYEE_NOT_FOUND + employeeId);
                }
            } else {
                throw new BookingException(constants.EMPLOYEE_NOT_EXISTS + employeeId);
            }
        } else {
            throw new BookingException(constants.NO_MEETING_FOUND + meetingId);
        }
    }
}
