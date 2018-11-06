package com.moviereviews.realm;

import android.content.Context;

import io.realm.Realm;

public class RealmRequests {

    private Realm realm;

    public RealmRequests(Context context){
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }


    

    public void close(){
        realm.close();
    }

}
