package com.moviereviews;

import com.moviereviews.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MovieApplication extends DaggerApplication{

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configRealm = new RealmConfiguration
                                                .Builder()
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
        Realm.setDefaultConfiguration(configRealm);


    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
