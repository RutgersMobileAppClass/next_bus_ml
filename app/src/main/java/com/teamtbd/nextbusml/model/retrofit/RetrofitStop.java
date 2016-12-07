package com.teamtbd.nextbusml.model.retrofit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetrofitStop {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("predictions")
    @Expose
    private List<RetrofitPrediction> predictions = null;

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
    public List<RetrofitPrediction> getPredictions() {
        return predictions;
    }

    /**
     *
     * @param predictions
     * The predictions
     */
    public void setPredictions(List<RetrofitPrediction> predictions) {
        this.predictions = predictions;
    }

}