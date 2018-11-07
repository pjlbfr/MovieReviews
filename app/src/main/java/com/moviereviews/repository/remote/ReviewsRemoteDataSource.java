package com.moviereviews.repository.remote;

import android.support.annotation.NonNull;

import com.moviereviews.ApplicationMR;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.Reviews;
import com.moviereviews.repository.ReviewsDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsRemoteDataSource implements ReviewsDataSource{

    private static ReviewsRemoteDataSource INSTANCE;

    private int offset = 0;


    public static ReviewsRemoteDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ReviewsRemoteDataSource();
        return INSTANCE;
    }

    @Override
    public void setOffsetZero() {
        offset = 0;
    }

    @Override
    public void getReviews(@NonNull final ReviewsCallback callback) {
        ApplicationMR.getApi().getReviews(offset).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                if (response.body() != null) {
                    callback.onReviewsLoaded(response.body().getReviews());
                    offset+=20;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
              //  Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getSearchByTitle(@NonNull String title, @NonNull final SearchByTitleCallback callback) {
        ApplicationMR.getApi().getSearchTitleReview(title).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                if (response.body() != null) {
                    callback.onReviewsLoaded(response.body().getReviews());
                    offset+=20;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
             //   Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getSearchByPublicationDate(@NonNull String date, @NonNull final SearchByPublicationDateCallback callback) {
        ApplicationMR.getApi().getSearchPublicationDateReview(date).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                if (response.body() != null) {
                    callback.onReviewsLoaded(response.body().getReviews());
                    offset+=20;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
               // Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void deleteAllReviews() {
        // не используется
    }

    @Override
    public void saveReviews(List<Review> reviews) {
        // не используется
    }

    @Override
    public boolean hasReviews() {
        return true;
    }

    @Override
    public void close() {

    }
}
