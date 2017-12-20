package com.ervin.dicoding_movie2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.FAVORITE;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.GAMBAR;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.ID_MOVIE;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.JUDUL;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.RELEASE;
import static com.ervin.dicoding_movie2.DatabaseContarct.MovieColumns.SINOPSIS;
import static com.ervin.dicoding_movie2.DatabaseContarct.TABLE_NAME;

/**
 * Created by ervin on 12/20/2017.
 */

public class MovieHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Movie> getAllData(){
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,_ID+ " DESC",null);
        cursor.moveToFirst();
        ArrayList<Movie> arrayList = new ArrayList<>();
        Movie movie;
        if (cursor.getCount()>0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                movie.setRelease(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                movie.setSynopsis(cursor.getString(cursor.getColumnIndexOrThrow(SINOPSIS)));
                movie.setPath_image(cursor.getString(cursor.getColumnIndexOrThrow(GAMBAR)));
                movie.setFavorite(cursor.getString(cursor.getColumnIndexOrThrow(FAVORITE)));
                movie.setId_movie(cursor.getString(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }
    public long insert(Movie movie){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(JUDUL, movie.getTitle());
        initialValues.put(RELEASE, movie.getRelease());
        initialValues.put(SINOPSIS, movie.getSynopsis());
        initialValues.put(GAMBAR, movie.getPath_image());
        initialValues.put(FAVORITE, movie.getFavorite());
        initialValues.put(ID_MOVIE, movie.getId_movie());
        return database.insert(TABLE_NAME, null, initialValues);
    }

    public int delete(int id){
        return database.delete(TABLE_NAME, _ID + " = '"+id+"'", null);
    }
}
