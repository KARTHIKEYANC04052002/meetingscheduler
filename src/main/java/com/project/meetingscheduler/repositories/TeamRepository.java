package com.project.meetingscheduler.repositories;

import com.project.meetingscheduler.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Class for Team
 *
 * @author karthikeyan
 */
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
