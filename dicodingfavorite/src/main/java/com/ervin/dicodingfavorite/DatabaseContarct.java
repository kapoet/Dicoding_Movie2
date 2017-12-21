package com.ervin.dicodingfavorite;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ervin on 12/20/2017.
 */

public class DatabaseContarct {

    public static String TABLE_NAME = "table_favorite";

    public static final class MovieColumns implements BaseColumns {


        public static String JUDUL = "nama";
        // Mahasiswa nim
        public static String SINOPSIS = "sinopsis";

        public static String GAMBAR = "gambar";

        public static String RELEASE = "release";

        public static String FAVORITE = "favorite";

        public static String ID_MOVIE = "id_movie";

    }

    public static final String AUTHORITY = "com.ervin.dicoding_movie2";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
