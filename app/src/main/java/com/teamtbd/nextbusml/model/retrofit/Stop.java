package com.teamtbd.nextbusml.model.retrofit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stop {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("predictions")
    @Expose
    private List<Prediction> predictions = null;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The predictions
     */
    public List<Prediction> getPredictions() {
        return predictions;
    }

    /**
     *
     * @param predictions
     * The predictions
     */
    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

}