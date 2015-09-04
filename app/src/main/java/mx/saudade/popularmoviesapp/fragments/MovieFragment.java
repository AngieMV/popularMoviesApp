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
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.data.AppLoaderManager;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.views.ContentListView;

public class MovieFragment extends Fragment{

    private static final String TAG = MovieFragment.class.getSimpleName();

    private MovieAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MovieAdapter(getActivity());
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

        getContentListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getAdapter().getItem(position);
                ((MovieAdapter) parent.getAdapter()).setSelectedIndex(position);
                startDetailActivity(movie);
            }
        });
        Log.v(TAG, "XXX onStart");
        List<Movie> results = getContentListView().getResults();
        if (results.size() == 0) {
            Log.v(TAG, "XXX calling getMovies");
            Manager manager = new Manager(getActivity(), new AppLoaderManager(getActivity()));
            manager.getMovies(getContentListView());
        }
    }

    private void startDetailActivity(Movie movie) {
        Intent i = new Intent();
        i.setClass(getActivity(), DetailActivity.class);
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, movie);
        startActivity(i);
    }

    private ContentListView getContentListView() {
        return (ContentListView) getView().findViewById(R.id.moviesContentListView);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.v(TAG, "xxx onViewStateRestored");
        //save state here
        getContentListView().restoreState(savedInstanceState, adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.v(TAG, "xxx onSaveInstanceState");
        //restore state here
        getContentListView().saveState(outState);
    }
}
