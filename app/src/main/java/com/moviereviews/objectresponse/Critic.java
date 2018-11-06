package com.moviereviews.objectresponse;

import android.os.Parcel;
import android.os.Parcelable;

public class Critic implements Parcelable{

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(display_name);
        parcel.writeString(sort_name);
        parcel.writeString(status);
        parcel.writeString(bio);
        parcel.writeString(seo_name);
        parcel.writeParcelable(multimedia, i);
    }

    public static final Parcelable.Creator<Critic> CREATOR = new Parcelable.Creator<Critic>(){

      public Critic createFromParcel(Parcel in){
          return new Critic(in);
      }

      @Override
      public Critic[] newArray(int i){
          return new Critic[i];
      }
    };

    private Critic(Parcel parcel){
        display_name = parcel.readString();
        sort_name = parcel.readString();
        status = parcel.readString();
        bio = parcel.readString();
        seo_name = parcel.readString();
        multimedia = parcel.readParcelable(MultimediaCritic.class.getClassLoader());
    }

}
