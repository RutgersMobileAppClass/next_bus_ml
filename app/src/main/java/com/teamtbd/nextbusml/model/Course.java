package com.teamtbd.nextbusml.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by crejaud on 12/5/16.
 */

public class Course implements Serializable {

    private String title;
    private CourseTime startTime, endTime;
    private Campus campus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course(String title, CourseTime startTime, CourseTime endTime, Campus campus) {
        this.title = title;

        this.startTime = startTime;
        this.endTime = endTime;
        this.campus = campus;
    }

    public CourseTime getStartTime() {
        return startTime;
    }

    public void setStartTime(CourseTime startTime) {
        this.startTime = startTime;
    }

    public CourseTime getEndTime() {
        return endTime;
    }

    public void setEndTime(CourseTime endTime) {
        this.endTime = endTime;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public String getCourseTime () {
        return startTime.getHour() + ":" + startTime.getMinute() + " - " + endTime.getHour() + ":" + endTime.getMinute();
    }
}
