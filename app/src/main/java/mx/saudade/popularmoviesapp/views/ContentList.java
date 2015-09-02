package mx.saudade.popularmoviesapp.views;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.AppAdapter;
import mx.saudade.popularmoviesapp.models.Results;

/**
 * Created by angelicamendezvega on 9/1/15.
 */
public class ContentList<T> {

    protected Context context;

    protected AbsListView view;

    protected View notificationView;

    protected AppAdapter adapter;

    public ContentList(Context context, AbsListView view, AppAdapter adapter, View notificationView) {
        this.context = context;
        this.view = view;
        this.adapter = adapter;
        this.notificationView = notificationView;
        this.notificationView.setVisibility(View.VISIBLE);
    }

    public void show(List<T> results) {
        adapter.setResults(results);
        view.setAdapter(adapter);
        notificationView.setVisibility(View.GONE);
    }

    public void error() {
        Toast.makeText(context, context.getString(R.string.error_no_result), Toast.LENGTH_SHORT).show();
        notificationView.setVisibility(View.GONE);
    }

    public void controlState(List<T> results) {
        if (results == null || results.size() == 0) {
            error();
        } else {
            show(results);
        }
    }

}
