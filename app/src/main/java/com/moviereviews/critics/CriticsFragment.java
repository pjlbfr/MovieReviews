package com.moviereviews.critics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moviereviews.R;
import com.moviereviews.critic.CriticActivity;
import com.moviereviews.interfaces.OnCriticCardClickListener;
import com.moviereviews.objectresponse.Critic;

import java.util.List;

public class CriticsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CriticsContract.View{

    private SwipeRefreshLayout swipeRefreshLayout;
    private CriticsContract.Presenter presenter;
    private CriticsRecycleViewAdapter criticsRecycleView;
    private EditText editTextSearch;

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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_critics);
        swipeRefreshLayout.setOnRefreshListener(this);
        // обработка в edittext поиска по имени
        editTextSearch = (EditText) view.findViewById(R.id.text_search_critic);
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    if (!editTextSearch.getText().toString().isEmpty())
                        presenter.getSearchByName(editTextSearch.getText().toString());
                    else
                        presenter.getCritics();
                    criticsRecycleView.clear();
                    return true;
                }
                return false;
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_critics);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        criticsRecycleView = new CriticsRecycleViewAdapter();
        criticsRecycleView.setOnCriticCardClickListener(onCriticCardClickListener);
        recyclerView.setAdapter(criticsRecycleView);
        presenter.getCritics();
        return view;
    }

    @Override
    public void setPresenter(CriticsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(List<Critic> critics) {
        criticsRecycleView.setData(critics);
    }

    @Override
    public void onRefresh() {
        criticsRecycleView.clear();
        presenter.getCritics();
        swipeRefreshLayout.setRefreshing(false);
    }

    OnCriticCardClickListener onCriticCardClickListener = new OnCriticCardClickListener() {
        @Override
        public void onClick(Critic critic) {
            Intent intent = new Intent(getContext(), CriticActivity.class);
            intent.putExtra(Critic.class.getSimpleName(), critic);
            getContext().startActivity(intent);
        }
    };
}
