package com.edbono.android.popularmovies;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.edbono.android.popularmovies.utilities.JsonParser;
import com.edbono.android.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    TextView jsonResponseTextView;
    TextView textView;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    Context context = MainActivity.this;


    @Override
    public void onClick(String movieTitle, String overview, String rating, String release_date, Bitmap bitmap){
        // before we can send the image we need to convert it to a Byte Array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Log.v("CLOSER" , "CLOSER");
        Class movieDetail = MovieDetail.class;
        Intent intentToShowMovieDetails = new Intent(context, movieDetail);
        intentToShowMovieDetails.putExtra("title", movieTitle);
        intentToShowMovieDetails.putExtra("overview", overview);
        intentToShowMovieDetails.putExtra("rating", rating);
        intentToShowMovieDetails.putExtra("release_date", release_date);
        intentToShowMovieDetails.putExtra("image", byteArray);
        startActivity(intentToShowMovieDetails);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected() && netInfo.isAvailable());
    }

    private void loadData(String s){
        if (isOnline() == true){
            Log.v("isONline", "isOnline");
            new FetchMovieTask().execute(s);
        }
        if (isOnline() == false){
            Log.v("nointernetS" , "nointernetS");
            Toast toast = Toast.makeText(getApplicationContext(), "No Internet Connection. Please Connect", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("oncreate", "oncreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_movie);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this, context);
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        String sorttype = "default";
        loadData(sorttype);
        Log.v("endOfoncreate", "endofoncreate");
    }


    public class FetchMovieTask extends AsyncTask<String, Void, List<List<String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<List<String>> doInBackground(String...str) {
            URL weatherRequestUrl;
            if (str[0] == "default"){
                 weatherRequestUrl = NetworkUtils.buildUrl();
            }else if (str[0] == "top_rated_sort"){
                 weatherRequestUrl = NetworkUtils.buildTopRatedUrl();
            }else{
                 weatherRequestUrl = NetworkUtils.buildUrl();
            }
            try {

                List<List<String>> movieData = new ArrayList<List<String>>();

                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);
                // this function is to get the URLs which we pass
                String[] posterPathsStringArray = JsonParser.getMovieDataJsonString(MainActivity.this, jsonWeatherResponse);
                String[] movieTitleStringArray = JsonParser.getMovieDataTitleJsonString(MainActivity.this, jsonWeatherResponse);
                String[] movieOverviewStringArray = JsonParser.getMovieOverviewJsonString(MainActivity.this, jsonWeatherResponse);
                String[] movieReleaseDateStringArray = JsonParser.getMovieReleaseDateJsonString(MainActivity.this, jsonWeatherResponse);
                Double[] movieVoteRatingDoubleArray = JsonParser.getMovieRatingJsonString(MainActivity.this, jsonWeatherResponse);

                String[] movieVoteRatingStringArray = new String[movieVoteRatingDoubleArray.length];
                for (int i =0; i< movieVoteRatingDoubleArray.length; i++){
                    movieVoteRatingStringArray[i] = String.valueOf(movieVoteRatingDoubleArray[i]);

                }


                String lengthOfPosterPathStringArray = Integer.toString(posterPathsStringArray.length);
                String[] posterURLs = new String[posterPathsStringArray.length];
                for (int i=0; i<posterPathsStringArray.length; i++){
                    posterURLs[i] = NetworkUtils.buildPosterUrl(posterPathsStringArray[i]).toString();
                }

                List<String> movieURLs = Arrays.asList(posterURLs);
                List<String> movieTitles = Arrays.asList(movieTitleStringArray);
                List<String> movieOverviews = Arrays.asList(movieOverviewStringArray);
                List<String> movieReleaseDate = Arrays.asList(movieReleaseDateStringArray);
                List<String> movieVoteRating = Arrays.asList(movieVoteRatingStringArray);


                movieData.add(movieURLs);
                movieData.add(movieTitles);
                movieData.add(movieOverviews);
                movieData.add(movieReleaseDate);
                movieData.add(movieVoteRating);


                return movieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<List<String>> movieData) {

            List<String> posterArrayList = new ArrayList<String>();
            List<String> titleArrayList = new ArrayList<String>();
            List<String> overviewArrayList;
            List<String> releaseDateArrayList;
            List<String> ratingArrayList;


            if (movieData.get(0) != null){
                posterArrayList = movieData.get(0);
                String[] posterURLs = new String[posterArrayList.size()];
                posterURLs =posterArrayList.toArray(posterURLs);
                if (posterURLs != null){
                    mMovieAdapter.setMovieData(posterURLs);
                }

            }

            if (movieData.get(1) != null){
                titleArrayList = movieData.get(1);
                String[] movieTitles = new String[titleArrayList.size()];
                movieTitles =titleArrayList.toArray(movieTitles);
                if (movieTitles != null){
                    mMovieAdapter.setMovieTitle(movieTitles);
                }

            }


            if (movieData.get(2) != null){

                Log.v("overviewNN","overviewNN" );
                overviewArrayList = movieData.get(2);
                String[] movieOverviews = new String[overviewArrayList.size()];
                //String overviewofMovie = overviewArrayList.get(0);
                //String temp = "temp " + overviewofMovie;
               // Log.v("temo ", temp );
                movieOverviews = overviewArrayList.toArray(movieOverviews);
                if (movieOverviews != null){
                    mMovieAdapter.setMovieOverview(movieOverviews);
                }

            }


            if (movieData.get(3) != null){
                releaseDateArrayList = movieData.get(3);
                String[] movieReleaseDate = new String[releaseDateArrayList.size()];
                movieReleaseDate = releaseDateArrayList. toArray(movieReleaseDate);
                if (movieReleaseDate != null){
                    mMovieAdapter.setMovieReleaseDate(movieReleaseDate);
                }

            }

            if (movieData.get(4) != null){
                ratingArrayList = movieData.get(4);
                String[] movieRatings = new String[ratingArrayList.size()];
                movieRatings = ratingArrayList.toArray(movieRatings);
                if (movieRatings != null){
                    mMovieAdapter.setMovieRating(movieRatings);
                }
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.sort, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.popular_movies) {
            mMovieAdapter.setMovieTitle(null);
            loadData("default");
            return true;
        }


        if (id == R.id.top_rated) {
            loadData("top_rated_sort");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
