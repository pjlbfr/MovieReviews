package com.moviereviews.models;

import android.content.Context;
import android.widget.Toast;

import com.moviereviews.AppplicationMR;
import com.moviereviews.R;
import com.moviereviews.objectresponse.Reviews;
import com.moviereviews.reviewes.ReviewsContract;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Model {

    private Context context;
    private ReviewsContract.Presenter presenter;
    private int page = 0;

    public Model(Context context, ReviewsContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void setToFirstPage(){
        this.page = 0;
    }

    public void getReviews(){
        AppplicationMR.getApi().getReviews(page).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.body() != null) {
                    presenter.setReviews(response.body().getReviews());
                    page+=20;
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getSearchByTitle(String title){
        AppplicationMR.getApi().getSearchTitleReview(title).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                    if (response.body() != null) {
                        presenter.setReviews(response.body().getReviews());
                        page+=20;
                    }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getSearchByPublicationDate(String date){
        AppplicationMR.getApi().getSearchPublicationDateReview(date).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.body() != null) {
                    presenter.setReviews(response.body().getReviews());
                    page+=20;
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
