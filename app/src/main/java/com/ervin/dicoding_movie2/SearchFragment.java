package com.ervin.dicoding_movie2;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
public class SearchFragment extends Fragment implements View.OnClickListener {
RecyclerView rvSearch;
ArrayList<Movie> dataMovie;
Button btnSearch;
EditText etSearch;
    ProgressBar pb;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().setTitle(R.string.search);
        rvSearch = (RecyclerView) v.findViewById(R.id.rv_search);
        rvSearch.setHasFixedSize(true);
        dataMovie = new ArrayList<>();
        btnSearch = (Button) v.findViewById(R.id.btn_search);
        etSearch = (EditText) v.findViewById(R.id.et_movie);
        pb= (ProgressBar) v.findViewById(R.id.pb_search);
        btnSearch.setOnClickListener(this);
        if(savedInstanceState!=null){
            dataMovie = savedInstanceState.getParcelableArrayList("listview.state");
            notifyDataChange();
        }
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_search :
                dataMovie.clear();
                String keyword = etSearch.getText().toString();
                String url = "https://api.themoviedb.org/3/search/movie?api_key="+BuildConfig.TMDB_API+"&language=en-US&query="+keyword;
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        dismissKeyboard(getActivity());
                        pb.setVisibility(View.VISIBLE);
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String result = new String(responseBody);
                        try{
                            JSONObject responseObject = new JSONObject(result);
                            JSONArray list = responseObject.getJSONArray("results");
                            for(int i=0;i<list.length();i++){
                                JSONObject infoMovie = list.getJSONObject(i);
                                Movie movie = new Movie(infoMovie.getString("title"),infoMovie.getString("release_date"),infoMovie.getString("overview"),infoMovie.getString("poster_path"));
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
                    //    loading.setVisibility(View.GONE);
                        pb.setVisibility(View.GONE);
                        notifyDataChange();
                        super.onFinish();
                    }
                });
                break;
        }
    }

    private void notifyDataChange() {
        rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieRvAdapter Result = new MovieRvAdapter(dataMovie,getContext());
        rvSearch.setAdapter(Result);
    }
    public void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("listview.state", (ArrayList<? extends Parcelable>) dataMovie);
    }
}
