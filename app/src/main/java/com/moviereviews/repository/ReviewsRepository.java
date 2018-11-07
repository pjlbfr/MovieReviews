package com.moviereviews.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.moviereviews.TypeDataSource;
import com.moviereviews.objectresponse.Review;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class ReviewsRepository implements ReviewsDataSource{

    private static ReviewsRepository INSTANCE = null;

    private final ReviewsDataSource reviewsRemoteDataSource;
    private final ReviewsDataSource reviewsLocalDataSource;
    private TypeDataSource typeDataSource = TypeDataSource.Local;
    private boolean firstLoad = true;
    private List<Review> reviewList = new ArrayList<Review>();

    private ReviewsRepository(@NonNull ReviewsDataSource reviewsRemoteDataSource,
                              @NonNull ReviewsDataSource reviewsLocalDataSource){

        this.reviewsRemoteDataSource = reviewsRemoteDataSource;
        this.reviewsLocalDataSource = reviewsLocalDataSource;
    }

    public static ReviewsRepository getInstance(ReviewsDataSource reviewsRemoteDataSource,
                                                ReviewsDataSource reviewsLocalDataSource){

        if (INSTANCE == null)
            INSTANCE = new ReviewsRepository(reviewsRemoteDataSource, reviewsLocalDataSource);
        return INSTANCE;
    }


    @Override
    public void setOffsetZero() {
        reviewsRemoteDataSource.setOffsetZero();
    }

    @Override
    public void getReviews(@NonNull final ReviewsCallback callback) {

        if (typeDataSource == TypeDataSource.Local){
            // проверить на наличие в реалме и вытащить данные
            if (reviewsLocalDataSource.hasReviews()) {

                reviewsLocalDataSource.getReviews(new ReviewsCallback() {
                    @Override
                    public void onReviewsLoaded(List<Review> reviews) {
                        callback.onReviewsLoaded(reviews);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            } else {
                getReviewsFromRemoteDataSource(callback);
            }
            typeDataSource = TypeDataSource.Remote;
        } else if (!firstLoad){
            getReviewsFromRemoteDataSource(callback);
        }
    }


    private void getReviewsFromRemoteDataSource(@NonNull final ReviewsCallback callback){
        reviewsRemoteDataSource.getReviews(new ReviewsCallback() {
            @Override
            public void onReviewsLoaded(List<Review> reviews) {
                reviewList.addAll(reviews);
                refreshLocalDataSource(reviewList);
                callback.onReviewsLoaded(reviews);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Review> reviews){
        reviewsLocalDataSource.deleteAllReviews();
        reviewsLocalDataSource.saveReviews(reviews);

    }

    public void setNotFirstLoad(){
        firstLoad = false;
    }


  /*  public static boolean isOnline(Context context)
    {
         ConnectivityManager cm =
                 (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo netInfo = cm.getActiveNetworkInfo();
         if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
         }
         return false;
    }
    */

    @Override
    public void close(){
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


    @Override
    public void getSearchByTitle(@NonNull String title, @NonNull SearchByTitleCallback callback) {

    }

    @Override
    public void getSearchByPublicationDate(@NonNull String date, @NonNull SearchByPublicationDateCallback callback) {

    }


}
