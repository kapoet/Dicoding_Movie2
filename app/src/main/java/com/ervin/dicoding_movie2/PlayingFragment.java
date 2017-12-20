package com.ervin.dicoding_movie2;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayingFragment extends Fragment {
    RecyclerView rvPlaying;
    ArrayList<Movie> dataMovie;
    ProgressBar pb;
    MovieRvAdapter Result;

    public PlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_playing, container, false);
        getActivity().setTitle(R.string.now_playing);
        rvPlaying = (RecyclerView) v.findViewById(R.id.rv_playing);
        pb = (ProgressBar) v.findViewById(R.id.pb_playing);
        rvPlaying.setHasFixedSize(true);
        dataMovie = new ArrayList<>();
        Log.d("coba", "onCreateView: "+savedInstanceState);
        if(savedInstanceState!=null){
            dataMovie = savedInstanceState.getParcelableArrayList("listview.state");
            notifyDataChange();
        } else{
            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+BuildConfig.TMDB_API+"&language=en-US";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    pb.setVisibility(View.VISIBLE);
                    super.onStart();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String hasil = new String(responseBody);
                    try {
                        JSONObject a =  new JSONObject(hasil);
                        JSONArray b = a.getJSONArray("results");
                        for (int i=0;i<b.length();i++){
                            JSONObject c = b.getJSONObject(i);
                            Movie movie = new Movie(c.getString("title"),c.getString("release_date"),c.getString("overview"),c.getString("poster_path"),c.getString("id"));
                            dataMovie.add(movie);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }

                @Override
                public void onFinish() {
                    pb.setVisibility(View.GONE);
                    notifyDataChange();
                    super.onFinish();
                }
            });
        }


        return v;
    }
    private void notifyDataChange() {
        rvPlaying.setLayoutManager(new LinearLayoutManager(getContext()));
        Result = new MovieRvAdapter(dataMovie,getContext());
        rvPlaying.setAdapter(Result);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listview.state", (ArrayList<? extends Parcelable>) dataMovie);
    }
}

