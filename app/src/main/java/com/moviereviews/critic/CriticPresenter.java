package com.moviereviews.critic;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Review;

import java.util.List;

public class CriticPresenter implements CriticContract.Presenter{

    private CriticContract.View view;
    private CriticModel model;

    public CriticPresenter(Context context, @NonNull CriticContract.View view) {
        this.view = view;
        model = new CriticModel(context, this);
        view.setPresenter(this);
    }

    @Override
    public void setOffsetZero() {
        model.setOffsetZero();
    }

    @Override
    public void getReviews(String name) {
        model.getReviews(name);
    }

    @Override
    public void setReviews(List<Review> reviews) {
        view.setData(reviews);
    }
}
