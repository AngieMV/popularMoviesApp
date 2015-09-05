package mx.saudade.popularmoviesapp.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.callbacks.AppCallback;
import mx.saudade.popularmoviesapp.data.AppLoaderManager;
import mx.saudade.popularmoviesapp.interfaces.AppInterface;
import mx.saudade.popularmoviesapp.utils.PreferenceUtils;
import mx.saudade.popularmoviesapp.views.ContentListView;
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

    public void getReviews(Movie movie, ContentListView<Review> contentListView) {
        Log.v(TAG, "movieId: " + movie.getId());
        if (appLoaderManager.getMovie(movie.get_Id()) != null) {
            invokeDBReviews(movie.get_Id(), contentListView);
        } else {
            invokeWebReviews(movie.getId(), contentListView);
        }
    }

    public void getVideos(Movie movie, ContentListView<Video> contentListView) {
        Log.v(TAG, "movieId: " + movie.getId());
        if (appLoaderManager.getMovie(movie.get_Id()) != null) {
            invokeDBVideos(movie.get_Id(), contentListView);
        } else {
            invokeWebVideos(movie.getId(), contentListView);
        }
    }

    public void invokeWebReviews(int movieId, ContentListView<Review> contentListView) {
        Log.v(TAG, "displayed from web");
        getMoviesInterface().getReviews(movieId, API_KEY, new AppCallback(contentListView));
    }

    public void  invokeDBReviews(String movieId, ContentListView<Review> contentListView) {
        Log.v(TAG, "displayed from dataBase");
        List<Review> reviews = appLoaderManager.getReviews(movieId);
        contentListView.evaluateResults(reviews);
    }

    public void invokeWebVideos(int movieId, ContentListView<Video> contentListView) {
        Log.v(TAG, "displayed from web");
        getMoviesInterface().getVideos(movieId, API_KEY, new AppCallback<Video>(contentListView));
    }

    public void invokeDBVideos(String movieId, ContentListView<Video> contentListView) {
        Log.v(TAG, "displayed from dataBase");
        List<Video> videos = appLoaderManager.getVideos(movieId);
        contentListView.evaluateResults(videos);
    }

    public void getMovies(ContentListView<Movie> contentListView) {
        if (isSetFavoritesMovies()) {
            invokeDBMovies(contentListView);
        } else {
            invokeWebMovies(contentListView);
        }
        contentListView.cleanPosition();
        PreferenceUtils.setPrevPreferenceFavorite(context);
        PreferenceUtils.setPrevPreferenceOrder(context);
    }

    public void invokeDBMovies(ContentListView<Movie> contentListView) {
        Log.v(TAG, "displayed from dataBase");
        String order = getOrderPreference();
        contentListView.evaluateResults(appLoaderManager.getMovies(order));
    }

    public void invokeWebMovies(ContentListView<Movie> contentListView) {
        if (!isOnline()) {
            Toast.makeText(context, context.getString(R.string.error_no_online), Toast.LENGTH_SHORT).show();
            return;
        }
        invokeWebMovies(getOrderPreference(), contentListView);
    }

    public void invokeWebMovies(String order, ContentListView<Movie> contentListView) {
        Log.v(TAG, "invokeRetro " + order);
        getMoviesInterface().getMovies(order, API_KEY, "1", new AppCallback(contentListView));
    }

    private String getOrderPreference() {
        String preference  = PreferenceUtils.getString(context, R.string.pref_sortby_key, R.string.pref_sortby_default_value);
        return getCode(preference);
    }

    private boolean isSetFavoritesMovies() {
        return PreferenceUtils.getBoolean(context, R.string.pref_fav_key);
    }

    public boolean isStatusChanged() {
        boolean isStatusChanged = PreferenceUtils.isNewFavoriteConfig(context)
                || PreferenceUtils.isNewOrderConfig(context);
        Log.v(TAG, "xxx isStatusChanged " + isStatusChanged);
        return isStatusChanged;
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
