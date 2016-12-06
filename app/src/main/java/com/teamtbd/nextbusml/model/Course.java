package com.teamtbd.nextbusml.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by crejaud on 12/5/16.
 */

public class Course implements Serializable {

    private String title;
    private Date startTime, endTime;
    private Campus campus;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Course(String title, Date startTime, Date endTime, Campus campus) {
        this.title = title;

        this.startTime = startTime;
        this.endTime = endTime;
        this.campus = campus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }
}
