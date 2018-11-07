package com.moviereviews.repository;

import android.support.annotation.NonNull;

import com.moviereviews.TypeDataSource;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public interface ReviewsDataSource {

    interface ReviewsCallback{

        void onReviewsLoaded(List<Review> reviews);

        void onDataNotAvailable();
    }

    interface SearchByTitleCallback{

        void onReviewsLoaded(List<Review> reviews);

        void onDataNotAvailable();

    }


    interface SearchByPublicationDateCallback{

        void onReviewsLoaded(List<Review> reviews);

        void onDataNotAvailable();

    }

    void setOffsetZero();

    void getReviews(@NonNull ReviewsCallback callback);

    void getSearchByTitle(@NonNull String title, @NonNull SearchByTitleCallback callback);

    void getSearchByPublicationDate(@NonNull String date, @NonNull SearchByPublicationDateCallback callback);

    void deleteAllReviews();

    void saveReviews(List<Review> reviews);

    boolean hasReviews();

    void close();

}
