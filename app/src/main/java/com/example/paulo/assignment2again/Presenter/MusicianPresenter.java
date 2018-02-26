package com.example.paulo.assignment2again.Presenter;



import com.example.paulo.assignment2again.Model.Muscian;
import com.example.paulo.assignment2again.Model.MusicianDataProvider;
import com.example.paulo.assignment2again.Model.OnDataProviderCallback;
import com.example.paulo.assignment2again.View.MusicianView;

import java.util.ArrayList;

/**
 * Created by paulo on 23/02/2018.
 */

public class MusicianPresenter {

    private final MusicianView view;
    private final MusicianDataProvider dataProvider;

    public MusicianPresenter(MusicianView view, MusicianDataProvider dataProvider) {
        this.view = view;
        this.dataProvider = dataProvider;
    }

    public void getMusician(String term) {
        this.dataProvider.getMusicianData(term, new OnDataProviderCallback() {
            @Override
            public void onProvideList(ArrayList<Muscian> results) {
                view.onLoadMusicians(results);
            }
        });
    }
}
