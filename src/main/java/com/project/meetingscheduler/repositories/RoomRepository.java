package com.project.meetingscheduler.repositories;

import com.project.meetingscheduler.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository Class for Room
 *
 * @author karthikeyan
 */
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
