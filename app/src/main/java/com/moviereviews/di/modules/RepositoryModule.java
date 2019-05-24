package com.moviereviews.di.modules;

import com.moviereviews.repository.ReviewsRepository;
import com.moviereviews.repository.local.ReviewsLocal;
import com.moviereviews.repository.remote.ReviewsRemote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

    @Provides
    @Singleton
    public static ReviewsRepository reviewsRepository(ReviewsRemote remote, ReviewsLocal local){

        return new ReviewsRepository(remote, local);
    }
}
