package com.moviereviews.critic;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.moviereviews.repository.ReviewsRepository;

public class CriticPresenter implements CriticContract.Presenter {

    public static final String TAG = CriticPresenter.class.getSimpleName();

    private CriticContract.View view;
    private ReviewsRepository reviewsRepository;

    public CriticPresenter(@NonNull CriticContract.View view, @NonNull ReviewsRepository reviewsRepository) {
        this.view = view;
        view.setPresenter(this);
        this.reviewsRepository = reviewsRepository;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadReviewsObservable(int page, String name) {
        reviewsRepository.loadCriticReviewsObservable(page, name)
                .subscribe(reviewsResult -> view.setData(reviewsResult.getReviews(), reviewsResult.isHas_more()),
                        error -> Log.d(TAG, "refreshReviewsObservable: "+ error.getMessage())
                );
    }
}
