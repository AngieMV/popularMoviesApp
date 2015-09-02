package mx.saudade.popularmoviesapp.callbacks;

import android.util.Log;

import mx.saudade.popularmoviesapp.models.Results;
import mx.saudade.popularmoviesapp.views.ContentListView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class AppCallback<T> implements Callback<Results<T>> {

    protected static final String TAG = AppCallback.class.getSimpleName();

    private ContentListView<T> ContentListView;

    public AppCallback(ContentListView<T> ContentListView) {
        this.ContentListView = ContentListView;
    }

    @Override
    public void success(Results<T> results, Response response) {
        Log.v(TAG, "success: " + results.getResults().size() + " " + results.toString());
        ContentListView.show(results.getResults());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.v(TAG, "failure: " + error.toString());
        ContentListView.error();
    }
}
