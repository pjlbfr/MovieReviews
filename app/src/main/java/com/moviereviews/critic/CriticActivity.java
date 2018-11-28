package com.moviereviews.critic;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;

import com.moviereviews.R;
import com.moviereviews.objectresponse.Critic;
import com.moviereviews.repository.ReviewsRepository;
import com.moviereviews.repository.local.ReviewsLocalDataSource;
import com.moviereviews.repository.remote.ReviewsRemoteDataSource;

public class CriticActivity extends AppCompatActivity{

    public static final String TAG = CriticActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic);

        Critic critic = getIntent().getParcelableExtra(TAG);

        // установка заголовка приложения именем критика
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            AppCompatTextView textView = (AppCompatTextView) getSupportActionBar().getCustomView().findViewById(R.id.text_title);

            textView.setText(critic.getDisplay_name());

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
            getSupportActionBar().setElevation(0);
        }

        CriticFragment criticFragment = CriticFragment.newInstance(critic);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.frame_layout, criticFragment)
                                   .commit();
        new CriticPresenter(criticFragment,
                            ReviewsRepository.getInstance(ReviewsRemoteDataSource.getInstance(),
                                                          ReviewsLocalDataSource.getInstance(getApplicationContext()))
        );
    }
}
