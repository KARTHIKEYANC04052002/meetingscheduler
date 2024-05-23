package com.project.meetingscheduler.services.interfaces;

import com.project.meetingscheduler.entities.Employee;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.EmployeeResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface EmployeeServiceInterface {
    public EmployeeResponse addEmployee(Employee employee) throws GlobalException;

    public List<EmployeeResponse> getAllEmployees() throws GlobalException;

    public EmployeeResponse getEmployee(Integer employeeId) throws GlobalException;

    public EmployeeResponse updateEmployee(Integer employeeId, @RequestBody Employee employee) throws GlobalException;

    public DeleteResponse deleteEmployee(Integer employeeId) throws GlobalException;
}
