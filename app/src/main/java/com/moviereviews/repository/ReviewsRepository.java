package com.moviereviews.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;

import java.util.List;

public class ReviewsRepository implements ReviewsDataSource {

    private static ReviewsRepository INSTANCE = null;

    private final ReviewsDataSource reviewsRemoteDataSource;
    private final ReviewsDataSource reviewsLocalDataSource;
    private Context context;

    private ReviewsRepository(Context context, @NonNull ReviewsDataSource reviewsRemoteDataSource,
                              @NonNull ReviewsDataSource reviewsLocalDataSource) {
        this.context = context;
        this.reviewsRemoteDataSource = reviewsRemoteDataSource;
        this.reviewsLocalDataSource = reviewsLocalDataSource;
    }

    public static ReviewsRepository getInstance(Context context,
                                                ReviewsDataSource reviewsRemoteDataSource,
                                                ReviewsDataSource reviewsLocalDataSource) {
        if (INSTANCE == null)
            INSTANCE = new ReviewsRepository(context, reviewsRemoteDataSource, reviewsLocalDataSource);
        return INSTANCE;
    }

    @Override
    public void refreshReviews(String title, @NonNull final ReviewsCallback callback) {
        reviewsRemoteDataSource.refreshReviews(title, new ReviewsCallback() {
            @Override
            public void onReviewsLoaded(List<Review> reviews, boolean hasMoreReviews) {
                reviewsLocalDataSource.deleteAllReviews();
                reviewsLocalDataSource.saveReviews(reviews);
                callback.onReviewsLoaded(reviews, hasMoreReviews);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void loadReviews(int page, String title, String date, @NonNull final ReviewsCallback callback) {
        if (reviewsLocalDataSource.hasReviews() && page == 0) {
            reviewsLocalDataSource.loadReviews(page, title, date, callback);
        } else {
            reviewsRemoteDataSource.loadReviews(page, title, date, new ReviewsCallback() {
                @Override
                public void onReviewsLoaded(List<Review> reviews, boolean hasMoreReviews) {
                    reviewsLocalDataSource.deleteAllReviews();
                    reviewsLocalDataSource.saveReviews(reviews);
                    callback.onReviewsLoaded(reviews, hasMoreReviews);
                }

                @Override
                public void onDataNotAvailable() {
                    callback.onDataNotAvailable();
                }
            });
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        reviewsLocalDataSource.close();
    }


    @Override
    public void deleteAllReviews() {

    }

    @Override
        public void saveReviews(List<Review> reviews) {

    }

    @Override
    public boolean hasReviews() {
        return false;
    }
}
