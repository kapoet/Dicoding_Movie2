package com.ervin.dicoding_movie2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView rvPlaying;
    ArrayList<Movie> dataMovie;

    MovieRvAdapter Result;
    MovieHelper movieHelper;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_playing, container, false);
        rvPlaying = (RecyclerView) v.findViewById(R.id.rv_playing);
        rvPlaying.setHasFixedSize(true);
        getActivity().setTitle("Favorite");
        displayData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayData();

    }

    private void displayData() {
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();
        dataMovie = new ArrayList<>();
        dataMovie = movieHelper.getAllData();
        movieHelper.close();
        rvPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        Result = new MovieRvAdapter(dataMovie,getContext());
        rvPlaying.setAdapter(Result);
    }
}
