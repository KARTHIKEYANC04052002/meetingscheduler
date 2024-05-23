package com.project.meetingscheduler.repositories;

import com.project.meetingscheduler.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Class for Employee
 *
 * @author karthikeyan
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
