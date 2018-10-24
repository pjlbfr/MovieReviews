package com.moviereviews.reviewes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.moviereviews.ApplicationMR;
import com.moviereviews.R;
import com.moviereviews.objectresponse.Reviews;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsModel implements ReviewsContract.Model{

    private Context context;
    private ReviewsContract.Presenter presenter;
    private int offset = 0;

    public ReviewsModel(Context context, ReviewsContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void setToFirstPage(){
        this.offset = 0;
    }

    @Override
    public void getReviews(){
        ApplicationMR.getApi().getReviews(offset).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                if (response.body() != null) {
                    presenter.setReviews(response.body().getReviews());
                    offset+=20;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getSearchByTitle(String title){
        ApplicationMR.getApi().getSearchTitleReview(title).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                    if (response.body() != null) {
                        presenter.setReviews(response.body().getReviews());
                        offset+=20;
                    }
            }

            @Override
            public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getSearchByPublicationDate(String date){
        ApplicationMR.getApi().getSearchPublicationDateReview(date).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(@NonNull Call<Reviews> call, @NonNull Response<Reviews> response) {
                if (response.body() != null) {
                    presenter.setReviews(response.body().getReviews());
                    offset+=20;
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reviews> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
