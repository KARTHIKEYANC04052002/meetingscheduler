package com.project.meetingscheduler.controllers;

import com.project.meetingscheduler.controllers.interfaces.TeamControllerInterface;
import com.project.meetingscheduler.entities.Team;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.repositories.TeamRepository;
import com.project.meetingscheduler.requestObjects.CreateTeamRequest;
import com.project.meetingscheduler.responseObjects.CreateTeamResponse;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.TeamResponse;
import com.project.meetingscheduler.services.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class to handle team related endpoints
 * The class has methods to create team, view team, delete team, update team.
 *
 * @author Karthikeyan
 */

@RestController
@RequestMapping("/team")
public class TeamController implements TeamControllerInterface {
    @Autowired
    private TeamRepository teamRepository;

    private TeamService teamService;

    public TeamController() {
    }

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<TeamResponse> add(CreateTeamRequest createTeamRequest) throws GlobalException {
        return ResponseEntity.ok(teamService.createTeam(createTeamRequest));
    }

    @GetMapping()
    public ResponseEntity<List<TeamResponse>> getAll() throws GlobalException {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamResponse> get(Integer teamId) throws GlobalException {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @Transactional
    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponse> update(Integer teamId, CreateTeamRequest createTeamRequest) throws GlobalException {
        return ResponseEntity.ok(teamService.updateTeam(teamId, createTeamRequest));
    }

    @Transactional
    @DeleteMapping("/{teamId}")
    public ResponseEntity<DeleteResponse> delete(Integer teamId) throws GlobalException {
        return ResponseEntity.ok(teamService.deleteTeam(teamId));
    }
}
