package com.moviereviews.objectresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Critics {

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("copyright")
    @Expose
    String copyright;

    @SerializedName("num_results")
    @Expose
    int numResults;

    @SerializedName("results")
    @Expose
    List<Critic> critics;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getNumResults() {
        return numResults;
    }

    public void setNumResults(int numResults) {
        this.numResults = numResults;
    }

    public List<Critic> getCritics() {
        return critics;
    }

    public void setCritics(List<Critic> critics) {
        this.critics = critics;
    }
}
