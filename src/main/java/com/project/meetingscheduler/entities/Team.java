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
 * Entity class for team
 *
 * @author karthikeyan
 */
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int teamId;
    @Column(name = "team_name")
    private String teamName;
    @Column(name = "team_size")
    private int teamSize;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Employee> employeeList = new ArrayList<>();
    @ManyToMany(mappedBy = "teamList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Meeting> meetingList = new ArrayList<>();

    public Team() {
    }

    public Team(String teamName, Integer teamSize) {
        this.teamName = teamName;
        this.teamSize = teamSize;
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

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public List<Meeting> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
        this.teamSize = employeeList.size();
    }

    public void addMeeting(Meeting meeting) {
        meetingList.add(meeting);
    }
}
