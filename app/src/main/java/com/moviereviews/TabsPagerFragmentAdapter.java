package com.moviereviews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moviereviews.critics.CriticsFragment;
import com.moviereviews.critics.CriticsPresenter;
import com.moviereviews.objectresponse.Critics;
import com.moviereviews.objectresponse.Resource;
import com.moviereviews.reviewes.ReviewsFragment;
import com.moviereviews.reviewes.ReviewsPresenter;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private Context context;

    public TabsPagerFragmentAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ReviewsFragment fragmentReviews = ReviewsFragment.newInstance();
                new ReviewsPresenter(context,fragmentReviews);
                return fragmentReviews;
            case 1:
                CriticsFragment fragmentCritics = CriticsFragment.newInstance();
                new CriticsPresenter(context, fragmentCritics);
                return fragmentCritics;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Reviewes";
            case 1:
                return "Critics";
            default:
                return null;
        }

    }
}