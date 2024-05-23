package com.project.meetingscheduler.responseObjects;

import java.util.ArrayList;
import java.util.List;

public class EmployeeResponse {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Integer> teamList = new ArrayList<>();
    private List<Integer> meetingList = new ArrayList<>();

    public EmployeeResponse() {
    }

    public EmployeeResponse(int employeeId, String firstName, String lastName, String email) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public EmployeeResponse(int employeeId, String firstName, String lastName, String email, List<Integer> teamList, List<Integer> meetingList) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.teamList = teamList;
        this.meetingList = meetingList;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Integer> teamList) {
        this.teamList = teamList;
    }

    public List<Integer> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Integer> meetingList) {
        this.meetingList = meetingList;
    }
}
