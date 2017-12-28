package com.ervin.dicoding_movie2;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ervin.dicoding_movie2.DatabaseContarct.CONTENT_URI;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.FAVORITE;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.GAMBAR;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.ID_MOVIE;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.JUDUL;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.RELEASE;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.SINOPSIS;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String DATA_MOVE = "data";
    ImageView ivDetailImage;
    TextView tvDetailTitle,tvDetailsynopsis,tvDetailrelease;
    Button btnFavorite;
    MovieHelper movieHelper;
    String title,sinopsis,relesae,pathImage, id_movie;
    int id = 0;
    Movie movie;
    Uri uri;
    int check =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle(R.string.detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivDetailImage = (ImageView) findViewById(R.id.iv_detail_poster);
        movieHelper = new MovieHelper(this);
        movieHelper.open();
        btnFavorite = (Button) findViewById(R.id.btn_favorite);
        tvDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvDetailrelease = (TextView) findViewById(R.id.tv_detail_release);
        tvDetailsynopsis = (TextView) findViewById(R.id.tv_detail_synopsis);
        uri = getIntent().getData();
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) movie = new Movie(cursor);
                cursor.close();
                btnFavorite.setText("Un-favortie");
                id=movie.getId();
            }

        } else{
            movie = getIntent().getParcelableExtra(DATA_MOVE);
            check=checkData();
            if(check!=0){
                btnFavorite.setText("Un-favortie");
            }
        }
        title = movie.getTitle();
        id_movie = movie.getId_movie();
        sinopsis = movie.getSynopsis();
        relesae = movie.getRelease();
        pathImage = movie.getPath_image();

        tvDetailTitle.setText(title);
        tvDetailsynopsis.setText(sinopsis);
        tvDetailrelease.setText(relesae);
        String url = "http://image.tmdb.org/t/p/w500"+pathImage;
        Glide.with(this).load(url).apply(new RequestOptions().placeholder(R.mipmap.ic_launcher)).into(ivDetailImage);

        btnFavorite.setOnClickListener(this);
    }

    private int checkData() {
        Cursor list = getContentResolver().query(CONTENT_URI,null,null,null,null);
        Log.d("list", "checkData: "+list);
        if(list!=null){
            try{
                ArrayList<Movie> mArrayList = new ArrayList<>();
                while(list.moveToNext()) {
                    Movie a = new Movie(list.getString(list.getColumnIndex(ID_MOVIE)),list.getInt(list.getColumnIndex(_ID)));
                    mArrayList.add(a);
                }
                for(int i=0;i<mArrayList.size();i++){
                    if(mArrayList.get(i).getId_movie().equals(movie.getId_movie())){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
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
        Intent intent = new Intent(DetailActivity.this, StackWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
