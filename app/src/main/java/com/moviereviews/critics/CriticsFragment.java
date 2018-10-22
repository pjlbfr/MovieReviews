package com.moviereviews.critics;

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
import com.moviereviews.objectresponse.Critic;

import java.util.List;

public class CriticsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CriticsContract.View{

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
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
        editTextSearch = (EditText) view.findViewById(R.id.tv_search_critic);
        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    presenter.getSearchByName(editTextSearch.getText().toString());
                    criticsRecycleView.clear();
                    return true;
                }
                return false;
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_critics);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        criticsRecycleView = new CriticsRecycleViewAdapter();
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
        presenter.setToFirstPage();
        criticsRecycleView.clear();
        presenter.getCritics();
        swipeRefreshLayout.setRefreshing(false);
    }
}
