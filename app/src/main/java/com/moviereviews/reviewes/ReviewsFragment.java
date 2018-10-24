package com.moviereviews.reviewes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.DatePickerDialog;

import com.moviereviews.R;
import com.moviereviews.interfaces.LoadPageListener;
import com.moviereviews.objectresponse.Review;

import java.util.List;

public class ReviewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,ReviewsContract.View{

    private SwipeRefreshLayout swipeRefreshLayout;
    private ReviewsContract.Presenter presenter;
    private ReviewsRecycleViewAdapter reviewsRecycleView;
    private TextView textViewDate;
    private EditText editTextSearch;

    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_reviews);
        swipeRefreshLayout.setOnRefreshListener(this);

        // обработка введенных данных в textview поиска по имени
        editTextSearch = (EditText) view.findViewById(R.id.text_search_reviews);
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                Log.d("TAG" , "НАЖАТО ПОИСК");
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    presenter.setToFirstPage();
                    presenter.getSearchByTitle(editTextSearch.getText().toString());
                    reviewsRecycleView.clear();
                    return true;
                }
                return false;
            }
        });
        // обработка нажатия на textview поиска по дате публикации
        textViewDate = (TextView) view.findViewById(R.id.text_date);
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = textViewDate.getText().toString();
                Integer year = Integer.parseInt(date.substring(0, 4));
                Integer month = Integer.parseInt(date.substring(7,9))-1;
                Integer day = Integer.parseInt(date.substring(12));
                if (getContext() != null) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    String datePickerParams = year + " / "
                                            + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + " / "
                                            + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);
                                    textViewDate.setText(datePickerParams);
                                    presenter.setToFirstPage();
                                    reviewsRecycleView.clear();
                                    presenter.getSearchByPublicationDate(datePickerParams.trim().replace("/", "-"));
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecycleView = new ReviewsRecycleViewAdapter();
        reviewsRecycleView.setCanLoadMore(true);
        reviewsRecycleView.setLoadPageListener(loadPageListener);
        recyclerView.setAdapter(reviewsRecycleView);
        presenter.setToFirstPage();
        presenter.getReviews();
        swipeRefreshLayout.setRefreshing(true);
        return view;
    }

    @Override
    public void setPresenter(ReviewsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(List<Review> reviews) {
        int pageSize = 20;
        if (reviews.size() < pageSize)
            reviewsRecycleView.setCanLoadMore(false);
        reviewsRecycleView.setData(reviews);
        swipeRefreshLayout.setRefreshing(false);
    }

    LoadPageListener loadPageListener = new LoadPageListener() {
        @Override
        public void loadPage() {
            presenter.getReviews();
            reviewsRecycleView.setLoaded();
            swipeRefreshLayout.setRefreshing(true);
        }
    };

    @Override
    public void onRefresh() {
        presenter.setToFirstPage();
        reviewsRecycleView.clear();
        presenter.getReviews();
        swipeRefreshLayout.setRefreshing(false);
    }
}
