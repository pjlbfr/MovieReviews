package com.moviereviews.reviewes;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.models.Model;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public class ReviewsPresenter implements ReviewsContract.Presenter {

    private Context context;
    private ReviewsContract.View view;
    private Model model;

    public ReviewsPresenter(Context context, @NonNull ReviewsContract.View view){
        this.view = view;
        view.setPresenter(this);
        this.model = new Model(context, this);
    }

    @Override
    public void setToFirstPage() {
        model.setToFirstPage();
    }

    @Override
    public void getReviews() {
        model.getReviews();
    }

    @Override
    public void setReviews(List<Review> reviews) {
        view.setData(reviews);
    }

    @Override
    public void getSearchByTitle(String title) {
        model.getSearchByTitle(title);
    }

    @Override
    public void getSearchByPublicationDate(String date) {
        model.getSearchByPublicationDate(date);
    }
}
