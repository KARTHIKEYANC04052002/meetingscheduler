package com.project.meetingscheduler.services.interfaces;

import com.project.meetingscheduler.entities.Room;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.RoomResponse;

import java.util.List;

public interface RoomServiceInterface {
    public RoomResponse addRoom(Room room) throws GlobalException;

    public List<RoomResponse> getAllRooms() throws GlobalException;

    public RoomResponse getRoom(Integer roomId) throws GlobalException;

    public RoomResponse updateRoom(Integer roomId, Room data) throws GlobalException;

    public DeleteResponse deleteRoom(Integer roomId) throws GlobalException;

}
