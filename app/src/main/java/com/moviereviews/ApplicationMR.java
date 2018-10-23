package com.moviereviews;

import android.app.Application;

import com.moviereviews.interfaces.NYTApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationMR extends Application {

    private static NYTApi sNYTApi;
    private Retrofit retrofit;

    @Override
    public void onCreate(){
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/movies/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sNYTApi = retrofit.create(NYTApi.class);
    }

    public static NYTApi getApi(){
        return sNYTApi;
    }

}
