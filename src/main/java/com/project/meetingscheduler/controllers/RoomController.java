package com.project.meetingscheduler.controllers;

import com.project.meetingscheduler.controllers.interfaces.RoomControllerInterface;
import com.project.meetingscheduler.entities.Room;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.RoomResponse;
import com.project.meetingscheduler.services.RoomService;
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
 * Controller class to handle room related endpoints
 * The class has methods to create room, view room, delete room, update room.
 *
 * @author karthikeyan
 */
@RestController
@RequestMapping("/room")
public class RoomController implements RoomControllerInterface {
    @Autowired
    RoomService roomService;

    public RoomController() {
    }

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Transactional
    @PostMapping()
    public ResponseEntity<RoomResponse> add(Room room) throws GlobalException {
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @GetMapping()
    public ResponseEntity<List<RoomResponse>> getAll() throws GlobalException {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> get(Integer roomId) throws GlobalException {
        return ResponseEntity.ok(roomService.getRoom(roomId));
    }

    @Transactional
    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> update(Integer roomId, Room room) throws GlobalException {
        return ResponseEntity.ok(roomService.updateRoom(roomId, room));
    }

    @Transactional
    @DeleteMapping("/{roomId}")
    public ResponseEntity<DeleteResponse> delete(Integer roomId) throws GlobalException {
        return ResponseEntity.ok(roomService.deleteRoom(roomId));
    }
}
