package com.moviereviews.di.modules;

import com.moviereviews.critic.CriticFragment;
import com.moviereviews.critics.CriticsFragment;
import com.moviereviews.reviewes.ReviewsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract ReviewsFragment reviewsFragment();

    @ContributesAndroidInjector
    abstract CriticsFragment criticsFragment();

    @ContributesAndroidInjector
    abstract CriticFragment criticFragment();
}
