package com.moviereviews.repository;

import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.repository.local.ReviewsLocal;
import com.moviereviews.repository.remote.ReviewsRemote;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ReviewsRepository implements ReviewsDataSource {

    private final ReviewsRemote remote;
    private final ReviewsLocal local;

    @Inject
    public ReviewsRepository(ReviewsRemote remote,
                             ReviewsLocal local) {
        this.remote = remote;
        this.local = local;
    }

    @Override
    public void close() {
        local.close();
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
        return remote.loadCriticReviewsObservable(page, name);
    }

    @Override
    public boolean hasReviews() {
        return false;
    }

    @Override
    public Observable<ReviewsResult> refreshReviewsObservable(String title, String date) {
        local.deleteAllReviews();
        return remote.loadReviewsObservable(0, title, date)
                .subscribeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ReviewsResult> loadReviewsObservable(int page, String title, String date) {
        return local.loadReviewsObservable(page, title, date)
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<ReviewsResult, Observable<ReviewsResult>>) reviewsResult -> {
                    if (reviewsResult != null && page == 0)
                        return Observable.just(reviewsResult);
                    else
                        return remote
                                .loadReviewsObservable(page, title, date)
                                .subscribeOn(Schedulers.io());
                });


//        if (reviewsLocalDataSource.hasReviews() && page == 0) {
//            return reviewsLocalDataSource.loadReviewsObservable(page, title, date);
//        } else {
//            return reviewsRemoteDataSource.loadReviewsObservable(page, title, date)
//                    .subscribeOn(AndroidSchedulers.mainThread())
//                    .flatMap((Function<ReviewsResult, ObservableSource<ReviewsResult>>)
//                            reviewsLocalDataSource::saveReviews
//                    );
//        }
    }
}
