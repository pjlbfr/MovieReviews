package com.moviereviews.di;

import com.moviereviews.MovieApplication;
import com.moviereviews.di.modules.ActivityModule;
import com.moviereviews.di.modules.FragmentModule;
import com.moviereviews.di.modules.LocalModule;
import com.moviereviews.di.modules.NetworkModule;
import com.moviereviews.di.modules.RepositoryModule;
import com.moviereviews.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        LocalModule.class,
        ActivityModule.class,
        FragmentModule.class,
        NetworkModule.class,
        RepositoryModule.class,
        ViewModelModule.class
})
public interface AppComponent extends AndroidInjector<MovieApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<MovieApplication>{}

}
