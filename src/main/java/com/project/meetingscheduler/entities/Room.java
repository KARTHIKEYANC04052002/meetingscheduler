package com.project.meetingscheduler.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for room
 *
 * @author karthikeyan
 */
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int roomId;
    @Column(name = "room_name")
    private String roomName;
    @Column(name = "room_capacity")
    private int roomCapacity;
    @ManyToMany(mappedBy = "roomList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Meeting> bookedMeetingList = new ArrayList<>();

    public Room() {
    }

    public Room(String roomName, int roomCapacity) {
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
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

    public List<Meeting> getBookedMeetingList() {
        return bookedMeetingList;
    }

    public void setBookedMeetingList(List<Meeting> bookedMeetingList) {
        this.bookedMeetingList = bookedMeetingList;
    }

    public void addMeeting(Meeting meeting) {
        bookedMeetingList.add(meeting);
    }

    @Override
    public String toString() {
        return "Room{" + "roomId=" + roomId + ", roomName='" + roomName + '\'' + ", roomCapacity=" + roomCapacity + ", bookedMeetingList=" + bookedMeetingList + '}';
    }
}
