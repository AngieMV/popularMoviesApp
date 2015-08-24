package mx.saudade.popularmoviesapp.callbacks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.AppAdapter;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.models.Results;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class AppCallback<T> implements Callback<Results<T>> {

    protected static final String TAG = AppCallback.class.getSimpleName();

    protected Context context;

    protected AbsListView view;

    protected View notificationView;

    protected AppAdapter adapter;

    public AppCallback(Context context, AbsListView view, View notificationView, AppAdapter adapter) {
        this.context = context;
        this.view = view;
        this.adapter = adapter;
        this.notificationView = notificationView;
        this.notificationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void success(Results<T> results, Response response) {
        Log.v(TAG, "sucess: " + results.toString());
        adapter.setResults(results.getResults());
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
