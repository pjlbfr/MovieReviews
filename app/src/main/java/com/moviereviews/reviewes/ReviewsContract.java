package com.moviereviews.reviewes;

import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Review;

import java.util.List;

import io.reactivex.Observable;

public interface ReviewsContract {

    interface View extends BaseView<ReviewsContract.Presenter> {

        void setData(List<Review> reviews, boolean hasMoreReviews);

        void showMessageIsEmpty();
    }

    interface Presenter {

        void refreshReviewsObservable(String title, String date);

        void loadReviewsObservable(int page, String title, String date);

        void close();
    }
}
