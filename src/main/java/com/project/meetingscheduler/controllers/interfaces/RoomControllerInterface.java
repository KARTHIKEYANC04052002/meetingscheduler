package com.project.meetingscheduler.controllers.interfaces;

import com.project.meetingscheduler.entities.Room;
import com.project.meetingscheduler.exceptions.GlobalException;
import com.project.meetingscheduler.responseObjects.DeleteResponse;
import com.project.meetingscheduler.responseObjects.RoomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Controller Interface for defining Room related methods
 *
 * @author karthikeyan
 */
public interface RoomControllerInterface {

    public ResponseEntity<RoomResponse> add(@RequestBody Room room) throws GlobalException;

    public ResponseEntity<List<RoomResponse>> getAll() throws GlobalException;

    public ResponseEntity<RoomResponse> get(@PathVariable Integer roomId) throws GlobalException;

    public ResponseEntity<RoomResponse> update(@PathVariable Integer roomId, @RequestBody Room data) throws GlobalException;

    public ResponseEntity<DeleteResponse> delete(@PathVariable Integer roomId) throws GlobalException;
}
