package com.moviereviews.di.modules;

import com.google.gson.Gson;
import com.moviereviews.BuildConfig;
import com.moviereviews.interfaces.NYTApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkModule {

    @Singleton
    @Provides
    static HttpLoggingInterceptor provideLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Singleton
    @Provides
    static OkHttpClient provideHttpClient(HttpLoggingInterceptor logging) {
        long TIMEOUT_SEC = 20;

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();
        return client;
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    static GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient client, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl("https://api.nytimes.com/svc/movies/v2/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    static NYTApi provideNYTApi(Retrofit retrofit) {
        return retrofit.create(NYTApi.class);
    }
}
