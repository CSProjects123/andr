package com.edbono.android.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String POPULAR_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/popular";

    private static final String TOP_RATED_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/top_rated";

    private static final String POSTER_PATH_URL ="http://image.tmdb.org/t/p/w185";

    private static final String DEFAULT_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/popular";

    final static String API = "api_key";
    final static String API_KEY = "****";

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param locationQuery The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl() {

        Uri builtUri = Uri.parse(DEFAULT_MOVIE_URL).buildUpon()
                .appendQueryParameter(API, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static URL buildTopRatedUrl() {

        Uri builtUri = Uri.parse(TOP_RATED_MOVIE_URL).buildUpon()
                .appendQueryParameter(API, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }


    public static URL buildPosterUrl(String poster_path) {

        String newBasePosterPathUrl = POSTER_PATH_URL + poster_path;
        Uri builtUri = Uri.parse(newBasePosterPathUrl).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}