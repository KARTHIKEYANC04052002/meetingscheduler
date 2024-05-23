package com.project.meetingscheduler.repositories;

import com.project.meetingscheduler.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository Class for Meeting
 *
 * @author karthikeyan
 */
public interface MeetingRepository extends JpaRepository<Meeting, Integer> {
    List<Meeting> findAllByDate(LocalDate date);
}
