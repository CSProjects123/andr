package com.edbono.android.popularmovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetail extends AppCompatActivity {

    TextView movieTitle;
    String title;

    TextView overviewTextView;
    String overview;

    TextView releaseDateTextView;
    String release_date;

    TextView ratingTextView;
    String rating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movieTitle = (TextView) findViewById(R.id.detail_movie_textview);
        overviewTextView= (TextView) findViewById(R.id.overview);
        releaseDateTextView = (TextView) findViewById(R.id.release_date);
        ratingTextView = (TextView) findViewById(R.id.rating);
        ImageView imageView = (ImageView) findViewById(R.id.posterImageview) ;

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("title")) {
                title = intentThatStartedThisActivity.getStringExtra("title");
                movieTitle.setText("NAME: " + title);
            }
            if (intentThatStartedThisActivity.hasExtra("overview")) {
                overview = intentThatStartedThisActivity.getStringExtra("overview");
                overviewTextView.setText("OVERVIEW: " + overview);
            }
            if (intentThatStartedThisActivity.hasExtra("rating")) {
                rating = intentThatStartedThisActivity.getStringExtra("rating");
                ratingTextView.setText("RATING: " + rating);
            }
            if (intentThatStartedThisActivity.hasExtra("release_date")) {
                release_date = intentThatStartedThisActivity.getStringExtra("release_date");
                releaseDateTextView.setText("RELEASE DATE: " + release_date);
            }
            if (intentThatStartedThisActivity.hasExtra("image")) {
                byte[] byteArray = intentThatStartedThisActivity.getByteArrayExtra("image");
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageView.setImageBitmap(bitmap);
            }

        }


    }
}
