package com.moviereviews.reviewes;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.moviereviews.objectresponse.ReviewsResult;
import com.moviereviews.usecases.GetReviewsUseCase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class ReviewsViewModel extends ViewModel {

    private final MutableLiveData<ReviewsResult> result = new MutableLiveData<>();
    private final GetReviewsUseCase reviewsUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public ReviewsViewModel(GetReviewsUseCase reviewsUseCase) {
        this.reviewsUseCase = reviewsUseCase;
    }

    public MutableLiveData<ReviewsResult> result() {
        return result;
    }

    public void refreshReviews(String title, String date) {
        compositeDisposable.add(reviewsUseCase.refreshReviews(title, date)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    result().postValue(result);
                        }
                ));
    }

    public void loadReviews(int page, String title, String date) {
        compositeDisposable.add(reviewsUseCase.getReviews(page, title, date)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    result().postValue(result);
                })
        );
    }

    @Override
    protected void onCleared() {
        // clean up resources
        super.onCleared();
        compositeDisposable.clear();
    }
}
