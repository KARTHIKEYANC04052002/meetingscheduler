package com.project.meetingscheduler.controllers;

import com.project.meetingscheduler.controllers.interfaces.EmployeeControllerInterface;
import com.project.meetingscheduler.entities.Employee;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.EmployeeResponse;
import com.project.meetingscheduler.services.EmployeeService;
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
 * Controller class to handle employee related endpoints
 * The class has methods to create employee, view employee, delete employee, update employee.
 *
 * @author karthikeyan
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController implements EmployeeControllerInterface {

    EmployeeService employeeService;

    public EmployeeController() {
    }

    @Autowired
    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<EmployeeResponse> add(Employee employee) throws GlobalException {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponse>> getAll() throws GlobalException {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> get(Integer employeeId) throws GlobalException {
        return ResponseEntity.ok(employeeService.getEmployee(employeeId));
    }

    @Transactional
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeResponse> update(Integer employeeId, Employee employee) throws GlobalException {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }

    @Transactional
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<DeleteResponse> delete(Integer employeeId) throws GlobalException {
        return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
    }
}
