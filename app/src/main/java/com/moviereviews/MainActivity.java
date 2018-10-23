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
import android.view.LayoutInflater;
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
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviews)));
            getSupportActionBar().setElevation(0);
        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsPagerFragmentAdapter pagerAdapter = new TabsPagerFragmentAdapter(getApplicationContext(),getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.colorReviews));
        tabLayout.setupWithViewPager(viewPager);

   /*     View v = LayoutInflater.from(this).inflate(R.layout.view_goal_tab_active, null);
        TextView tv = (TextView)v.findViewById(R.id.goal_tab_active_tv);
        tv.setSelected(true);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTabs.getTabAt(0).setCustomView(v);
*/

        //View view = LayoutInflater.from(this).inflate(R.layout.item_tab_reviews, null);
        //TextView tv = (TextView) view.findViewById(R.id.text_tab_reviews);
        //view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
         //                                               ViewGroup.LayoutParams.WRAP_CONTENT));
        //tabLayout.getTabAt(0).setCustomView(view);

        tvReviews = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab_reviews, null);
        tvReviews.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tabLayout.getTabAt(0).setCustomView(tvReviews);

        tvCritics = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tab_critics, null);
        tvCritics.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        tabLayout.getTabAt(1).setCustomView(tvCritics);

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        AppCompatTextView textView = (AppCompatTextView) getSupportActionBar().getCustomView().findViewById(R.id.text_title);

        if (position == 0) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorReviews)));
            textView.setText(getResources().getString(R.string.reviews));
            tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorReviews));
            tabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.colorReviews));

            tvReviews.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorReviews));
            tvReviews.setBackgroundResource(R.drawable.tab_background_reviews_selected);

            tvCritics.setTextColor(Color.WHITE);
            tvCritics.setBackgroundResource(R.drawable.tab_background_critics_unselected);
        } else {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorCritics)));
            textView.setText(getResources().getString(R.string.critics));
            tabLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));
            tabLayout.setTabTextColors(Color.WHITE, ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));

            tvReviews.setTextColor(Color.WHITE);
            tvReviews.setBackgroundResource(R.drawable.tab_background_reviews_unselected);

            tvCritics.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorCritics));
            tvCritics.setBackgroundResource(R.drawable.tab_background_critics_selected);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
