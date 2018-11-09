package com.moviereviews;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.moviereviews.critics.CriticsFragment;
import com.moviereviews.critics.CriticsPresenter;
import com.moviereviews.repository.ReviewsRepository;
import com.moviereviews.repository.local.ReviewsLocalDataSource;
import com.moviereviews.repository.remote.ReviewsRemoteDataSource;
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
            // вкладка рецензии
            case 0:
                ReviewsFragment fragmentReviews = ReviewsFragment.newInstance();
                new ReviewsPresenter(fragmentReviews,
                                     ReviewsRepository.getInstance(context,
                                                                   ReviewsRemoteDataSource.getInstance(),
                                                                   ReviewsLocalDataSource.getInstance(context)));
                return fragmentReviews;
             // вкладка критики
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

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}