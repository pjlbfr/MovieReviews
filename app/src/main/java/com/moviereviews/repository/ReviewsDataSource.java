package com.moviereviews.repository;

import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;

import java.util.List;

public interface ReviewsDataSource {

    interface ReviewsCallback {

        void onReviewsLoaded(List<Review> reviews, boolean hasMoreReviews);

        void onDataNotAvailable();
    }

    void refreshReviews(String title, @NonNull ReviewsCallback callback);

    void loadReviews(int page, String title, String date, @NonNull ReviewsCallback callback);

    void deleteAllReviews();

    void saveReviews(List<Review> reviews);

    boolean hasReviews();

    void close();

}
