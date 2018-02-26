package com.example.paulo.assignment2again.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.paulo.assignment2again.Adapter.RecyclerAdapter;
import com.example.paulo.assignment2again.Model.Muscian;
import com.example.paulo.assignment2again.Model.MusicianDataProvider;
import com.example.paulo.assignment2again.Presenter.MusicianPresenter;
import com.example.paulo.assignment2again.R;
import com.example.paulo.assignment2again.View.MusicianView;
import java.util.ArrayList;



public class Fragment3 extends Fragment implements MusicianView {

    private RecyclerAdapter mAdapter;

    public static Fragment3 newInstance() {

        Bundle args = new Bundle();

        Fragment3 fragment3 = new Fragment3();
        fragment3.setArguments(args);
        return fragment3;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_three, container , false);
        RecyclerView mPopView = view.findViewById(R.id.recycler_View3);
        final SwipeRefreshLayout mSwipe2Refresh = view.findViewById(R.id.swipe_3);
        mSwipe2Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mAdapter.removeAllMusicians();

                mSwipe2Refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mSwipe2Refresh.setRefreshing(false);

                        Fragment3 frag = new Fragment3();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, frag, "Fragment Three").commit();

                    }
                }, 1000L);}});

        mAdapter = new RecyclerAdapter(getContext());
        mPopView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPopView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MusicianDataProvider dataProvider = new MusicianDataProvider(getContext());
        dataProvider.init("BaseURL");
        MusicianPresenter presenter = new MusicianPresenter(this, dataProvider);
        presenter.getMusician("pop");
    }

    @Override
    public void onLoadMusicians(ArrayList<Muscian> results) {

        mAdapter.addMoreMusicians(results);
    }
}
