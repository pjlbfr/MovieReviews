package com.moviereviews;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TextView tvReviews;
    private TextView tvCritics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviews)));
            getSupportActionBar().setElevation(0);
        }
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final TabsPagerFragmentAdapter pagerAdapter = new TabsPagerFragmentAdapter(getApplicationContext(),getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);

        tvCritics = (TextView) findViewById(R.id.text_tab_critics_new);
        tvCritics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        tvReviews = (TextView) findViewById(R.id.text_tab_reviews_new);
        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
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
                tvTitle.setText(getResources().getString(R.string.reviews));
                appBarLayout.setBackgroundResource(R.color.colorReviews);

                tvReviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorReviews));
                tvReviews.setBackgroundResource(R.drawable.tab_background_reviews_selected);

                tvCritics.setTextColor(Color.WHITE);
                tvCritics.setBackgroundResource(R.drawable.tab_background_critics_unselected);
            } else {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
                tvTitle.setText(getResources().getString(R.string.critics));
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
}
