package com.moviereviews.objectresponse;

public class Critic{

    String display_name;
    String sort_name;
    String status;
    String bio;
    String seo_name;
    MultimediaCritic multimedia;

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSeo_name() {
        return seo_name;
    }

    public void setSeo_name(String seo_name) {
        this.seo_name = seo_name;
    }

    public MultimediaCritic getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(MultimediaCritic multimedia) {
        this.multimedia = multimedia;
    }
}
