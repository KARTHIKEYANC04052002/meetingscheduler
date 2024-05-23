package com.project.meetingscheduler.services;

import com.project.meetingscheduler.constants.constants;
import com.project.meetingscheduler.entities.Meeting;
import com.project.meetingscheduler.entities.Room;
import com.project.meetingscheduler.exceptions.BookingException;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.repositories.EmployeeRepository;
import com.project.meetingscheduler.repositories.MeetingRepository;
import com.project.meetingscheduler.repositories.RoomRepository;
import com.project.meetingscheduler.repositories.TeamRepository;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.RoomResponse;
import com.project.meetingscheduler.services.interfaces.RoomServiceInterface;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Room
 *
 * @author karthikeyan
 */
@Service
public class RoomService implements RoomServiceInterface {

    @Autowired
    EntityManager entityManager;
    private MeetingRepository meetingRepository;
    private EmployeeRepository employeeRepository;
    private RoomRepository roomRepository;
    private TeamRepository teamRepository;

    public RoomService() {
    }

    @Autowired
    public RoomService(EmployeeRepository employeeRepository, MeetingRepository meetingRepository, RoomRepository roomRepository, TeamRepository teamRepository) {
        this.employeeRepository = employeeRepository;
        this.meetingRepository = meetingRepository;
        this.roomRepository = roomRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Method to create room
     *
     * @param room
     * @return {Room}
     */
    @Override
    public RoomResponse addRoom(Room room) throws GlobalException {
        if (roomRepository.findAll().stream().filter(room1 -> room1.getRoomName().equals(room.getRoomName())).collect(Collectors.toList()).size() > 0) {
            throw new GlobalException(HttpStatus.CONFLICT.value(), constants.ROOM_WITH_NAME_ALREADY_EXISTS + room.getRoomName());
        }
        roomRepository.save(room);
        RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
        roomResponse.setBookedMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getRoomList().contains(room)).map(Meeting::getMeetingId).collect(Collectors.toList()));
        return roomResponse;
    }

    /**
     * Method to get all rooms details
     *
     * @return {List<Integer>}
     */
    @Override
    public List<RoomResponse> getAllRooms() throws GlobalException {
        return roomRepository.findAll().stream().map(room -> {
            RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
            roomResponse.setBookedMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getRoomList().contains(room)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return roomResponse;
        }).collect(Collectors.toList());
    }

    /**
     * Method to get the room details by id
     *
     * @param roomId
     * @return {Room}
     */
    @Override
    public RoomResponse getRoom(Integer roomId) throws GlobalException {
        if (roomRepository.existsById(roomId)) {
            Room room = roomRepository.findById(roomId).get();
            RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
            roomResponse.setBookedMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getRoomList().contains(room)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return roomResponse;
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.ROOM_NOT_EXISTS + roomId);
        }
    }

    /**
     * Method to update the room details
     *
     * @param roomId
     * @param room
     * @return {Room}
     */
    @Override
    public RoomResponse updateRoom(Integer roomId, Room room) throws GlobalException {
        if (roomRepository.existsById(roomId)) {
            if (roomRepository.findAll().stream().filter(room1 -> roomId!=room1.getRoomId() && room1.getRoomName().equals(room.getRoomName())).collect(Collectors.toList()).size() > 0) {
                throw new GlobalException(HttpStatus.CONFLICT.value(), constants.ROOM_WITH_NAME_ALREADY_EXISTS + room.getRoomName());
            }
            room.setRoomId(roomId);
            roomRepository.save(room);
            RoomResponse roomResponse = new RoomResponse(room.getRoomId(), room.getRoomName(), room.getRoomCapacity());
            roomResponse.setBookedMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getRoomList().contains(room)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return roomResponse;
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.ROOM_NOT_EXISTS + roomId);
        }
    }

    /**
     * Method to delete the room by id
     *
     * @param roomId
     * @return {DeleteResponse}
     * @throws BookingException
     */
    @Override
    public DeleteResponse deleteRoom(Integer roomId) throws GlobalException {
        if (roomRepository.existsById(roomId)) {
            roomRepository.deleteById(roomId);
            return new DeleteResponse(HttpStatus.OK.value(), constants.ROOM_DELETED + roomId);
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.ROOM_NOT_EXISTS);
        }
    }
}
