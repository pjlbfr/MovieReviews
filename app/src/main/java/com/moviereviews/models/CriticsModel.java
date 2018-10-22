package com.moviereviews.models;

import android.content.Context;
import android.widget.Toast;

import com.moviereviews.AppplicationMR;
import com.moviereviews.R;
import com.moviereviews.critics.CriticsContract;
import com.moviereviews.objectresponse.Critics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriticsModel {

    private Context context;
    private CriticsContract.Presenter presenter;
    private int page = 0;

    public CriticsModel(Context context, CriticsContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    public void setToFirstPage(){
        this.page = 0;
    }

    public void getCritics(){
        AppplicationMR.getApi().getCritics().enqueue(new Callback<Critics>() {
            @Override
            public void onResponse(Call<Critics> call, Response<Critics> response) {
                if (response.body() != null){
                    presenter.setCritics(response.body().getCritics());
                }
            }

            @Override
            public void onFailure(Call<Critics> call, Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getSearchByName(String name){
        AppplicationMR.getApi().getSearchNameCritic(name).enqueue(new Callback<Critics>() {
            @Override
            public void onResponse(Call<Critics> call, Response<Critics> response) {
                if (response.body() != null){
                    presenter.setCritics(response.body().getCritics());
                }
            }

            @Override
            public void onFailure(Call<Critics> call, Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
