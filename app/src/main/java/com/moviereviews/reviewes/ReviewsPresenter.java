package com.moviereviews.reviewes;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.moviereviews.repository.ReviewsRepository;


public class ReviewsPresenter implements ReviewsContract.Presenter {

    public static final String TAG = ReviewsPresenter.class.getSimpleName();

    private ReviewsContract.View view;
    private final ReviewsRepository reviewsRepository;

    public ReviewsPresenter(@NonNull ReviewsContract.View view, @NonNull ReviewsRepository reviewsRepository) {
        this.view = view;
        view.setPresenter(this);
        this.reviewsRepository = reviewsRepository;
    }

    @SuppressLint("CheckResult")
    @Override
    public void refreshReviewsObservable(String title, String date) {
        reviewsRepository.refreshReviewsObservable(title, date)
        .subscribe(reviewsResult -> view.setData(reviewsResult.getReviews(), reviewsResult.isHas_more()),
                error-> Log.d(TAG, "refreshReviewsObservable: ")

        );
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadReviewsObservable(int page, String title, String date) {
        reviewsRepository.loadReviewsObservable(page, title, date)
                .subscribe(reviewsResults -> view.setData(reviewsResults.getReviews(), reviewsResults.isHas_more()),
                        error -> {
                            Log.d(TAG, "loadReviewsObserv: "+ error.getMessage());
                        }
                );
    }

    @Override
    public void close() {
        reviewsRepository.close();
    }
}
