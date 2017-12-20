package com.ervin.dicoding_movie2;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ervin on 12/10/2017.
 */

public class MovieHolder extends RecyclerView.ViewHolder {
    ImageView ivMovie;
    TextView tvTitle, tvSynopsis, tvRelease;
    Button btnDetail;

    public MovieHolder(View itemView) {
        super(itemView);
        ivMovie = (ImageView) itemView.findViewById(R.id.img_item_photo);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
        tvSynopsis = (TextView) itemView.findViewById(R.id.tv_item_synopsis);
        tvRelease = (TextView) itemView.findViewById(R.id.tv_item_release);
        btnDetail = (Button) itemView.findViewById(R.id.btn_item_movie);
    }
}
