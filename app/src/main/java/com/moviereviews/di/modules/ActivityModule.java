package com.moviereviews.di.modules;

import com.moviereviews.MainActivity;
import com.moviereviews.critic.CriticActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

   @ContributesAndroidInjector
   abstract MainActivity mainActivity();

   @ContributesAndroidInjector
   abstract CriticActivity criticActivity();
}
