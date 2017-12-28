package com.ervin.dicoding_movie2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.ervin.dicoding_movie2.DatabaseContarct.CONTENT_URI;

/**
 * Created by ervin on 12/21/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<MovieHolder> {
    Context context;
    private Cursor list;

    public FavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setListMovie(Cursor listMo) {
        this.list = listMo;
    }
    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        MovieHolder vh = new MovieHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, final int position) {
        final Movie movie = getItem(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvSynopsis.setText(movie.getSynopsis());
        holder.tvRelease.setText(movie.getRelease());
        String url = "http://image.tmdb.org/t/p/w185"+movie.getPath_image();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .override(350, 550).placeholder(R.mipmap.ic_launcher))
                .into(holder.ivMovie);
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(CONTENT_URI+"/"+movie.getId());
                Intent intent = new Intent(context,DetailActivity.class);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    private Movie getItem(int position){
        if (!list.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(list);
    }
}
