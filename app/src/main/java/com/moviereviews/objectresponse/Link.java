package com.moviereviews.objectresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Link extends RealmObject{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("suggested_link_text")
    @Expose
    private String suggested_link_text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSuggested_link_text() {
        return suggested_link_text;
    }

    public void setSuggested_link_text(String suggested_link_text) {
        this.suggested_link_text = suggested_link_text;
    }
}
