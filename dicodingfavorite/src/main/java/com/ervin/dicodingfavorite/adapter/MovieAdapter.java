package com.ervin.dicodingfavorite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ervin.dicodingfavorite.R;

import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.GAMBAR;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.JUDUL;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.RELEASE;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.SINOPSIS;
import static com.ervin.dicodingfavorite.DatabaseContarct.getColumnString;

/**
 * Created by ervin on 12/21/2017.
 */

public class MovieAdapter extends CursorAdapter {

    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View itemView, Context context, Cursor cursor) {
        if (cursor != null){
            ImageView ivMovie = (ImageView) itemView.findViewById(R.id.img_item_photo);
            TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
            TextView tvSynopsis = (TextView) itemView.findViewById(R.id.tv_item_synopsis);
            TextView tvRelease = (TextView) itemView.findViewById(R.id.tv_item_release);
            tvTitle.setText(getColumnString(cursor,JUDUL));
            tvSynopsis.setText(getColumnString(cursor,SINOPSIS));
            tvRelease.setText(getColumnString(cursor,RELEASE));
            String url = "http://image.tmdb.org/t/p/w185"+getColumnString(cursor,GAMBAR);
            Glide.with(context)
                    .load(url)
                    .apply(new RequestOptions()
                            .override(350, 550).placeholder(R.mipmap.ic_launcher))
                    .into(ivMovie);
        }
    }
}
