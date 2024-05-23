package com.project.meetingscheduler.responseObjects;

import java.util.ArrayList;
import java.util.List;

public class TeamResponse {
    private int teamId;
    private String teamName;
    private int teamSize;
    private List<Integer> employeeList = new ArrayList<>();
    private List<Integer> meetingList = new ArrayList<>();

    public TeamResponse() {
    }

    public TeamResponse(int teamId, String teamName, int teamSize) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamSize = teamSize;
    }

    public TeamResponse(int teamId, String teamName, int teamSize, List<Integer> employeeList, List<Integer> meetingList) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamSize = teamSize;
        this.employeeList = employeeList;
        this.meetingList = meetingList;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public List<Integer> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Integer> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Integer> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Integer> meetingList) {
        this.meetingList = meetingList;
    }
}
