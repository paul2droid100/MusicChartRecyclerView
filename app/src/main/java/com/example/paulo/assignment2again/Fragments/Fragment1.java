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


public class Fragment1 extends Fragment implements MusicianView {


    private RecyclerAdapter mAdapter;

    public static Fragment1 newInstance() {

        Bundle args = new Bundle();

        Fragment1 fragment1 = new Fragment1();
        fragment1.setArguments(args);


        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_one, container , false);
        final SwipeRefreshLayout mSwipe2Refresh = view.findViewById(R.id.swipe_it);
        mSwipe2Refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mAdapter.removeAllMusicians();

                mSwipe2Refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mSwipe2Refresh.setRefreshing(false);

                        Fragment1 frag = new Fragment1();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_holder, frag, "Fragment One").commit();

                    }
                }, 1000L);}});

        RecyclerView mRockView = view.findViewById(R.id.recycler_View1);
        mAdapter = new RecyclerAdapter(getContext());
        mRockView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRockView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MusicianDataProvider dataProvider = new MusicianDataProvider(getContext());
        dataProvider.init("BaseURL");
        MusicianPresenter presenter = new MusicianPresenter(this, dataProvider);
        presenter.getMusician("rock");
    }

    @Override
    public void onLoadMusicians(ArrayList<Muscian> results) {
        mAdapter.addMoreMusicians(results);
        mAdapter.notifyDataSetChanged();
    }

}
