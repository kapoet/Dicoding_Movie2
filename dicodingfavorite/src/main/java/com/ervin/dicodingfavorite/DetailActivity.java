package com.ervin.dicodingfavorite;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ervin.dicodingfavorite.entity.MovieItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ervin.dicodingfavorite.DatabaseContarct.CONTENT_URI;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.FAVORITE;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.GAMBAR;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.ID_MOVIE;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.JUDUL;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.RELEASE;
import static com.ervin.dicodingfavorite.DatabaseContarct.MovieColumns.SINOPSIS;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ivDetailImage;
    TextView tvDetailTitle,tvDetailsynopsis,tvDetailrelease;
    Button btnFavorite;
    MovieItem movieItem=null;
    String title,sinopsis,relesae,pathImage, id_movie;
    int id = 0;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        btnFavorite = (Button) findViewById(R.id.btn_favorite);
        tvDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvDetailrelease = (TextView) findViewById(R.id.tv_detail_release);
        tvDetailsynopsis = (TextView) findViewById(R.id.tv_detail_synopsis);
        ivDetailImage = (ImageView) findViewById(R.id.iv_detail_poster);
        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) movieItem = new MovieItem(cursor);
                cursor.close();
                btnFavorite.setText("Un-favortie");
                id=movieItem.getId();
            }
        }
        title = movieItem.getTitle();
        id_movie = movieItem.getId_movie();
        sinopsis = movieItem.getSynopsis();
        relesae = movieItem.getRelease();
        pathImage = movieItem.getPath_image();

        tvDetailTitle.setText(title);
        tvDetailsynopsis.setText(sinopsis);
        tvDetailrelease.setText(relesae);
        String url = "http://image.tmdb.org/t/p/w500"+pathImage;
        Glide.with(this).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(ivDetailImage);
        btnFavorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        uri = Uri.parse(CONTENT_URI+"/"+id);
        if(id!=0)
        {
            getContentResolver().delete(uri,null,null);
            btnFavorite.setText("Favorite");
            checkData();
        } else{
            ContentValues values = new ContentValues();
            values.put(JUDUL,title);
            values.put(SINOPSIS,sinopsis);
            values.put(GAMBAR,pathImage);
            values.put(RELEASE,relesae);
            values.put(FAVORITE,"favorite");
            values.put(ID_MOVIE,id_movie);
            getContentResolver().insert(CONTENT_URI,values);
            btnFavorite.setText("Un-favorite");
            checkData();
        }
    }

    private int checkData() {
        Cursor list = getContentResolver().query(CONTENT_URI,null,null,null,null);
        Log.d("list", "checkData: "+list);
        if(list!=null){
            try{
                ArrayList<MovieItem> mArrayList = new ArrayList<>();
                while(list.moveToNext()) {
                    MovieItem a = new MovieItem();
                    a.setId_movie(list.getString(list.getColumnIndex(ID_MOVIE)));
                    a.setId(list.getInt(list.getColumnIndex(_ID)));
                    mArrayList.add(a);
                }
                for(int i=0;i<mArrayList.size();i++){
                    if(mArrayList.get(i).getId_movie().equals(id_movie)){
                        id = mArrayList.get(i).getId();
                        uri = Uri.parse(CONTENT_URI+"/"+id);
                        break;
                    } else {
                        id = 0;
                    }

                }
                if(mArrayList.size()<1){
                    id=0;
                }
            } catch (IllegalStateException a){

            }
        }


        return id;
    }
}
