package com.moviereviews.usecases;

import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.repository.ReviewsDataSource;
import com.moviereviews.repository.ReviewsRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetReviewsUseCase{

    private final ReviewsDataSource dataSource;

    @Inject
    public GetReviewsUseCase(ReviewsRepository dataSource) {
        this.dataSource = dataSource;
    }

    public Observable<ReviewsResult> getReviews(int page, String title, String date){
        return dataSource.loadReviewsObservable(page, title, date);
    }

    public Observable<ReviewsResult> refreshReviews(String title, String date){
        return dataSource.refreshReviewsObservable(title, date);
    }
}
