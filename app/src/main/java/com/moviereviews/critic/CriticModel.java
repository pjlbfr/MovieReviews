package com.moviereviews.critic;

import android.content.Context;
import android.widget.Toast;

import com.moviereviews.ApplicationMR;
import com.moviereviews.R;
import com.moviereviews.objectresponse.Reviews;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriticModel implements CriticContract.Model{

    private Context context;
    private CriticContract.Presenter presenter;
    private int offset = 0;

    public CriticModel(Context context, CriticContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void setOffsetZero(){
        this.offset = 0;
    }

    @Override
    public void getReviews(String name) {
        ApplicationMR.getApi().getSearchCriticReviews(name, offset).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                if (response.body() != null){
                    presenter.setReviews(response.body().getReviews());
                    offset+=20;
                }
            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
