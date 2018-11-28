package com.moviereviews.critics;

import android.content.Context;

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

    private Context context;
    private CriticsContract.Presenter presenter;

    public CriticsModel(Context context, CriticsContract.Presenter presenter) {
        this.context = context;
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
