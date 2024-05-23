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
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.EmployeeResponse;
import com.project.meetingscheduler.services.interfaces.EmployeeServiceInterface;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Class for employee
 *
 * @author karthikeyan
 */
@Service
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired
    EntityManager entityManager;
    private MeetingRepository meetingRepository;
    private EmployeeRepository employeeRepository;
    private RoomRepository roomRepository;
    private TeamRepository teamRepository;

    public EmployeeService() {
    }

    @Autowired
    public EmployeeService(MeetingRepository meetingRepository, EmployeeRepository employeeRepository, RoomRepository roomRepository, TeamRepository teamRepository) {
        this.meetingRepository = meetingRepository;
        this.employeeRepository = employeeRepository;
        this.roomRepository = roomRepository;
        this.teamRepository = teamRepository;
    }


    /**
     * Method to Create employee
     *
     * @param employee
     * @return {Employee}
     */
    @Override
    @Transactional
    public EmployeeResponse addEmployee(Employee employee) throws GlobalException {
        if (employeeRepository.findAll().stream().filter(employee1 -> employee.getEmail().equals(employee1.getEmail())).collect(Collectors.toList()).size() > 0) {
            throw new GlobalException(HttpStatus.CONFLICT.value(), constants.EMPLOYEE_WITH_EMAIL_ALREADY_EXISTS + employee.getEmail());
        }
        employeeRepository.save(employee);
        EmployeeResponse employeeResponse = new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
        employeeResponse.setTeamList(teamRepository.findAll().stream().filter(team -> team.getEmployeeList().contains(employee)).map(Team::getTeamId).collect(Collectors.toList()));
        employeeResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getEmployeeList().contains(employee)).map(Meeting::getMeetingId).collect(Collectors.toList()));
        return employeeResponse;
    }

    /**
     * Method to get all employees details
     *
     * @return {List<Integer>}
     * @throws GlobalException
     */
    @Override
    public List<EmployeeResponse> getAllEmployees() throws GlobalException {
        return employeeRepository.findAll().stream().map(employee -> {
            EmployeeResponse employeeResponse = new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
            employeeResponse.setTeamList(teamRepository.findAll().stream().filter(team -> team.getEmployeeList().contains(employee)).map(Team::getTeamId).collect(Collectors.toList()));
            employeeResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getEmployeeList().contains(employee)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return employeeResponse;
        }).collect(Collectors.toList());
    }

    /**
     * Method to get employee details by id
     *
     * @param employeeId
     * @return {Employee}
     * @throws GlobalException
     */
    @Override
    public EmployeeResponse getEmployee(Integer employeeId) throws GlobalException {
        if (employeeRepository.existsById(employeeId)) {
            Employee employee = employeeRepository.findById(employeeId).get();
            EmployeeResponse employeeResponse = new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
            employeeResponse.setTeamList(teamRepository.findAll().stream().filter(team -> team.getEmployeeList().contains(employee)).map(Team::getTeamId).collect(Collectors.toList()));
            employeeResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getEmployeeList().contains(employee)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return employeeResponse;
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.EMPLOYEE_NOT_EXISTS + employeeId);
        }
    }

    /**
     * Method to Update employee details
     *
     * @param employeeId
     * @param employee
     * @return {Employee}
     */
    @Override
    @Transactional
    public EmployeeResponse updateEmployee(Integer employeeId, Employee employee) throws GlobalException {
        if (employeeRepository.existsById(employeeId)) {
            if (employeeRepository.findAll().stream().filter(employee1 -> employeeId != employee1.getEmployeeId() && employee.getEmail().equals(employee1.getEmail())).collect(Collectors.toList()).size() > 0) {
                throw new GlobalException(HttpStatus.CONFLICT.value(), constants.EMPLOYEE_WITH_EMAIL_ALREADY_EXISTS + employee.getEmail());
            }
            employee.setEmployeeId(employeeId);
            employeeRepository.save(employee);
            EmployeeResponse employeeResponse = new EmployeeResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), employee.getEmail());
            employeeResponse.setTeamList(teamRepository.findAll().stream().filter(team -> team.getEmployeeList().contains(employee)).map(Team::getTeamId).collect(Collectors.toList()));
            employeeResponse.setMeetingList(meetingRepository.findAll().stream().filter(meeting -> meeting.getEmployeeList().contains(employee)).map(Meeting::getMeetingId).collect(Collectors.toList()));
            return employeeResponse;
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.EMPLOYEE_NOT_EXISTS + employeeId);
        }
    }

    /**
     * Method to delete the employee by id
     *
     * @param employeeId
     * @return {DeleteResponse}
     * @throws GlobalException
     */
    @Override
    @Transactional
    public DeleteResponse deleteEmployee(Integer employeeId) throws GlobalException {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return new DeleteResponse(HttpStatus.OK.value(), constants.EMPLOYEE_DELETED + employeeId);
        } else {
            throw new GlobalException(HttpStatus.NOT_FOUND.value(), constants.EMPLOYEE_NOT_EXISTS);
        }
    }
}
