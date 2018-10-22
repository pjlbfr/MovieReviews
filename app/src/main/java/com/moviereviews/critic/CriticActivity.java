package com.moviereviews.critic;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;

import com.moviereviews.R;
import com.moviereviews.reviewes.ReviewsRecycleViewAdapter;

import java.util.ArrayList;

public class CriticActivity extends AppCompatActivity{

    public static final String CRITIC_TAG = "Critic_Tag";
    private RecyclerView recyclerView;
    private ReviewsRecycleViewAdapter reviewsRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_critic);

        Intent intent = getIntent();
        ArrayList<String> critic = intent.getStringArrayListExtra(CRITIC_TAG);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            AppCompatTextView textView = (AppCompatTextView) getSupportActionBar().getCustomView().findViewById(R.id.title);
            textView.setText(critic.get(0));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
            getSupportActionBar().setElevation(0);
        }

        CriticFragment criticFragment = CriticFragment.newInstance(critic);
        getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.frame_layout, criticFragment)
                                   .commit();
        CriticPresenter presenter = new CriticPresenter(this, criticFragment);
    }
}
