package com.moviereviews.repository.remote;

import com.moviereviews.NetworkClient;
import com.moviereviews.interfaces.NYTApi;
import com.moviereviews.objectresponse.Reviews;
import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.realm.RealmRequests;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ReviewsRemote {

    private final NYTApi retrofit;
    private final RealmRequests realmRequests;

    @Inject
    public ReviewsRemote(NYTApi retrofit, RealmRequests realmRequests) {
        this.retrofit = retrofit;
        this.realmRequests = realmRequests;
    }

    public Observable<ReviewsResult> loadCriticReviewsObservable(int page, String name) {

        return retrofit.getCriticReviews(page, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Reviews, ObservableSource<ReviewsResult>>)
                        reviews -> Observable.just(new ReviewsResult(reviews.isHas_more(), reviews.getReviews())));
    }

    public boolean hasReviews() {
        return true;
    }

    public Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date) {
        return retrofit.getReviewsObservable(page, title, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Reviews, ObservableSource<ReviewsResult>>)
                        reviews -> {
//                            Observable.just(new ReviewsResult(reviews.isHas_more(), reviews.getReviews()));
                            return realmRequests.insertReviews(new ReviewsResult(reviews.isHas_more(), reviews.getReviews()));
                        });
    }
}
