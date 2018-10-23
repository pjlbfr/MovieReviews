package com.moviereviews.critics;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moviereviews.objectresponse.Critic;

import java.util.List;

public class CriticsPresenter implements CriticsContract.Presenter{

    private CriticsContract.View view;
    private CriticsModel model;

    public CriticsPresenter(Context context, @NonNull CriticsContract.View view){
        this.view = view;
        view.setPresenter(this);
        this.model = new CriticsModel(context, this);
    }

    @Override
    public void getCritics() {
        model.getCritics();
    }

    @Override
    public void setCritics(List<Critic> critics) {
        view.setData(critics);
    }

    @Override
    public void getSearchByName(String name) {
        model.getSearchByName(name);
    }
}
