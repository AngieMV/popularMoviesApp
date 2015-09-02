package mx.saudade.popularmoviesapp.callbacks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.AppAdapter;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.models.Results;
import mx.saudade.popularmoviesapp.utils.ListViewUtil;
import mx.saudade.popularmoviesapp.views.ContentList;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public class AppCallback<T> implements Callback<Results<T>> {

    protected static final String TAG = AppCallback.class.getSimpleName();

    private ContentList contentList;

    public AppCallback( ContentList contentList) {
        this.contentList = contentList;
    }

    @Override
    public void success(Results<T> results, Response response) {
        Log.v(TAG, "success: " + results.getResults().size() + " " + results.toString());
        contentList.show(results.getResults());
    }

    @Override
    public void failure(RetrofitError error) {
        Log.v(TAG, "failure: " + error.toString());
        contentList.error();
    }
}
