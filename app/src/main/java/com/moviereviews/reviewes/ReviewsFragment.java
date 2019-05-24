package com.moviereviews.reviewes;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.moviereviews.R;
import com.moviereviews.Utils;
import com.moviereviews.interfaces.NYTApi;
import com.moviereviews.objectresponse.Critics;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.objectresponse.ReviewsResult;
import com.paginate.Paginate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import dagger.android.support.DaggerFragment;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class ReviewsFragment extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener, Paginate.Callbacks {

    public static final String TAG = ReviewsFragment.class.getSimpleName();
    private final Calendar cal = Calendar.getInstance();

    @BindView(R.id.text_search_reviews)
    EditText editTextSearch;
    @BindView(R.id.text_date)
    TextView textViewDate;
    @BindView(R.id.recycler_reviews)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_reviews)
    SwipeRefreshLayout swipeRefreshLayout;

    private ReviewsRecycleViewAdapter reviewsAdapter;
    private boolean isRefresh = false;
    private Paginate paginate;
    private boolean hasMoreReviews = true;
    private boolean loading = false;
    private ReviewsViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


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
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        textViewDate.setText(getString(R.string.dateFormat, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
        isRefresh = true;

        setupViewModel();

        return view;
    }

    private void setupViewModel(){
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ReviewsViewModel.class);
        viewModel.loadReviews(0, editTextSearch.getText().toString(), "");
        viewModel.result().observe(this, observerResult());
    }

    private Observer<List<Review>> observerReviews(){
        return reviews -> {

        };
    }

    private Observer<Boolean> observerHasMore(){
        return hasMore -> {};
    }

    private Observer<ReviewsResult> observerResult(){
        return result -> {
            this.hasMoreReviews = result.isHas_more();
        //    reviewsAdapter.setData(result.getReviews());
        };
    }

    public void setData(List<Review> reviews, boolean hasMoreReviews) {
        if (isRefresh) {
            initRecycleView(reviews);
            isRefresh = false;
        } else {
            reviewsAdapter.setData(reviews);
        }
        this.hasMoreReviews = hasMoreReviews;
        loading = false;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        refresh(editTextSearch.getText().toString());
        swipeRefreshLayout.setRefreshing(false);
    }

    private void refresh(String title) {
        isRefresh = true;
        viewModel.refreshReviews(title, "");
    }

    public void showMessageIsEmpty() {
        Toast.makeText(getContext(), getResources().getString(R.string.nothing_found), Toast.LENGTH_SHORT).show();
    }

    private void initRecycleView(List<Review> reviews){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsAdapter = new ReviewsRecycleViewAdapter(reviews);
        recyclerView.setAdapter(reviewsAdapter);
        initPaginate();
    }

    private void initPaginate() {
        if (paginate != null)
            return;
        paginate = Paginate.with(recyclerView, this)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .setLoadingListItemCreator(new ReviewsLoadingItemCreator())
                .build();
    }

    // paginate methods
    @Override
    public void onLoadMore() {
        loading = true;
        if (!isRefresh);
            viewModel.loadReviews(reviewsAdapter.getItemCount(), editTextSearch.getText().toString(), "");
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return !hasMoreReviews;
    }

    //edit text search title
    @OnTextChanged(value = R.id.text_search_reviews, callback = AFTER_TEXT_CHANGED)
    void onTextChanged(Editable title) {
        refresh(title.toString());
    }

    @OnClick(R.id.text_date)
    void onClick() {
        String date = textViewDate.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd", Locale.ENGLISH);

        try {
            cal.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (getContext() != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year, monthOfYear, dayOfMonth) -> {
                        String dateFormat1 = getString(R.string.dateFormat, year, monthOfYear + 1, dayOfMonth);
                        textViewDate.setText(dateFormat1);
                        //   reviewsAdapter.clear();
                        //     presenter.getSearchByPublicationDate(datePickerParams.trim().replace("/", "-"));
                    }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        }
    }
}
