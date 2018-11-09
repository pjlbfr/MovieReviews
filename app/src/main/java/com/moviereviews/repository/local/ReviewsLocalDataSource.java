package com.moviereviews.repository.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;
import com.moviereviews.realm.RealmRequests;
import com.moviereviews.repository.ReviewsDataSource;

import java.util.List;

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
    public void refreshReviews(String title, @NonNull ReviewsCallback callback) {

    }

    @Override
    public void loadReviews(int page, String title, String date, @NonNull ReviewsCallback callback) {
        callback.onReviewsLoaded(realmRequests.getReviews(), false);
    }

    @Override
    public void deleteAllReviews() {
        realmRequests.deleteAllReviews();
    }

    @Override
    public void saveReviews(List<Review> reviews) {
        realmRequests.insertReviews(reviews);
    }

    @Override
    public boolean hasReviews(){
        return realmRequests.hasReviews();
    }


    @Override
    public void close(){
        realmRequests.close();
    }
}
