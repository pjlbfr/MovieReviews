package com.moviereviews.objectresponse;

import android.os.Parcel;
import android.os.Parcelable;

public class MultimediaCritic implements Parcelable{

    private Resource resource;

    public MultimediaCritic(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(resource, i);
    }

    public static final Parcelable.Creator<MultimediaCritic> CREATOR = new Parcelable.Creator<MultimediaCritic>(){

        public MultimediaCritic createFromParcel(Parcel in) {
            return new MultimediaCritic(in);
        }

        @Override
        public MultimediaCritic[] newArray(int i) {
            return new MultimediaCritic[i];
        }
    };

    private MultimediaCritic(Parcel parcel){
        resource = parcel.readParcelable(Resource.class.getClassLoader());
    }

}
