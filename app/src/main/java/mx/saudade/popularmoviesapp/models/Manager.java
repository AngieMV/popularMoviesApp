package mx.saudade.popularmoviesapp.models;

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

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.adapters.ReviewAdapter;
import mx.saudade.popularmoviesapp.adapters.VideoAdapter;
import mx.saudade.popularmoviesapp.callbacks.AppCallback;
import mx.saudade.popularmoviesapp.interfaces.AppInterface;
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

    public Manager(Context context) {
        this.context = context;
    }

    private AppInterface getMoviesInterface() {
        if (restAdapter == null) {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BASE_URL).build();
        }
        return restAdapter.create(AppInterface.class);
    }

    public void invokeReviews(int movieId, AbsListView view, View notificationView) {
        Log.v(TAG, "movieId: " + movieId);
        getMoviesInterface().getReviews(movieId, API_KEY, new AppCallback(context, view, notificationView, new ReviewAdapter(context)));
    }

    public void invokeVideos(int movieId, AbsListView view, View notificationView) {
            Log.v(TAG, "movieId: " + movieId);
            getMoviesInterface().getVideos(movieId, API_KEY, new AppCallback<Video>(context, view, notificationView, new VideoAdapter(context)));
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
        getMoviesInterface().getMovies(order, API_KEY, "1", new AppCallback(context, (GridView) view, notificationView, new MovieAdapter(context)));
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

}
