package com.moviereviews.critic;

import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public interface CriticContract {
    interface View extends BaseView<CriticContract.Presenter> {
        void setData(List<Review> reviews, boolean hasMoreReviews);
    }

    interface Presenter{
        void loadReviewsObservable(int page, String name);
    }
}
