package com.moviereviews.critic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.moviereviews.interfaces.LoadPageListener;
import com.moviereviews.objectresponse.Critic;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.reviewes.ReviewsRecycleViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CriticFragment extends Fragment implements CriticContract.View, SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = CriticFragment.class.getSimpleName();
    private ReviewsRecycleViewAdapter reviewsRecycleView;
    private CriticContract.Presenter presenter;
    private Critic critic;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static CriticFragment newInstance(Critic critic){
        CriticFragment fragment = new CriticFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG, critic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            critic = getArguments().getParcelable(TAG);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_critic, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_critic_in_activity);
        // вывод фотографии критика на экран, если есть ссылка на нее
        // иначе выводим картинку critic_default.png
        if (critic.getMultimedia() != null)
            Picasso.get()
                    .load(critic.getMultimedia().getResource().getSrc())
                    .into(imageView);
        else imageView.setImageResource(R.drawable.critic_default);
        // вывод name критика
        TextView nameCritic = (TextView)view.findViewById(R.id.text_display_name_critic);
        nameCritic.setText(critic.getDisplay_name());
        // вывод status критика
        TextView status = (TextView)view.findViewById(R.id.text_status_critic);
        status.setText(critic.getStatus());
        // вывод bio критика, если оно существует,
        // иначе убираем с экрана textview, отображающее его
        final TextView bio = (TextView)view.findViewById(R.id.text_bio_critic);
        if (critic.getBio() != null){
            bio.setText(Html.fromHtml(critic.getBio()));
        } else
            bio.setVisibility(View.GONE);
        CardView cardView = (CardView) view.findViewById(R.id.card_critic);
        // нажатием на карточку, делаем видимой или невидимой bio критика
        // для удобства просмотра списка его рецензий
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (bio.getVisibility() == View.GONE && !bio.getText().toString().isEmpty())
                bio.setVisibility(View.VISIBLE);
            else
                bio.setVisibility(View.GONE);
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_critic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewsRecycleView = new ReviewsRecycleViewAdapter();
        reviewsRecycleView.setLoadPageListener(loadPageListener);
        recyclerView.setAdapter(reviewsRecycleView);
        presenter.setOffsetZero();
        presenter.getReviews(critic.getDisplay_name());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_critic);
        swipeRefreshLayout.setRefreshing(true);
        return view;
    }

    @Override
    public void setData(List<Review> reviews) {
        reviewsRecycleView.setData(reviews);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(CriticContract.Presenter presenter) {
        this.presenter = presenter;
    }

    LoadPageListener loadPageListener = new LoadPageListener() {
        @Override
        public void loadPage() {
            presenter.getReviews(critic.getDisplay_name());
          //  reviewsRecycleView.setLoaded();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
