package mx.saudade.popularmoviesapp.callbacks;

import android.content.Context;
import android.util.Log;
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

    public MoviesCallback(Context context, GridView view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void success(Results results, Response response) {
        Log.v(TAG, "sucess: " + results.toString());

        adapter = new GridAdapter(context, results.getMovies());
        view.setAdapter(adapter);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.v(TAG, "failure: " + error.toString());
        Toast.makeText(context, context.getString(R.string.error_no_result), Toast.LENGTH_SHORT).show();
    }

}
