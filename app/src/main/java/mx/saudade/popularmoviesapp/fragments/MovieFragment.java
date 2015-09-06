package mx.saudade.popularmoviesapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.activities.DetailActivity;
import mx.saudade.popularmoviesapp.activities.MovieActivity;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.data.AppLoaderManager;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.views.ContentListView;

public class MovieFragment extends Fragment {

    private static final String TAG = MovieFragment.class.getSimpleName();

    private MovieAdapter adapter;

    private Manager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MovieAdapter(getActivity(), 1);
        manager = new Manager(getActivity(), new AppLoaderManager(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.v(TAG, "XXX onCreateView");
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getContentListView().setOnItemClickListener((MovieActivity)getActivity());
        Log.v(TAG, "XXX onStart");
        List<Movie> results = getContentListView().getResults();
        if (results.size() == 0 || manager.isStatusChanged() || manager.isFavListChanged(results)) {
            Log.v(TAG, "XXX calling getMovies");
            manager.getMovies(getContentListView());
        }
    }

    private ContentListView getContentListView() {
        return (ContentListView) getView().findViewById(R.id.moviesContentListView);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getContentListView().restoreState(savedInstanceState, adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getContentListView().saveState(outState);
    }
}
