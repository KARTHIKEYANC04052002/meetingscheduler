package com.project.meetingscheduler.services;

import com.project.meetingscheduler.constants.constants;
import com.project.meetingscheduler.entities.Employee;
import com.project.meetingscheduler.entities.Meeting;
import com.project.meetingscheduler.entities.Team;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.repositories.EmployeeRepository;
import com.project.meetingscheduler.repositories.MeetingRepository;
import com.project.meetingscheduler.repositories.RoomRepository;
import com.project.meetingscheduler.repositories.TeamRepository;
import com.project.meetingscheduler.requestObjects.CreateTeamRequest;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.TeamResponse;
import com.project.meetingscheduler.services.interfaces.TeamServiceInterface;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Team
 *
 * @author karthikeyan
 */
@Service
public class TeamService implements TeamServiceInterface {
    @Autowired
    EntityManager entityManager;
    private MeetingRepository meetingRepository;
    private EmployeeRepository employeeRepository;
    private RoomRepository roomRepository;
    private TeamRepository teamRepository;

    public TeamService() {
    }

    @Autowired
    public TeamService(MeetingRepository meetingRepository, EmployeeRepository employeeRepository, RoomRepository roomRepository, TeamRepository teamRepository) {
        this.meetingRepository = meetingRepository;
        this.employeeRepository = employeeRepository;
        this.roomRepository = roomRepository;
        this.teamRepository = teamRepository;
    }

    /**
     * Method to create Team with the list of employees
     * The method to create the team
     *
     * @param createTeamRequest {CreateTeamRequest}
     * @return {CreateTeamResponse}
     * @throws GlobalException
     */
    @Transactional
    public TeamResponse createTeam(CreateTeamRequest createTeamRequest) throws GlobalException {
        List<Integer> nonExistentEmployees = new ArrayList<>();

        // Checking the existence of the employees
        for (Integer employeeId : createTeamRequest.getEmployeeList()) {
            if (!employeeRepository.existsById(employeeId)) {
                nonExistentEmployees.add(employeeId);
            }
        }

        if (!nonExistentEmployees.isEmpty()) {
            String exceptionMessage = constants.EMPLOYEE_NOT_EXISTS + nonExistentEmployees + ", ";
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), exceptionMessage);
        }
        if (teamRepository.findAll().stream().filter(team -> createTeamRequest.getTeamName().equals(team.getTeamName())).collect(Collectors.toList()).size() > 0) {
            throw new GlobalException(HttpStatus.CONFLICT.value(), constants.TEAM_WITH_NAME_ALREADY_EXISTS + createTeamRequest.getTeamName());
        }

        Team newTeam = new Team(createTeamRequest.getTeamName(), createTeamRequest.getEmployeeList().size());
        for (Integer employeeId : createTeamRequest.getEmployeeList()) {
            newTeam.addEmployee(employeeRepository.findById(employeeId).get());
        }
        teamRepository.save(newTeam);
        TeamResponse teamResponse = new TeamResponse(newTeam.getTeamId(), newTeam.getTeamName(), newTeam.getTeamSize());
        teamResponse.setEmployeeList(employeeRepository.findAll().stream().filter(employee -> employee.getTeamList().contains(newTeam)).map(Employee::getEmployeeId).collect(Collectors.toList()));
        teamResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getTeamList().contains(newTeam)).map(Meeting::getMeetingId).collect(Collectors.toList()));
        return teamResponse;
    }

    /**
     * Method to get all teams details
     *
     * @return {List<Integer>}
     * @throws GlobalException
     */
    @Override
    public List<TeamResponse> getAllTeams() throws GlobalException {
        return teamRepository.findAll().stream().map(team -> {
            TeamResponse teamResponse = new TeamResponse(team.getTeamId(), team.getTeamName(), team.getTeamSize());
            teamResponse.setEmployeeList(employeeRepository.findAll().stream().filter(employee -> employee.getTeamList().contains(team)).map(Employee::getEmployeeId).collect(Collectors.toList()));
            teamResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getTeamList().contains(team)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return teamResponse;
        }).collect(Collectors.toList());
    }

    /**
     * Method to get the team details by id
     *
     * @param teamId
     * @return {Team}
     * @throws GlobalException
     */
    @Override
    public TeamResponse getTeam(Integer teamId) throws GlobalException {
        if (teamRepository.existsById(teamId)) {
            Team team = teamRepository.findById(teamId).get();
            TeamResponse teamResponse = new TeamResponse(team.getTeamId(), team.getTeamName(), team.getTeamSize());
            teamResponse.setEmployeeList(employeeRepository.findAll().stream().filter(employee -> employee.getTeamList().contains(team)).map(Employee::getEmployeeId).collect(Collectors.toList()));
            teamResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getTeamList().contains(team)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return teamResponse;
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.TEAM_NOT_EXISTS + teamId);
        }
    }

    /**
     * Method to update Team with the list of employees
     * The method to update the team
     *
     * @param createTeamRequest {CreateTeamRequest} - CreateTeamRequest is reused here
     * @return {CreateTeamResponse}
     * @throws GlobalException
     */

    @Transactional
    public TeamResponse updateTeam(Integer teamId, CreateTeamRequest createTeamRequest) throws GlobalException {
        List<Integer> nonExistentEmployees = new ArrayList<>();

        // Checking the existence of the employees
        for (Integer employeeId : createTeamRequest.getEmployeeList()) {
            if (!employeeRepository.existsById(employeeId)) {
                nonExistentEmployees.add(employeeId);
            }
        }

        if (!nonExistentEmployees.isEmpty()) {
            String exceptionMessage = constants.EMPLOYEE_NOT_EXISTS + nonExistentEmployees + ", ";
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), exceptionMessage);
        }
        if (teamRepository.findAll().stream().filter(team -> teamId != team.getTeamId() && createTeamRequest.getTeamName().equals(team.getTeamName())).collect(Collectors.toList()).size() > 0) {
            throw new GlobalException(HttpStatus.CONFLICT.value(), constants.TEAM_WITH_NAME_ALREADY_EXISTS + createTeamRequest.getTeamName());
        }

        Team newTeam = new Team(createTeamRequest.getTeamName(), createTeamRequest.getEmployeeList().size());
        for (Integer employeeId : createTeamRequest.getEmployeeList()) {
            newTeam.addEmployee(employeeRepository.findById(employeeId).get());
        }
        newTeam.setTeamId(teamId);
        teamRepository.save(newTeam);
        TeamResponse teamResponse = new TeamResponse(newTeam.getTeamId(), newTeam.getTeamName(), newTeam.getTeamSize());
        teamResponse.setEmployeeList(employeeRepository.findAll().stream().filter(employee -> employee.getTeamList().contains(newTeam)).map(Employee::getEmployeeId).collect(Collectors.toList()));
        teamResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getTeamList().contains(newTeam)).map(Meeting::getMeetingId).collect(Collectors.toList()));
        return teamResponse;
    }

    /**
     * Method to delete the team details by id
     *
     * @param teamId
     * @return {DeleteResponse}
     * @throws GlobalException
     */
    @Override
    public DeleteResponse deleteTeam(Integer teamId) throws GlobalException {
        if (teamRepository.existsById(teamId)) {
            teamRepository.deleteById(teamId);
            return new DeleteResponse(HttpStatus.OK.value(), constants.TEAM_DELETED + teamId);
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.TEAM_NOT_EXISTS);
        }
    }
}
