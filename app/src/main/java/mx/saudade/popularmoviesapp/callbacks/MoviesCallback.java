package mx.saudade.popularmoviesapp.callbacks;

import android.util.Log;

import mx.saudade.popularmoviesapp.models.Results;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by angelicamendezvega on 8/4/15.
 */
public class MoviesCallback implements Callback<Results> {

    private static final String TAG = MoviesCallback.class.getSimpleName();

    @Override
    public void success(Results results, Response response) {
        Log.v(TAG, "sucess: " + results.toString());

    }

    @Override
    public void failure(RetrofitError error) {
        Log.v(TAG, "failure: " + error.toString());
    }

}
