package com.moviereviews.reviewes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.widget.Toast;

import com.moviereviews.R;
import com.moviereviews.interfaces.LoadPageListener;
import com.moviereviews.objectresponse.Review;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReviewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ReviewsContract.View {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ReviewsContract.Presenter presenter;
    private ReviewsRecycleViewAdapter reviewsAdapter;
    private TextView textViewDate;
    private EditText editTextSearch;
    private boolean isRefresh = false;
    private String title = "";

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
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    title = editTextSearch.getText().toString();
                    presenter.refreshReviews(title);
                    reviewsAdapter.clear();
                    return true;
                }
                return false;
            }
        });

        // обработка нажатия на textview поиска по дате публикации
        textViewDate = (TextView) view.findViewById(R.id.text_date);
        final Calendar cal = Calendar.getInstance();
        textViewDate.setText(getString(R.string.dateFormat, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)));
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = textViewDate.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd", Locale.ENGLISH);

                try {
                    cal.setTime(dateFormat.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (getContext() != null) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    String dateFormat = getString(R.string.dateFormat, year, monthOfYear+1, dayOfMonth);
                                    textViewDate.setText(dateFormat);
                                 //   reviewsAdapter.clear();
                               //     presenter.getSearchByPublicationDate(datePickerParams.trim().replace("/", "-"));
                                }
                            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show();
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsAdapter = new ReviewsRecycleViewAdapter();
        reviewsAdapter.setLoadPageListener(loadPageListener);
        recyclerView.setAdapter(reviewsAdapter);

        presenter.loadReviews(0, editTextSearch.getText().toString(), "");
        swipeRefreshLayout.setRefreshing(false);
        return view;
    }

    @Override
    public void setPresenter(ReviewsContract.Presenter presenter) {
        this.presenter = presenter;
    }


   @Override
    public void setData(List<Review> reviews) {
        if (isRefresh) {
            reviewsAdapter.clear();
            isRefresh = false;
        }
        reviewsAdapter.setData(reviews);
//        reviewsAdapter.setLoaded();
        swipeRefreshLayout.setRefreshing(false);
    }

    LoadPageListener loadPageListener = new LoadPageListener() {
        @Override
        public void loadPage() {
            presenter.loadReviews(reviewsAdapter.getItemCount(), editTextSearch.getText().toString(), "");
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        presenter.refreshReviews(editTextSearch.getText().toString());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessageIsEmpty(){
        Toast.makeText(getContext(), getResources().getString(R.string.nothing_found),Toast.LENGTH_SHORT).show();
    }
}
