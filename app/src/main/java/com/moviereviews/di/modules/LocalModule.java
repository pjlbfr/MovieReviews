package com.moviereviews.di.modules;

import com.moviereviews.Utils;
import com.moviereviews.realm.RealmRequests;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class LocalModule {

    @Singleton
    @Provides
    static RealmRequests providesRealmRequest() {
        return new RealmRequests();
    }

    @Singleton
    @Provides
    static Utils getUtils(){
        return new Utils();
    }

}
