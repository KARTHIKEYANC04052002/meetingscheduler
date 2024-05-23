package com.project.meetingscheduler.requestObjects;

import java.util.List;

/**
 * Class for UpdateMeetingRoomRequest
 *
 * @author karthikeyan
 */
public class UpdateMeetingRoomRequest {
    private List<Integer> roomList;

    public UpdateMeetingRoomRequest() {
    }


    public UpdateMeetingRoomRequest(List<Integer> roomList) {
        this.roomList = roomList;
    }

    public List<Integer> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
    }

    @Override
    public String toString() {
        return "UpdateMeetingRoomRequest{" + "roomList=" + roomList + '}';
    }
}
