package com.moviereviews;

import android.app.ActionBar;
import android.database.Observable;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

import com.moviereviews.interfaces.NYTApi;
import com.moviereviews.objectresponse.Critics;
import com.moviereviews.realm.RealmRequests;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.text_tab_reviews_new)
    TextView tvReviews;
    @BindView(R.id.text_tab_critics_new)
    TextView tvCritics;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviews)));
            getSupportActionBar().setElevation(0);
        }

        final TabsPagerFragmentAdapter pagerAdapter = new TabsPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (getSupportActionBar() != null) {
            AppCompatTextView tvTitle = (AppCompatTextView) getSupportActionBar().getCustomView().findViewById(R.id.text_title);
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

            if (position == 0) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviews)));
                tvTitle.setText(getString(R.string.reviews));
                appBarLayout.setBackgroundResource(R.color.colorReviews);

                tvReviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorReviews));
                tvReviews.setBackgroundResource(R.drawable.tab_background_reviews_selected);

                tvCritics.setTextColor(Color.WHITE);
                tvCritics.setBackgroundResource(R.drawable.tab_background_critics_unselected);
            } else {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
                tvTitle.setText(getString(R.string.critics));
                appBarLayout.setBackgroundResource(R.color.colorCritics);

                tvReviews.setTextColor(Color.WHITE);
                tvReviews.setBackgroundResource(R.drawable.tab_background_reviews_unselected);

                tvCritics.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));
                tvCritics.setBackgroundResource(R.drawable.tab_background_critics_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.text_tab_reviews_new, R.id.text_tab_critics_new})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_tab_reviews_new:
                viewPager.setCurrentItem(0);
                break;
            case R.id.text_tab_critics_new:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
