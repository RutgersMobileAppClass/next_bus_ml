package com.teamtbd.nextbusml.model;

/**
 * Created by crejaud on 12/5/16.
 */

public enum Campus {
    BUSCH("Busch"), LIVINGSTON("Livingston"), COOK("Cook/Douglass"), COLLEGE_AVE("College Avenue");

    private String campusValue;

    Campus(String campusValue) {
        this.campusValue = campusValue;
    }

    public String getCampusValue() {
        return campusValue;
    }

}
