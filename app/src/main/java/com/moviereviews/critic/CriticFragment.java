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
import com.moviereviews.interfaces.LoadPageListener;
import com.moviereviews.objectresponse.Review;
import com.moviereviews.reviewes.ReviewsRecycleViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CriticFragment extends Fragment implements CriticContract.View {

    public static final String TAG = CriticFragment.class.getSimpleName();
    private ReviewsRecycleViewAdapter reviewsRecycleView;
    private CriticContract.Presenter presenter;
    private ArrayList<String> critic;

    public static CriticFragment newInstance(ArrayList<String> critic) {
        CriticFragment fragment = new CriticFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(TAG, critic);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            critic = getArguments().getStringArrayList(TAG);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_critic, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_critic_in_activity);
        // вывод фотографии критика на экран, если есть ссылка на нее
        // иначе выводим картинку critic_default.png
        if (critic.size() == 4)
            Picasso.get()
                    .load(critic.get(3))
                    .into(imageView);
        else imageView.setImageResource(R.drawable.critic_default);
        // вывод name критика
        TextView nameCritic = (TextView)view.findViewById(R.id.text_display_name_critic);
        nameCritic.setText(critic.get(0));
        // вывод status критика
        TextView status = (TextView)view.findViewById(R.id.text_status_critic);
        status.setText(critic.get(1));
        // вывод bio критика, если оно существует,
        // иначе убираем с экрана textview, отображающее его
        final TextView bio = (TextView)view.findViewById(R.id.text_bio_critic);
        if (!critic.get(2).isEmpty()){
            bio.setText(Html.fromHtml(critic.get(2)));
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
        reviewsRecycleView.setCanLoadMore(true);
        reviewsRecycleView.setLoadPageListener(loadPageListener);
        recyclerView.setAdapter(reviewsRecycleView);
        presenter.setOffsetZero();
        presenter.getReviews(critic.get(0));
        return view;
    }

    @Override
    public void setData(List<Review> reviews) {
        if (reviews.size() < 20)
            reviewsRecycleView.setCanLoadMore(false);
        reviewsRecycleView.setData(reviews);
    }

    @Override
    public void setPresenter(CriticContract.Presenter presenter) {
        this.presenter = presenter;
    }

    LoadPageListener loadPageListener = new LoadPageListener() {
        @Override
        public void loadPage() {
            presenter.getReviews(critic.get(0));
            reviewsRecycleView.setLoaded();
        }
    };
}
