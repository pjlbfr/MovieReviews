package com.moviereviews.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.ReviewsResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

public class ReviewsRepository implements ReviewsDataSource {

    private static ReviewsRepository INSTANCE = null;

    private final ReviewsDataSource reviewsRemoteDataSource;
    private final ReviewsDataSource reviewsLocalDataSource;

    private ReviewsRepository(@NonNull ReviewsDataSource reviewsRemoteDataSource,
                              @NonNull ReviewsDataSource reviewsLocalDataSource) {
        this.reviewsRemoteDataSource = reviewsRemoteDataSource;
        this.reviewsLocalDataSource = reviewsLocalDataSource;
    }

    public static ReviewsRepository getInstance(ReviewsDataSource reviewsRemoteDataSource,
                                                ReviewsDataSource reviewsLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new ReviewsRepository(reviewsRemoteDataSource, reviewsLocalDataSource);
        return INSTANCE;
    }

    @Override
    public void close() {
        reviewsLocalDataSource.close();
    }


    @Override
    public void deleteAllReviews() {

    }

    @Override
    public Observable<ReviewsResult> saveReviews(ReviewsResult result) {
        return null;
    }

    @Override
    public Observable<ReviewsResult> loadCriticReviewsObservable(int page, String name) {
        return reviewsRemoteDataSource.loadCriticReviewsObservable(page, name);
    }

    @Override
    public boolean hasReviews() {
        return false;
    }

    @Override
    public Observable<ReviewsResult> refreshReviewsObservable(String title, String date) {
        return reviewsRemoteDataSource.loadReviewsObservable(0, title, date)
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<ReviewsResult, ObservableSource<ReviewsResult>>) reviewsResult -> {
                    reviewsLocalDataSource.deleteAllReviews();
                    return reviewsLocalDataSource.saveReviews(reviewsResult);
                });
    }

    @Override
    public Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date) {
        if (reviewsLocalDataSource.hasReviews() && page == 0) {
            return reviewsLocalDataSource.loadReviewsObservable(page, title, date);
        } else {
            return reviewsRemoteDataSource.loadReviewsObservable(page, title, date)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .flatMap((Function<ReviewsResult, ObservableSource<ReviewsResult>>)
                            reviewsLocalDataSource::saveReviews
                    );
        }
    }
}
