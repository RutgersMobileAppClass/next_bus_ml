package com.teamtbd.nextbusml.model;

import java.io.Serializable;

/**
 * Created by pjb165 on 12/6/16.
 */
public class CourseTime implements Serializable {
    private int hour;
    private int minute;


    public CourseTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }








}
