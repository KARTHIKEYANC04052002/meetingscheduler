package com.project.meetingscheduler.services.interfaces;

import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.requestObjects.CreateTeamRequest;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.TeamResponse;

import java.util.List;

/**
 * Service Interface class for Team
 *
 * @author karthikeyan
 */
public interface TeamServiceInterface {
    public TeamResponse createTeam(CreateTeamRequest createTeamRequest) throws GlobalException;

    public List<TeamResponse> getAllTeams() throws GlobalException;

    public TeamResponse getTeam(Integer teamId) throws GlobalException;

    public TeamResponse updateTeam(Integer teamId, CreateTeamRequest createTeamRequest) throws GlobalException;

    public DeleteResponse deleteTeam(Integer teamId) throws GlobalException;
}
