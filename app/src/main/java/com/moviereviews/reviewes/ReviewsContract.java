package com.moviereviews.reviewes;

import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public interface ReviewsContract {

    interface View extends BaseView<ReviewsContract.Presenter> {
        void setData(List<Review> reviews);
    }

    interface Presenter{
        void setToFirstPage();
        void getReviews();
        void setReviews(List<Review> reviews);
        void getSearchByTitle(String title);
        void getSearchByPublicationDate(String date);
    }
}
