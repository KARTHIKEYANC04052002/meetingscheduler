package com.project.meetingscheduler.controllers.interfaces;

import com.project.meetingscheduler.entities.Employee;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.EmployeeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Controller Interface for defining Employee related methods
 *
 * @author karthikeyan
 */
public interface EmployeeControllerInterface {

    public ResponseEntity<EmployeeResponse> add(@RequestBody Employee employee) throws GlobalException;

    public ResponseEntity<List<EmployeeResponse>> getAll() throws GlobalException;

    public ResponseEntity<EmployeeResponse> get(@PathVariable Integer employeeId) throws GlobalException;

    public ResponseEntity<EmployeeResponse> update(@PathVariable Integer employeeId, @RequestBody Employee employee) throws GlobalException;

    public ResponseEntity<DeleteResponse> delete(@PathVariable Integer employeeId) throws GlobalException;
}
