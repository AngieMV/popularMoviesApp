package mx.saudade.popularmoviesapp.callbacks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.GridAdapter;
import mx.saudade.popularmoviesapp.models.Results;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class MoviesCallback implements Callback<Results> {

    private static final String TAG = MoviesCallback.class.getSimpleName();

    private Context context;

    private GridAdapter adapter;

    private GridView view;

    private View notificationView;

    public MoviesCallback(Context context, GridView view, View notificationView) {
        this.context = context;
        this.view = view;
        this.notificationView = notificationView;
        this.notificationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void success(Results results, Response response) {
        Log.v(TAG, "sucess: " + results.toString());

        adapter = new GridAdapter(context, results.getMovies());
        view.setAdapter(adapter);
        notificationView.setVisibility(View.GONE);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.v(TAG, "failure: " + error.toString());
        Toast.makeText(context, context.getString(R.string.error_no_result), Toast.LENGTH_SHORT).show();
        notificationView.setVisibility(View.GONE);
    }


}
