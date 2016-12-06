package com.teamtbd.nextbusml.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by crejaud on 12/5/16.
 */

public class Stop implements Serializable {
    private List<Date> arrivals;

    private String name;
    private String busName;

    public Stop(String name, String busName, List<Date> arrivals) {
        this.name = name;
        this.busName = busName;
        this.arrivals = arrivals;
    }

    public List<Date> getArrivals() {
        return arrivals;
    }

    public void setArrivals(List<Date> arrivals) {
        this.arrivals = arrivals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }
}
