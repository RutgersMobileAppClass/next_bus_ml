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
    private int day;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course(String title, CourseTime startTime, CourseTime endTime, String campus, String day) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;

        if (Campus.BUSCH.getCampusValue().equals(campus)) {
            this.campus = Campus.BUSCH;
        }
        else if (Campus.LIVINGSTON.getCampusValue().equals(campus)) {
            this.campus = Campus.LIVINGSTON;
        }
        else if (Campus.COOK.getCampusValue().equals(campus)) {
            this.campus = Campus.COOK;
        }
        else if (Campus.COLLEGE_AVE.getCampusValue().equals(campus)) {
            this.campus = Campus.COLLEGE_AVE;
        }
        else {
            this.campus = Campus.BUSCH;
        }


        if (day.equals("Monday")) {
            this.day = 0;
        }
        else if (day.equals("Tuesday")) {
            this.day = 1;
        }
        else if (day.equals("Wednesday")) {
            this.day = 2;
        }
        else if (day.equals("Thursday")) {
            this.day = 3;
        }
        else if (day.equals("Friday")) {
            this.day = 4;
        }
        else {
            this.day = 0;
        }
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
