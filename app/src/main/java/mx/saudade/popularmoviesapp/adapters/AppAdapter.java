package mx.saudade.popularmoviesapp.adapters;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import mx.saudade.popularmoviesapp.models.Results;

/**
 * Created by angelicamendezvega on 8/24/15.
 */
public abstract class AppAdapter<T> extends BaseAdapter {

    protected Context context;

    protected List<T> results;

    public static int INVALID_INDEX = -1;

    protected int selectedIndex = INVALID_INDEX;

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

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    protected int getColor(int id) {
        return context.getResources().getColor(id);
    }

}
