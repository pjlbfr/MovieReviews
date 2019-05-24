package com.moviereviews.critics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.moviereviews.objectresponse.Critic;

import java.util.List;

public class CriticsPresenter implements CriticsContract.Presenter{

    public static final String TAG = CriticsPresenter.class.getSimpleName();

    private CriticsContract.View view;
    private CriticsContract.Model model;

    public CriticsPresenter(@NonNull CriticsContract.View view){
        this.view = view;
        view.setPresenter(this);
        this.model = new CriticsModel(this);
    }

    @Override
    public void setCritics(List<Critic> critics) {
        view.setData(critics);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getCriticsObservable(String name) {
        model.getCriticsObservable(name)
                .subscribe(critics -> view.setData(critics),
                           error -> Log.d(TAG, "getCriticsObservable: " + error.getMessage())
                );
    }
}
