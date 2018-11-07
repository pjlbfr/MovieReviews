package com.moviereviews.realm;

import android.content.Context;

import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.Review;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmRequests {

    private Realm realm;

    public RealmRequests(Context context){
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public List<Review> getReviews(){
        return realm.where(Review.class).findAll();
    }

    public List<Review> getReviewsByTitle(String title){
        return realm.where(Review.class).equalTo("title", title).findAll();
    }

    public List<Review> getReviewsByPublicationDate(String date){
        return realm.where(Review.class).equalTo("publication_date", date).findAll();
    }

    public void insertReviews(List<Review> reviews){
        realm.beginTransaction();
        for (Review review : reviews){
            review.setId(UUID.randomUUID().toString());
            realm.copyToRealm(review);
        }
        realm.commitTransaction();
    }

    public void deleteAllReviews(){
        RealmResults<Review> results = realm.where(Review.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public List<Critic> getCritics(){
        return realm.where(Critic.class).findAllAsync();
    }

    public void insertCritics(List<Critic> critics){
        realm.beginTransaction();
        for (Critic critic : critics){
            critic.setId(UUID.randomUUID().toString());
            realm.copyToRealm(critic);
        }
        realm.commitTransaction();
    }


    public void deleteAllCritics(){
        RealmResults<Critic> results = realm.where(Critic.class).findAll();
        realm.beginTransaction();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public boolean hasReviews(){
        if (realm.where(Review.class).count() != 0)
            return true;
        else return false;
    }

    public void close(){
        realm.close();
    }

}
