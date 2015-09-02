package mx.saudade.popularmoviesapp.models;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.adapters.ReviewAdapter;
import mx.saudade.popularmoviesapp.adapters.VideoAdapter;
import mx.saudade.popularmoviesapp.callbacks.AppCallback;
import mx.saudade.popularmoviesapp.data.AppLoaderManager;
import mx.saudade.popularmoviesapp.interfaces.AppInterface;
import mx.saudade.popularmoviesapp.views.ContentList;
import retrofit.RestAdapter;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class Manager {

    private static final String TAG = Manager.class.getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org";

    private final String API_KEY = "YOUR MOVIE DB API KEY HERE";

    private RestAdapter restAdapter = null;

    private Context context;

    private AppLoaderManager appLoaderManager;

    public Manager(Context context, AppLoaderManager appLoaderManager) {
        this.context = context;
        this.appLoaderManager = appLoaderManager;
    }

    private AppInterface getMoviesInterface() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL).build();
        }
        return restAdapter.create(AppInterface.class);
    }

    public void getReviews(Movie movie, AbsListView view, View notificationView) {
        Log.v(TAG, "movieId: " + movie.getId());
        if (appLoaderManager.getMovie(movie.get_Id()) != null) {
            Log.v(TAG, "displayed from dataBase");
            invokeDBReviews(movie.get_Id(),  view, notificationView);
        } else {
            Log.v(TAG, "displayed from web");
            invokeWebReviews(movie.getId(), view, notificationView);
        }
    }

    public void getVideos(Movie movie, AbsListView view, View notificationView) {
        Log.v(TAG, "movieId: " + movie.getId());
        if (appLoaderManager.getMovie(movie.get_Id()) != null) {
            Log.v(TAG, "displayed from dataBase");
            invokeDBVideos(movie.get_Id(), view, notificationView);
        } else {
            Log.v(TAG, "displayed from web");
            invokeWebVideos(movie.getId(), view, notificationView);
        }
    }

    public void invokeWebReviews(int movieId, AbsListView view, View notificationView) {
        getMoviesInterface().getReviews(movieId, API_KEY, new AppCallback(getReviewContentList(view, notificationView)));
    }

    public void  invokeDBReviews(String movieId, AbsListView view, View notificationView) {
        List<Review> reviews = appLoaderManager.getReviews(movieId);
        getReviewContentList(view, notificationView).controlState(reviews);
    }

    public void invokeWebVideos(int movieId, AbsListView view, View notificationView) {
        getMoviesInterface().getVideos(movieId, API_KEY, new AppCallback<Video>(getVideoContentList(view, notificationView)));
    }

    public void invokeDBVideos(String movieId, AbsListView view, View notificationView) {
        List<Video> videos = appLoaderManager.getVideos(movieId);
        getVideoContentList(view, notificationView).controlState(videos);
    }

    public void invokeRetro(AbsListView view, View notificationView) {
        if (!isOnline()) {
            Toast.makeText(context, context.getString(R.string.error_no_online), Toast.LENGTH_SHORT).show();
            return;
        }

        invokeRetro(getOrderPreference(), view, notificationView);
    }

    public void invokeRetro(String order, AbsListView view, View notificationView) {
        Log.v(TAG, "invokeRetro " + order);
        ContentList<Movie> contentList = new ContentList<Movie>(context, (GridView) view, new MovieAdapter(context), notificationView);
        getMoviesInterface().getMovies(order, API_KEY, "1", new AppCallback(contentList));
    }

    private String getOrderPreference() {
        String preference  = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.pref_sortby_key)
                        , context.getString(R.string.pref_sortby_default_value));
        return getCode(preference);
    }

    private String getCode(String selection) {
        if (StringUtils.equalsIgnoreCase(context.getString(R.string.pref_sortby_default_value), selection)) {
            return "popularity.desc";
        } else {
            return "vote_average.desc";
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private ContentList<Review> getReviewContentList(AbsListView view, View notificationView) {
        return new ContentList<Review>(context, view, new ReviewAdapter(context), notificationView);
    }

    private ContentList<Video> getVideoContentList(AbsListView view, View notificationView) {
        return new ContentList<Video>(context, view, new VideoAdapter(context), notificationView);
    }

}
