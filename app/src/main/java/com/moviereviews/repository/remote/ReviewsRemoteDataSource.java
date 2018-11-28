package com.moviereviews.repository.remote;

import com.moviereviews.NetworkClient;
import com.moviereviews.objectresponse.Reviews;
import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.repository.ReviewsDataSource;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ReviewsRemoteDataSource implements ReviewsDataSource {

    private static ReviewsRemoteDataSource INSTANCE;

    public static ReviewsRemoteDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ReviewsRemoteDataSource();
        return INSTANCE;
    }

    @Override
    public void deleteAllReviews() {
        // не используется
    }

    @Override
    public Observable<ReviewsResult> saveReviews(ReviewsResult result) {
        return null;
    }

    @Override
    public Observable<ReviewsResult> loadCriticReviewsObservable(int page, String name) {
        return NetworkClient.getRetrofit().getCriticReviews(page, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Reviews, ObservableSource<ReviewsResult>>)
                        reviews -> Observable.just(new ReviewsResult(reviews.isHas_more(), reviews.getReviews())));
    }


    @Override
    public boolean hasReviews() {
        return true;
    }

    @Override
    public Observable<ReviewsResult> refreshReviewsObservable(String title, String date) {
        return null;
    }

    @Override
    public Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date) {
        return NetworkClient.getRetrofit().getReviewsObservable(page, title, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Reviews, ObservableSource<ReviewsResult>>)
                        reviews -> Observable.just(new ReviewsResult(reviews.isHas_more(), reviews.getReviews())));
    }

    @Override
    public void close() {

    }
}
