package com.project.meetingscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class for start of the SpringBootApplication
 *
 * @author karthikeyan
 */
@SpringBootApplication
public class MeetingSchedulerApplication {

    /**
     * The method for start of the application
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MeetingSchedulerApplication.class, args);
    }
}
