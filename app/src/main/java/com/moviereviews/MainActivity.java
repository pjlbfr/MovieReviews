package com.moviereviews;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TabLayout tabLayout;
    private TextView tvReviews;
    private TextView tvCritics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar_title);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviewes)));
            getSupportActionBar().setElevation(0);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsPagerFragmentAdapter pagerAdapter = new TabsPagerFragmentAdapter(getApplicationContext(),getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.colorReviewes));
        tabLayout.setupWithViewPager(viewPager);
    //   ViewGroup.LayoutParams layoutParams = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
      //                                                                     ViewGroup.LayoutParams.WRAP_CONTENT);

        tabLayout.getTabAt(0).setCustomView(R.layout.item_tab_reviews);
        tabLayout.getTabAt(1).setCustomView(R.layout.item_tab_critics);

        tvReviews = (TextView)tabLayout.getTabAt(0).getCustomView().findViewById(R.id.textview_tab_reviews);
        tvCritics = (TextView)tabLayout.getTabAt(1).getCustomView().findViewById(R.id.textview_tab_critics);
      //  tvReviews.setLayoutParams(layoutParams);
      //  tvCritics.setLayoutParams(layoutParams);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        AppCompatTextView textView = (AppCompatTextView) getSupportActionBar().getCustomView().findViewById(R.id.title);

        if (position == 0) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviewes)));
            textView.setText(getResources().getString(R.string.reviewes));
            tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorReviewes));
            tabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.colorReviewes));

            tvReviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorReviewes));
            tvReviews.setBackgroundResource(R.drawable.background_tab_reviews_select);

            tvCritics.setTextColor(Color.WHITE);
            tvCritics.setBackgroundResource(R.drawable.background_tab_critics_unselect);

        } else {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
            textView.setText(getResources().getString(R.string.critics));
            tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));
            tabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));

            tvReviews.setTextColor(Color.WHITE);
            tvReviews.setBackgroundResource(R.drawable.background_tab_reviews_unselect);

            tvCritics.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));
            tvCritics.setBackgroundResource(R.drawable.background_tab_critics_select);


        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
