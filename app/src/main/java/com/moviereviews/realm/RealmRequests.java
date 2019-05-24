package com.moviereviews.realm;

import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.ReviewsResult;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmRequests {

    private Realm realm;

    public RealmRequests() {
        realm = Realm.getDefaultInstance();
    }

    public List<Review> getReviews() {
        return realm.where(Review.class).findAll();
    }

    public Observable<ReviewsResult> getReviewsObservable() {
        return Observable.fromCallable(() -> new ReviewsResult(false, realm.where(Review.class).findAll()));
    }

    public Observable<ReviewsResult> insertReviews(ReviewsResult result) {
        return Observable.fromCallable(() -> {
            realm.beginTransaction();
            for (Review review : result.getReviews()) {
                review.setId(UUID.randomUUID().toString());
                realm.copyToRealm(review);
            }
            realm.commitTransaction();
            return result;
        });
    }

    public void deleteAllReviews() {
        RealmResults<Review> results = realm.where(Review.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public List<Critic> getCritics() {
        return realm.where(Critic.class).findAllAsync();
    }

    public void insertCritics(List<Critic> critics) {
        realm.beginTransaction();
        for (Critic critic : critics) {
            critic.setId(UUID.randomUUID().toString());
            realm.copyToRealm(critic);
        }
        realm.commitTransaction();
    }


    public void deleteAllCritics() {
        RealmResults<Critic> results = realm.where(Critic.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public boolean hasReviews() {
        if (realm.where(Review.class).count() != 0)
            return true;
        else return false;
    }

    public void close() {
        realm.close();
    }

}
