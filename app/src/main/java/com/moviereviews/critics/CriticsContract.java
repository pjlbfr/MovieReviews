package com.moviereviews.critics;


import com.moviereviews.interfaces.BaseView;
import com.moviereviews.objectresponse.Critic;

import java.util.List;

import io.reactivex.Observable;

public interface CriticsContract {

    interface View extends BaseView<Presenter> {
        void setData(List<Critic> critics);
    }

    interface Presenter{
        void setCritics(List<Critic> critics);
        void getCriticsObservable(String name);
    }

    interface Model {
        Observable<List<Critic>> getCriticsObservable(String name);
    }
}
