package com.project.meetingscheduler.responseObjects;

import com.project.meetingscheduler.entities.Employee;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingResponse {
    private int meetingId;
    private String meetingName;
    private String description;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Employee bookedBy;
    private List<Integer> teamList = new ArrayList<>();
    private List<Integer> employeeList = new ArrayList<>();
    private List<Integer> roomList = new ArrayList<>();

    public MeetingResponse() {
    }

    public MeetingResponse(int meetingId, String meetingName, String description, LocalDate date, LocalTime startTime, LocalTime endTime, Employee bookedBy) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedBy = bookedBy;
    }

    public MeetingResponse(int meetingId, String meetingName, String description, LocalDate date, LocalTime startTime, LocalTime endTime, Employee bookedBy, List<Integer> teamList, List<Integer> employeeList, List<Integer> roomList) {
        this.meetingId = meetingId;
        this.meetingName = meetingName;
        this.description = description;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookedBy = bookedBy;
        this.teamList = teamList;
        this.employeeList = employeeList;
        this.roomList = roomList;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Employee getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(Employee bookedBy) {
        this.bookedBy = bookedBy;
    }

    public List<Integer> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Integer> teamList) {
        this.teamList = teamList;
    }

    public List<Integer> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Integer> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Integer> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
    }
}
