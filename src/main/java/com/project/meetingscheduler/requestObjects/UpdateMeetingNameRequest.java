package com.project.meetingscheduler.requestObjects;

/**
 * Class for UpdateMeetingNameRequest
 *
 * @author karthikeyan
 */
public class UpdateMeetingNameRequest {
    private String meetingName;

    public UpdateMeetingNameRequest() {
    }

    public UpdateMeetingNameRequest(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    @Override
    public String toString() {
        return "UpdateMeetingNameRequest{" + "meetingName='" + meetingName + '\'' + '}';
    }
}
