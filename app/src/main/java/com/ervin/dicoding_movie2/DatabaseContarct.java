package com.ervin.dicoding_movie2;

import android.provider.BaseColumns;

/**
 * Created by ervin on 12/20/2017.
 */

public class DatabaseContarct {

    static String TABLE_NAME = "table_favorite";

    static final class MovieColumns implements BaseColumns {


        static String JUDUL = "nama";
        // Mahasiswa nim
        static String SINOPSIS = "sinopsis";

        static String GAMBAR = "gambar";

        static String RELEASE = "release";

        static String FAVORITE = "favorite";

        static String ID_MOVIE = "id_movie";

    }
}
