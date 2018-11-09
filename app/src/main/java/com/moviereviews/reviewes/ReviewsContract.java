package com.moviereviews.reviewes;

import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public interface ReviewsContract {

    interface View extends BaseView<ReviewsContract.Presenter> {
        void setData(List<Review> reviews);
        void showMessageIsEmpty();
    }

    interface Presenter {
        void loadReviews(int page, String title, String date);

        void refreshReviews(String title);

        void setReviews(List<Review> reviews);

        void close();
    }
}
