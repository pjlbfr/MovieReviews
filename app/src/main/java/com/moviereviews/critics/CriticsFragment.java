package com.moviereviews.critics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moviereviews.R;
import com.moviereviews.critic.CriticActivity;
import com.moviereviews.interfaces.OnCriticCardClickListener;
import com.moviereviews.objectresponse.Critic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

import static butterknife.OnTextChanged.Callback.AFTER_TEXT_CHANGED;

public class CriticsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CriticsContract.View{

    @BindView(R.id.swipe_refresh_critics)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.text_search_critic)
    EditText editTextSearch;
    @BindView(R.id.recycler_critics)
    RecyclerView recyclerView;

    private CriticsContract.Presenter presenter;

    public static CriticsFragment newInstance() {
        return new CriticsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_critics, null);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        presenter.getCriticsObservable(getString(R.string.all_critics));
        return view;
    }

    @Override
    public void setPresenter(CriticsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(List<Critic> critics) {
        initRecycleView(critics);
    }

    private void initRecycleView(List<Critic> critics){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CriticsRecycleViewAdapter criticsRecycleView = new CriticsRecycleViewAdapter(critics, onCriticCardClickListener);
        recyclerView.setAdapter(criticsRecycleView);
    }

    @Override
    public void onRefresh() {
        editTextSearch.setText("");
        swipeRefreshLayout.setRefreshing(false);
    }

    // обработка в edittext поиска по имени
    @OnTextChanged(value = R.id.text_search_critic, callback = AFTER_TEXT_CHANGED)
    void onTextChanged(Editable name) {
        if (!name.toString().isEmpty()){
            presenter.getCriticsObservable(name.toString());
        } else {
            presenter.getCriticsObservable(getString(R.string.all_critics));
        }
    }

    OnCriticCardClickListener onCriticCardClickListener = critic -> {
        Intent intent = new Intent(getContext(), CriticActivity.class);
        intent.putExtra(CriticActivity.TAG, critic);
        if (getContext() != null)
            getContext().startActivity(intent);
    };
}
