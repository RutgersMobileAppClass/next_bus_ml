package com.teamtbd.nextbusml.model;

import java.util.Date;

/**
 * Created by crejaud on 12/5/16.
 */

public class Course {

    private Date startTime, endTime;
    private Campus campus;

    public Course(Date startTime, Date endTime, Campus campus) {
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
