package mx.saudade.popularmoviesapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Results;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public abstract class AppAdapter<T> extends BaseAdapter {

    protected Context context;

    protected List<T> results;

    public AppAdapter(Context context) {
        this.context = context;
        results = new ArrayList<T>();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Results<T> getResults() {
        return new Results(results);
    }

    public void setResults(List<T> results) {
        this.results = results;
        notifyDataSetChanged();
    }

}
