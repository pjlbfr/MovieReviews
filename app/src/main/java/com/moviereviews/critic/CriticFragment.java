package com.moviereviews.critic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moviereviews.R;
import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.reviewes.ReviewsLoadingItemCreator;
import com.moviereviews.reviewes.ReviewsRecycleViewAdapter;
import com.paginate.Paginate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CriticFragment extends Fragment implements CriticContract.View, Paginate.Callbacks {

    public static final String TAG = CriticFragment.class.getSimpleName();

    @BindView(R.id.text_display_name_critic)
    TextView nameCritic;
    @BindView(R.id.text_status_critic)
    TextView status;
    @BindView(R.id.text_bio_critic)
    TextView bio;
    @BindView(R.id.card_critic)
    CardView cardView;
    @BindView(R.id.recycler_critic)
    RecyclerView recyclerView;
    @BindView(R.id.image_critic_in_activity)
    ImageView imageView;

    private ReviewsRecycleViewAdapter reviewsRecycleView;
    private CriticContract.Presenter presenter;
    private Critic critic;
    private Paginate paginate;
    private boolean hasMoreReviews = true;
    private boolean loading = false;

    public static CriticFragment newInstance(Critic critic) {
        CriticFragment fragment = new CriticFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, critic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            critic = getArguments().getParcelable(TAG);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_critic, null);
        ButterKnife.bind(this, view);

        // вывод фотографии критика на экран, если есть ссылка на нее
        // иначе выводим картинку critic_default.png
        if (critic.getMultimedia() != null)
            Picasso.get()
                    .load(critic.getMultimedia().getResource().getSrc())
                    .into(imageView);
        else imageView.setImageResource(R.drawable.critic_default);

        // вывод name критика
        nameCritic.setText(critic.getDisplay_name());

        // вывод status критика
        status.setText(critic.getStatus());

        // вывод bio критика, если оно существует,
        // иначе убираем с экрана textview, отображающее его
        if (critic.getBio() != null) {
            bio.setText(Html.fromHtml(critic.getBio()));
        } else
            bio.setVisibility(View.GONE);

        initRecycleView();
        return view;
    }

    @Override
    public void setData(List<Review> reviews, boolean hasMoreReviews) {
        loading = false;
        this.hasMoreReviews = hasMoreReviews;
        reviewsRecycleView.setData(reviews);
    }

    @Override
    public void setPresenter(CriticContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void initRecycleView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecycleView = new ReviewsRecycleViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(reviewsRecycleView);
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

    @Override
    public void onLoadMore() {
        loading = true;
        presenter.loadReviewsObservable(reviewsRecycleView.getItemCount(), critic.getDisplay_name());
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return !hasMoreReviews;
    }

    @OnClick(R.id.card_critic)
    void onClickCard() {
        if (bio.getVisibility() == View.GONE && !bio.getText().toString().isEmpty())
            bio.setVisibility(View.VISIBLE);
        else
            bio.setVisibility(View.GONE);
    }
}
