package com.ervin.dicoding_movie2;

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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String DATA_MOVE = "data";
    ImageView ivDetailImage;
    TextView tvDetailTitle,tvDetailsynopsis,tvDetailrelease;
    Button btnFavorite;
    MovieHelper movieHelper;
    String title,sinopsis,relesae,pathImage, id_movie;
    int id = 0;
    ArrayList<Movie> dataMovie;
    Movie movie;
    int check=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.setTitle(R.string.detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivDetailImage = (ImageView) findViewById(R.id.iv_detail_poster);
        movieHelper = new MovieHelper(this);
        movieHelper.open();
        dataMovie = new ArrayList<>();
        dataMovie = movieHelper.getAllData();
        movieHelper.close();
        btnFavorite = (Button) findViewById(R.id.btn_favorite);
        tvDetailTitle = (TextView) findViewById(R.id.tv_detail_title);
        tvDetailrelease = (TextView) findViewById(R.id.tv_detail_release);
        tvDetailsynopsis = (TextView) findViewById(R.id.tv_detail_synopsis);
        movie = getIntent().getParcelableExtra(DATA_MOVE);
        for(int i=0;i<dataMovie.size();i++){
            if(dataMovie.get(i).getId_movie().equals(movie.getId_movie())){
                btnFavorite.setText("Un-favorite");                     //salahnya di databasenya. coba buat idmovienya ntar
                id = dataMovie.get(i).getId();
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

    private void checkData() {
        movieHelper.open();
        ArrayList<Movie> dataMovi=movieHelper.getAllData();
        movieHelper.close();
        for(int i=0;i<dataMovi.size();i++){
            if(dataMovi.get(i).getId_movie().equals(movie.getId_movie())){
                id = dataMovi.get(i).getId();
                break;
            } else {
                id = 0;
            }

        }
        if(dataMovi.size()<1){
            id=0;
        }
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
            movieHelper.open();
            movieHelper.delete(id);
            movieHelper.close();
            btnFavorite.setText("Favorite");
            checkData();
        } else{
            Movie movie = new Movie();
            movie.setPath_image(pathImage);
            movie.setSynopsis(sinopsis);
            movie.setTitle(title);
            movie.setRelease(relesae);
            movie.setFavorite("favorite");
            movie.setId_movie(id_movie);
            movieHelper.open();
            movieHelper.insert(movie);
            movieHelper.close();
            btnFavorite.setText("Un-favorite");
            checkData();
        }

    }


}
