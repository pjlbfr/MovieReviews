package com.moviereviews.critics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.moviereviews.ApplicationMR;
import com.moviereviews.NetworkClient;
import com.moviereviews.R;
import com.moviereviews.interfaces.NYTApi;
import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.Critics;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.Reviews;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
//        return ApplicationMR.getApi().getCritics(name)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap((Function<Critics, ObservableSource<List<Critic>>>) critics -> Observable.just(critics.getCritics()));
//    }
}
