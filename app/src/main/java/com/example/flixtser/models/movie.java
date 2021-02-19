package com.example.flixtser.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class movie {

    int movieID;
    String posterPath;
    String title;
    String overview;
    double rating;

    //empty as it is needed for the parcel library
    public movie() { }

    public movie(JSONObject jsonObject) throws JSONException {
        posterPath=jsonObject.getString("poster_path");
        title=jsonObject.getString("title");
        overview=jsonObject.getString("overview");
        rating=jsonObject.getDouble("vote_average");
        movieID=jsonObject.getInt("id");
    }

    public static List<movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieID() {
        return movieID;
    }
}
