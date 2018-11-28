package com.moviereviews.repository.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.realm.RealmRequests;
import com.moviereviews.repository.ReviewsDataSource;

import java.util.List;

import io.reactivex.Observable;

public class ReviewsLocalDataSource implements ReviewsDataSource {

    private static ReviewsLocalDataSource INSTANCE;

    private RealmRequests realmRequests;

    private ReviewsLocalDataSource(@NonNull Context context){
        realmRequests = new RealmRequests(context);
    }

    public static ReviewsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null)
            INSTANCE = new ReviewsLocalDataSource(context);
        return INSTANCE;
    }

    @Override
    public void deleteAllReviews() {
        realmRequests.deleteAllReviews();
    }

    @Override
    public Observable<ReviewsResult> saveReviews(ReviewsResult result) {
        return realmRequests.insertReviews(result);
    }

    @Override
    public Observable<ReviewsResult> loadCriticReviewsObservable(int page, String name) {
        return null;
    }

    @Override
    public boolean hasReviews(){
        return realmRequests.hasReviews();
    }

    @Override
    public Observable<ReviewsResult> refreshReviewsObservable(String title, String date) {
        return null;
    }

    @Override
    public Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date) {
        return realmRequests.getReviewsObservable();
    }

    @Override
    public void close(){
        realmRequests.close();
    }
}
