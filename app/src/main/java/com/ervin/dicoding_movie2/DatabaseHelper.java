package com.ervin.dicoding_movie2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_FAVORITE = "create table "+TABLE_NAME+
            " ("+_ID+" integer primary key autoincrement, " +
            JUDUL+" text not null, " +
            SINOPSIS+" text not null, " +
            RELEASE+" text not null, " +
            GAMBAR+" text not null, " +
            FAVORITE+" text not null, " +
            ID_MOVIE+" text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
