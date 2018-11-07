package com.moviereviews.reviewes;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;
import com.moviereviews.repository.ReviewsDataSource;
import com.moviereviews.repository.ReviewsRepository;

import java.util.List;

public class ReviewsPresenter implements ReviewsContract.Presenter {

    private ReviewsContract.View view;
    private final ReviewsRepository reviewsRepository;

    public ReviewsPresenter(Context context, @NonNull ReviewsContract.View view, @NonNull ReviewsRepository reviewsRepository){
        this.view = view;
        view.setPresenter(this);
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public void setToFirstPage() {
        reviewsRepository.setOffsetZero();
    }

    @Override
    public void getReviews() {
        reviewsRepository.getReviews(new ReviewsDataSource.ReviewsCallback() {
            @Override
            public void onReviewsLoaded(List<Review> reviews) {
                view.setData(reviews);
            }

            @Override
            public void onDataNotAvailable() {
                // вывести во view ошибку
            }
        });
    }

    @Override
    public void setNotFirstLoad() {
        reviewsRepository.setNotFirstLoad();
    }


    @Override
    public void setReviews(List<Review> reviews) {
        view.setData(reviews);
    }

    @Override
    public void getSearchByTitle(String title) {

    }

    @Override
    public void getSearchByPublicationDate(String date) {

    }

    @Override
    public void close() {
        reviewsRepository.close();
    }
}
