package com.moviereviews.reviewes;

import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;
import com.moviereviews.repository.ReviewsDataSource;
import com.moviereviews.repository.ReviewsRepository;

import java.util.List;

public class ReviewsPresenter implements ReviewsContract.Presenter {

    private ReviewsContract.View view;
    private final ReviewsRepository reviewsRepository;
    private boolean hasMoreReviews = true;

    public ReviewsPresenter(@NonNull ReviewsContract.View view, @NonNull ReviewsRepository reviewsRepository) {
        this.view = view;
        view.setPresenter(this);
        this.reviewsRepository = reviewsRepository;
    }

    @Override
    public void loadReviews(int page, String title, String date) {
      //  if (hasMoreReviews) {
            reviewsRepository.loadReviews(page, title, date, new ReviewsDataSource.ReviewsCallback() {
                @Override
                public void onReviewsLoaded(List<Review> reviews, boolean hasMoreReviews) {
                    view.setData(reviews, hasMoreReviews);
                    setHasMoreReviews(hasMoreReviews);
                }

                @Override
                public void onDataNotAvailable() {

                }
            });
        //}
    }

    private void setHasMoreReviews(boolean hasMore){
        this.hasMoreReviews = hasMore;
    }

    @Override
    public void refreshReviews(String title) {
        reviewsRepository.refreshReviews(title, new ReviewsDataSource.ReviewsCallback() {
            @Override
            public void onReviewsLoaded(List<Review> reviews, boolean hasMoreReviews) {
                if (reviews.size() == 0){
                    view.showMessageIsEmpty();
                }else {
                    view.setData(reviews, hasMoreReviews);
                    setHasMoreReviews(hasMoreReviews);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // вывести во view
            }
        });
    }

//    @Override
//    public void setReviews(List<Review> reviews) {
//        view.setData(reviews, hasMoreReviews);
//    }

    @Override
    public void close() {
        reviewsRepository.close();
    }
}
