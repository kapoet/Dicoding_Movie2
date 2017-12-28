package com.ervin.dicoding_movie2;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import static com.ervin.dicoding_movie2.DatabaseContarct.CONTENT_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    RecyclerView rvPlaying;
    ArrayList<Movie> dataMovie;
    private Cursor list;
    FavoriteAdapter Result;

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
        Result = new FavoriteAdapter(getContext());
        displayData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayData();

    }

    private void displayData() {
        rvPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        new LoadNoteAsync().execute();
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContext().getContentResolver().query(CONTENT_URI,null,null,null,null);

        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            list = cursor;
            Result.setListMovie(list);
            rvPlaying.setAdapter(Result);
            Result.notifyDataSetChanged();
        }
    }
}
