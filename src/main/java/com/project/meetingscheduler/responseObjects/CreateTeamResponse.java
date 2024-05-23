package com.project.meetingscheduler.responseObjects;

import com.project.meetingscheduler.requestObjects.CreateTeamRequest;

import java.util.List;

/**
 * Class for CreateTeamResponse
 *
 * @author karthikeyan
 */
public class CreateTeamResponse extends CreateTeamRequest {
    public Integer teamId;

    public CreateTeamResponse() {
        super();
    }

    public CreateTeamResponse(Integer teamId, String teamName, List<Integer> employeeList) {
        super(teamName, employeeList);
        this.teamId = teamId;
    }
}
