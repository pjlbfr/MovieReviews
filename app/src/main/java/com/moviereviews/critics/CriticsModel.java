package com.moviereviews.critics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.moviereviews.ApplicationMR;
import com.moviereviews.R;
import com.moviereviews.objectresponse.Critics;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CriticsModel implements CriticsContract.Model {

    private Context context;
    private CriticsContract.Presenter presenter;

    public CriticsModel(Context context, CriticsContract.Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void getCritics(){
        ApplicationMR.getApi().getCritics().enqueue(new Callback<Critics>() {
            @Override
            public void onResponse(@NonNull Call<Critics> call, @NonNull Response<Critics> response) {
                if (response.body() != null){
                    presenter.setCritics(response.body().getCritics());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Critics> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void getSearchByName(String name){
        ApplicationMR.getApi().getSearchNameCritic(name).enqueue(new Callback<Critics>() {
            @Override
            public void onResponse(@NonNull Call<Critics> call, @NonNull  Response<Critics> response) {
                if (response.body() != null){
                    presenter.setCritics(response.body().getCritics());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Critics> call, @NonNull  Throwable t) {
                Toast.makeText(context, context.getResources().getText(R.string.error_message), Toast.LENGTH_LONG).show();
            }
        });
    }
}
