package com.moviereviews.objectresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultimediaReview {
    @SerializedName("type")
    @Expose
    String type;
    @SerializedName("src")
    @Expose
    String src;
    @SerializedName("height")
    @Expose
    Integer height;
    @SerializedName("width")
    @Expose
    Integer width;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }
}
