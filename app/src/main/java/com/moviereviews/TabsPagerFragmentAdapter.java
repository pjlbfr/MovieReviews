package com.moviereviews;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moviereviews.critics.CriticsFragment;
import com.moviereviews.critics.CriticsPresenter;
import com.moviereviews.reviewes.ReviewsFragment;

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

//    @Inject
//    ReviewsPresenter reviewsPresenter;
//    @Inject
//    ReviewsRepository reviewsRepository;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // вкладка рецензии
            case 0:
                return ReviewsFragment.newInstance();

            // вкладка критики
            case 1:
                CriticsFragment fragmentCritics = CriticsFragment.newInstance();
                new CriticsPresenter(fragmentCritics);
                return fragmentCritics;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}