package com.project.meetingscheduler.constants;

/**
 * Class to hold all the constants
 *
 * @author karthikeyan
 */
public class constants {
    public static final String ID_NOT_EXISTS = "Your identity is not exists in the database with the following id: ";
    public static final String ROOM_NOT_EXISTS = "Room(s) with the following id(s) not exists: ";
    public static final String ROOM_WITH_NAME_ALREADY_EXISTS = "Room with the following name already exists: ";
    public static final String TEAM_NOT_EXISTS = "Team(s) with the following id(s) not exists: ";
    public static final String TEAM_WITH_NAME_ALREADY_EXISTS = "Team with the following name already exists: ";
    public static final String EMPLOYEE_NOT_EXISTS = "Employee(s) with the following id(s) not exists: ";
    public static final String EMPLOYEE_WITH_EMAIL_ALREADY_EXISTS = "Employee with the following email id already exists: ";
    public static final String ROOM_NOT_CAPABLE = "Selected room(s) is not capable for the attendee(s) added: ";
    public static final String ROOM_OCCUPIED = "Room(s) with the following id(s) occupied for another meeting: ";
    public static final String EMPLOYEE_OCCUPIED = "Employee(s) with the following id(s) occupied in another meeting: ";
    public static final String MEETING_CANCELLED = "Meeting cancelled successfully";
    public static final String MEETING_CANCEL_RESTRICTED = "You can't cancel the meeting now. It's less than 30 minutes for the start of the meeting";
    public static final String NO_MEETING_FOUND = "No Meeting Found with the id: ";
    public static final String NO_SLOT_FOUND = "Slot not available";
    public static final String NO_ROOM_FOUND = "Room not available";
    public static final String EMPLOYEE_NOT_FOUND = "Employee(s) with the following id(s) not in the meeting: ";
    public static final String EMPLOYEE_DELETED = "Employee with the following id is deleted: ";
    public static final String ROOM_DELETED = "Room with the following id is deleted: ";
    public static final String TEAM_DELETED = "Team with the following id is deleted: ";
    public static final String INVALID_DATA_FORMAT = "Invalid Data Format";
    public static final String FOREIGN_KEY_CONSTRAINT_FAIL = "Could not execute statement - Cannot delete or update a parent row: a foreign key constraint fails";

}
