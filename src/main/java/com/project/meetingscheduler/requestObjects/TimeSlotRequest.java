package com.project.meetingscheduler.requestObjects;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Class for TimeSlotRequest
 *
 * @author karthikeyan
 */
public class TimeSlotRequest {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlotRequest() {
    }

    public TimeSlotRequest(LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "TimeSlotRequest{" + "date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + '}';
    }
}
