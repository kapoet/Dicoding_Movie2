package com.ervin.dicoding_movie2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ervin on 12/10/2017.
 */
public class Movie implements Parcelable {
    private String title, release, synopsis, path_image, favorite, id_movie;
    private int id;


    public Movie() {
    }

    public Movie(String title, String release, String synopsis, String path_image, String id_movie) {
        this.title = title;
        this.release = release;
        this.synopsis = synopsis;
        this.path_image = path_image;
        this.id_movie = id_movie;
    }

    public String getTitle() {
        return title;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease() {
        return release;
    }

    public String getId_movie() {
        return id_movie;
    }

    public void setId_movie(String id_movie) {
        this.id_movie = id_movie;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setPath_image(String path_image) {
        this.path_image = path_image;
    }

    public String getPath_image() {
        return path_image;
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
        dest.writeInt(this.id);
        dest.writeString(this.id_movie);
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.release = in.readString();
        this.synopsis = in.readString();
        this.path_image = in.readString();
        this.favorite = in.readString();
        this.id = in.readInt();
        this.id_movie = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
