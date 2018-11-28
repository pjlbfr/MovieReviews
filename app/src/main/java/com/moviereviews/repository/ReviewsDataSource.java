package com.moviereviews.repository;

import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.ReviewsResult;

import java.util.List;

import io.reactivex.Observable;

public interface ReviewsDataSource {

    void deleteAllReviews();

    boolean hasReviews();

    Observable<ReviewsResult> saveReviews(ReviewsResult result);

    Observable<ReviewsResult> loadCriticReviewsObservable(int page, String name);

    Observable<ReviewsResult> refreshReviewsObservable(String title, String date);

    Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date);


    void close();

}
