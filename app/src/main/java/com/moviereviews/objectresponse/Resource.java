package com.moviereviews.objectresponse;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class Resource extends RealmObject implements Parcelable{

    private String type;
    private String src;
    private int height;
    private int width;
    private String credit;

    public Resource() {

    }

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(src);
        parcel.writeInt(height);
        parcel.writeInt(width);
        parcel.writeString(credit);
    }


    public static final Parcelable.Creator<Resource> CREATOR = new Parcelable.Creator<Resource>(){

        public Resource createFromParcel(Parcel in) {
            return new Resource(in);
        }

        @Override
        public Resource[] newArray(int i) {
            return new Resource[i];
        }
    };


    private Resource(Parcel parcel) {
        type = parcel.readString();
        src = parcel.readString();
        height = parcel.readInt();
        width = parcel.readInt();
        credit = parcel.readString();
    }

}
