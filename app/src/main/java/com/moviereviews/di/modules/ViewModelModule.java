package com.moviereviews.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.moviereviews.ViewModelFactory;
import com.moviereviews.di.annotation.ViewModelKey;
import com.moviereviews.reviewes.ReviewsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ReviewsViewModel.class)
    abstract ViewModel bindReviewsViewModel(ReviewsViewModel reviewsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
