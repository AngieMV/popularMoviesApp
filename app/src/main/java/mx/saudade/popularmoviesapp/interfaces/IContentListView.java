package mx.saudade.popularmoviesapp.interfaces;

import android.widget.AdapterView;

import java.util.List;

import mx.saudade.popularmoviesapp.adapters.AppAdapter;

/**
 * Created by angie on 9/1/15.
 */
public interface IContentListView<T>  {

    void setAdapter(AppAdapter<T> adapter);

    void setResults(List<T> results);

    void error();

    void evaluateResults(List<T> results);

    void setOnItemClickListener(AdapterView.OnItemClickListener listener);

}
