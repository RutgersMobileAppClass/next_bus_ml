package com.teamtbd.nextbusml.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {

    @SerializedName("minutes")
    @Expose
    private String minutes;
    @SerializedName("seconds")
    @Expose
    private String seconds;

    /**
     *
     * @return
     * The minutes
     */
    public String getMinutes() {
        return minutes;
    }

    /**
     *
     * @param minutes
     * The minutes
     */
    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    /**
     *
     * @return
     * The seconds
     */
    public String getSeconds() {
        return seconds;
    }

    /**
     *
     * @param seconds
     * The seconds
     */
    public void setSeconds(String seconds) {
        this.seconds = seconds;
    }

    public String toString() {
        return getMinutes();
    }

}
