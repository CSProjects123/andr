package com.edbono.android.popularmovies.utilities;


import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.R;


import android.util.Log;


/**
 * Utility functions to handle OpenWeatherMap JSON data.
 */
public final class JsonParser {


    /**
     * This method parses JSON from a web response and returns an array of Strings
     * describing the weather over various days from the forecast.
     * <p/>
     * Later on, we'll be parsing the JSON into structured data within the
     * getFullWeatherDataFromJson function, leveraging the data we have stored in the JSON. For
     * now, we just convert the JSON into human-readable strings.
     *
     * @param forecastJsonStr JSON response from server
     *
     * @return Array of Strings describing weather data
     *
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static String[] getMovieDataJsonString(Context context, String movieDataJson)
            throws JSONException {

        JSONObject jsonObject = new JSONObject(movieDataJson);
        JSONArray weatherArray = jsonObject.getJSONArray("results");
        String[] posterPaths = new String[weatherArray.length()];

        for (int i=0; i<weatherArray.length(); i++){
            JSONObject movieDetails = weatherArray.getJSONObject(i);
            String poster_path_string;
            poster_path_string = movieDetails.getString("poster_path");
            posterPaths[i] = poster_path_string;
        }
        return posterPaths;
    }

    public static String[] getMovieDataTitleJsonString(Context context, String movieDataJson)
            throws JSONException {


        JSONObject jsonObject = new JSONObject(movieDataJson);
        JSONArray movieArray = jsonObject.getJSONArray("results");
        String[] titles = new String[movieArray.length()];

        for (int i=0; i<movieArray.length(); i++){
            JSONObject movieDetails = movieArray.getJSONObject(i);
            String title;
            title= movieDetails.getString("title");
            titles[i] = title;
        }
        return titles;
    }

    public static String[] getMovieOverviewJsonString(Context context, String movieDataJson)
            throws JSONException {


        JSONObject jsonObject = new JSONObject(movieDataJson);
        JSONArray movieArray = jsonObject.getJSONArray("results");
        String[] overview = new String[movieArray.length()];

        for (int i=0; i<movieArray.length(); i++){
            JSONObject movieDetails = movieArray.getJSONObject(i);
            String overviewString;
            overviewString = movieDetails.getString("overview");
            overview[i] = overviewString;
        }
        return overview;
    }

    public static String[] getMovieReleaseDateJsonString(Context context, String movieDataJson)
            throws JSONException {


        JSONObject jsonObject = new JSONObject(movieDataJson);
        JSONArray movieArray = jsonObject.getJSONArray("results");
        String[] releaseDate = new String[movieArray.length()];

        for (int i=0; i<movieArray.length(); i++){
            JSONObject movieDetails = movieArray.getJSONObject(i);
            String date;
            date = movieDetails.getString("release_date");
            releaseDate[i] = date;
        }
        return releaseDate;
    }

    public static Double[] getMovieRatingJsonString(Context context, String movieDataJson)
            throws JSONException {


        JSONObject jsonObject = new JSONObject(movieDataJson);
        JSONArray movieArray = jsonObject.getJSONArray("results");
        Double[] movieRating = new Double[movieArray.length()];

        for (int i=0; i<movieArray.length(); i++){
            JSONObject movieDetails = movieArray.getJSONObject(i);
            Double rating;
            rating = movieDetails.getDouble("vote_average");
            movieRating[i] = rating;
        }
        return movieRating;
    }




}