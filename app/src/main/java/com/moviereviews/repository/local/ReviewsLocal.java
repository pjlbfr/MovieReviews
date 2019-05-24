package com.moviereviews.repository.local;

import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.realm.RealmRequests;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ReviewsLocal {

    public static final String TAG = ReviewsLocal.class.getSimpleName();

    private final RealmRequests realmRequests;

    @Inject
    public ReviewsLocal(RealmRequests realmRequests) {
        this.realmRequests = realmRequests;
    }


    public void deleteAllReviews() {
        realmRequests.deleteAllReviews();
    }

    public Observable<ReviewsResult> saveReviews(ReviewsResult result) {
        return realmRequests.insertReviews(result);
    }

//    public Observable<ReviewsResult> loadCriticReviewsObservable(int page, String name) {
//        return null;
//    }

//    public boolean hasReviews() {
//
//        return realmRequests.hasReviews();
//    }

    public Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date) {
        return realmRequests.getReviewsObservable();
    }

    public void close() {
        realmRequests.close();
    }
}
