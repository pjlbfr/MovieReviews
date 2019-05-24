package com.moviereviews.critics;

import com.moviereviews.NetworkClient;
import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.Critics;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CriticsModel implements CriticsContract.Model {

    public static final String TAG = CriticsModel.class.getSimpleName();

    private CriticsContract.Presenter presenter;

    public CriticsModel(CriticsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Observable<List<Critic>> getCriticsObservable(String name) {
        return NetworkClient.getRetrofit()
                .getCritics(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Critics, ObservableSource<List<Critic>>>) critics -> Observable.just(critics.getCritics()));

    }

}
