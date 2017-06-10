package com.edbono.android.popularmovies;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private String[] mMovieData;
    private String[] mMovieTitle;
    private String[] mMovieOverview;
    private String[] mMovieReleaseDate;
    private String[] mMovieRating;

    private Context context;
    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieTitle, String overview, String rating, String release_date, Bitmap bitmap);
    }


    public MovieAdapter(MovieAdapterOnClickHandler ml, Context context){
        if (mMovieData == null){
            mMovieData = new String[]{"first name ", "second name ", "third name ", "fourth name"};
            Log.v("stringarYYYY", "stringarYYYY");
        }

        if (mMovieTitle == null){
            mMovieTitle = new String[]{"default"};
        }

        mClickHandler = ml;
        this.context = context;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        public final TextView mMovieTextView;
        public final ImageView mImageView;


        public MovieAdapterViewHolder(View view) {
            super(view);
            Log.v("was this called", "was this called");
            mMovieTextView = (TextView) view.findViewById(R.id.movie_url_data);
            mImageView = (ImageView) view.findViewById(R.id.image_view_list_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bitmap image=((BitmapDrawable)mImageView.getDrawable()).getBitmap();
            int adapterPosition = getAdapterPosition();
            String Title = mMovieTitle[adapterPosition];
            String overview = mMovieOverview[adapterPosition];
            String rating = mMovieRating[adapterPosition];
            String release_date = mMovieReleaseDate[adapterPosition];
            mClickHandler.onClick(Title, overview, rating, release_date, image);
        }

    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        Log.v("NNNN", "NNNN");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movieData = mMovieData[position];

        try{
            //movieAdapterViewHolder.mMovieTextView.setText(movieData);
            Picasso.with(context).load(mMovieData[position]).into(movieAdapterViewHolder.mImageView);

        }catch (Exception e){
            Log.v("EXCEPTION", "EXCEPTION");
        }
        Log.v("Bind function", "bind function");
    }

    @Override
    public int getItemCount() {
        Log.v("getitemcount called", "getitemcount called");
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    public void setMovieData(String[] movieData) {
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    public void setMovieTitle(String[] movieTitle) {
        mMovieTitle = movieTitle;
        notifyDataSetChanged();
    }

    public void setMovieOverview(String[] overview) {
        Log.v("OVERview", "OVERview");
        mMovieOverview = overview;
        notifyDataSetChanged();
    }

    public void setMovieReleaseDate(String[] release_date) {
        mMovieReleaseDate = release_date;
        notifyDataSetChanged();
    }

    public void setMovieRating(String[] rating) {
        mMovieRating = rating;
        notifyDataSetChanged();
    }

}
