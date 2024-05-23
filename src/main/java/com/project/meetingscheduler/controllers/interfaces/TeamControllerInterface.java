package com.project.meetingscheduler.controllers.interfaces;

import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.requestObjects.CreateTeamRequest;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.TeamResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Controller Interface for defining Team related methods
 *
 * @author Karthikeyan
 */
public interface TeamControllerInterface {

    public ResponseEntity<TeamResponse> add(@RequestBody CreateTeamRequest createTeamRequest) throws GlobalException;

    public ResponseEntity<List<TeamResponse>> getAll() throws GlobalException;

    public ResponseEntity<TeamResponse> get(@PathVariable Integer teamId) throws GlobalException;

    public ResponseEntity<TeamResponse> update(@PathVariable Integer teamId, @RequestBody CreateTeamRequest createTeamRequest) throws GlobalException;

    public ResponseEntity<DeleteResponse> delete(@PathVariable Integer teamId) throws GlobalException;
}
