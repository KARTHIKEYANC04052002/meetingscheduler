package com.project.meetingscheduler.requestObjects;

import java.util.List;

/**
 * Class for CreateTeamRequest
 *
 * @author karthikeyan
 */
public class CreateTeamRequest {
    private String teamName;
    private List<Integer> employeeList;

    public CreateTeamRequest() {
    }

    public CreateTeamRequest(String teamName, List<Integer> employeeList) {
        this.teamName = teamName;
        this.employeeList = employeeList;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Integer> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Integer> employeeList) {
        this.employeeList = employeeList;
    }

    @Override
    public String toString() {
        return "CreateTeamRequest{" + "teamName='" + teamName + '\'' + ", employeeList=" + employeeList + '}';
    }
}
