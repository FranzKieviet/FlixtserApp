package com.example.flixtser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixtser.models.movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "I hid my key";
    public static final String VIDEOS_URL= "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle=findViewById(R.id.tvTitleDetail);
        tvOverview=findViewById(R.id.tvOverviewDetail);
        ratingBar=findViewById(R.id.ratingBar);
        youTubePlayerView=findViewById(R.id.player);

        movie film = Parcels.unwrap(getIntent().getParcelableExtra("film"));
        tvTitle.setText(film.getTitle());
        tvOverview.setText((film.getOverview()));
        ratingBar.setRating((float)film.getRating());


        AsyncHttpClient client =new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, film.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0){
                        return;
                }
                       String youtubeKey = results.getJSONObject(0).getString("key");
                       Log.d("DetailActivity", youtubeKey);
                       intializeYoutube(youtubeKey);

                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to parse JSON");
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });


    }

    private void intializeYoutube(String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("DetailActivity", "onInitializationSuccess");
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("DetailActivity", "onInitializationFailure");
            }
        });
    }
}