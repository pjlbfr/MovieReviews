package com.moviereviews.critic;

import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public interface CriticContract {
    interface View extends BaseView<CriticContract.Presenter> {
        void setData(List<Review> reviews);
    }

    interface Presenter{
        void setOffsetZero();
        void getReviews(String name);
        void setReviews(List<Review> reviews);
    }
}
