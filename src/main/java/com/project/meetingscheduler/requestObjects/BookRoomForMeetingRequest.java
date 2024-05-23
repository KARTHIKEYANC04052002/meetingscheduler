package com.project.meetingscheduler.requestObjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Class for BookRoomForMeetingRequest
 *
 * @author karthikeyan
 */
public class BookRoomForMeetingRequest {
    private int bookedBy;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String meetingName;
    private String description;
    private List<Integer> teamList;
    private List<Integer> employeeList;
    private List<Integer> roomList;

    public BookRoomForMeetingRequest() {
    }


    public BookRoomForMeetingRequest(int bookedBy, LocalDate date, LocalTime startTime, LocalTime endTime, String meetingName, String description) {
        this.bookedBy = bookedBy;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingName = meetingName;
        this.description = description;
    }

    public BookRoomForMeetingRequest(int bookedBy, LocalDate date, LocalTime startTime, LocalTime endTime, String meetingName, String description, List<Integer> teamList, List<Integer> employeeList, List<Integer> roomList) {
        this.bookedBy = bookedBy;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingName = meetingName;
        this.description = description;
        this.teamList = teamList;
        this.employeeList = employeeList;
        this.roomList = roomList;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
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

    @Override
    public String toString() {
        return "BookRoomForMeetingRequest{" + "bookedBy=" + bookedBy + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", meetingName='" + meetingName + '\'' + ", description='" + description + '\'' + ", teamList=" + teamList + ", employeeList=" + employeeList + ", roomList=" + roomList + '}';
    }
}
