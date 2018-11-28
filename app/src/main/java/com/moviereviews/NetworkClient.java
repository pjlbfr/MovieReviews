package com.moviereviews;

import com.moviereviews.interfaces.NYTApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static Retrofit retrofit;
    private static NYTApi sNYTApi;

    public NetworkClient() {

    }

    public static NYTApi getRetrofit(){
        if (retrofit == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.nytimes.com/svc/movies/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            sNYTApi = retrofit.create(NYTApi.class);
        }

        return sNYTApi;
    }
}
