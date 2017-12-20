package com.ervin.dicoding_movie2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by ervin on 12/10/2017.
 */

public class MovieRvAdapter extends RecyclerView.Adapter<MovieHolder> {
    ArrayList<Movie> listMovie;
    Context context;
    public MovieRvAdapter(ArrayList<Movie> listMovie, Context context) {
        this.listMovie = listMovie;
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        MovieHolder vh = new MovieHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        holder.tvTitle.setText(listMovie.get(position).getTitle());
        holder.tvSynopsis.setText(listMovie.get(position).getSynopsis());
        holder.tvRelease.setText(listMovie.get(position).getRelease());
        String url = "http://image.tmdb.org/t/p/w185"+listMovie.get(position).getPath_image();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .override(350, 550).placeholder(R.mipmap.ic_launcher))
                .into(holder.ivMovie);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = listMovie.get(position);
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra(DetailActivity.DATA_MOVE,movie);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }
}
