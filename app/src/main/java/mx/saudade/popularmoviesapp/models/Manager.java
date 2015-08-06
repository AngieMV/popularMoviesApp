package mx.saudade.popularmoviesapp.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.callbacks.MoviesCallback;
import mx.saudade.popularmoviesapp.interfaces.MoviesInterface;
import retrofit.RestAdapter;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class Manager {

    private static final String TAG = Manager.class.getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org";

    private final String API_KEY = "YOUR MOVIE DB API KEY HERE";

    private Context context;

    private GridView view;

    private View notificationView;

    public Manager(Context context, GridView view, View notificationView) {
        this.context = context;
        this.view = view;
        this.notificationView = notificationView;
    }

    public void invokeRetro() {
        if (!isOnline()) {
            Toast.makeText(context, context.getString(R.string.error_no_online), Toast.LENGTH_SHORT).show();
            return;
        }

        invokeRetro(getOrderPreference());
    }

    public void invokeRetro(String order) {

        Log.v(TAG, "invokeRetro " + order);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL).build();

        MoviesInterface moviesInterface = restAdapter.create(MoviesInterface.class);
        moviesInterface.getMovies(order, API_KEY , "1", new MoviesCallback(context, view, notificationView));

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
