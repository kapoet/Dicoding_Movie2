package com.ervin.dicodingfavorite.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.ervin.dicodingfavorite.DatabaseContarct;

import static android.provider.BaseColumns._ID;
import static com.ervin.dicodingfavorite.DatabaseContarct.getColumnInt;
import static com.ervin.dicodingfavorite.DatabaseContarct.getColumnString;

/**
 * Created by ervin on 12/21/2017.
 */

public class MovieItem implements Parcelable {
    private String title, release, synopsis, path_image, favorite, id_movie;
    private int id;

    public MovieItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPath_image() {
        return path_image;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.release);
        dest.writeString(this.synopsis);
        dest.writeString(this.path_image);
        dest.writeString(this.favorite);
        dest.writeString(this.id_movie);
        dest.writeInt(this.id);
    }

    public MovieItem(Parcel in) {
        this.title = in.readString();
        this.release = in.readString();
        this.synopsis = in.readString();
        this.path_image = in.readString();
        this.favorite = in.readString();
        this.id_movie = in.readString();
        this.id = in.readInt();
    }
    public MovieItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, DatabaseContarct.MovieColumns.JUDUL);
        this.release = getColumnString(cursor, DatabaseContarct.MovieColumns.RELEASE);
        this.synopsis = getColumnString(cursor, DatabaseContarct.MovieColumns.SINOPSIS);
        this.path_image = getColumnString(cursor, DatabaseContarct.MovieColumns.GAMBAR);
        this.favorite = getColumnString(cursor, DatabaseContarct.MovieColumns.FAVORITE);
        this.id_movie = getColumnString(cursor, DatabaseContarct.MovieColumns.ID_MOVIE);
    }
    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
