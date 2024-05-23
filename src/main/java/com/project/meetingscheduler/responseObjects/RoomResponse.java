package com.project.meetingscheduler.responseObjects;

import java.util.ArrayList;
import java.util.List;

public class RoomResponse {
    private int roomId;
    private String roomName;
    private int roomCapacity;
    private List<Integer> bookedMeetingList = new ArrayList<>();

    public RoomResponse() {
    }

    public RoomResponse(int roomId, String roomName, int roomCapacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    public RoomResponse(int roomId, String roomName, int roomCapacity, List<Integer> bookedMeetingList) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
        this.bookedMeetingList = bookedMeetingList;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    public void setRoomCapacity(int roomCapacity) {
        this.roomCapacity = roomCapacity;
    }

    public List<Integer> getBookedMeetingList() {
        return bookedMeetingList;
    }

    public void setBookedMeetingList(List<Integer> bookedMeetingList) {
        this.bookedMeetingList = bookedMeetingList;
    }
}
