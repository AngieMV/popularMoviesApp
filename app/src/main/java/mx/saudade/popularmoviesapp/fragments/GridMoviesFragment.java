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
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.activities.DetailActivity;
import mx.saudade.popularmoviesapp.adapters.GridAdapter;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Results;

public class GridMoviesFragment extends Fragment{

    private static final String TAG = GridMoviesFragment.class.getSimpleName();

    private Manager manager;

    private GridView gridView;

    private TextView notificationView;

    private static final String ID_MOVIES = TAG + " movies";

    private List<Movie> movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        notificationView = (TextView) rootView.findViewById(R.id.textView_loading);
        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = (Movie) parent.getAdapter().getItem(position);
                startDetailActivity(movie);
            }
        });

        manager = new Manager(getActivity(), gridView, notificationView);

        if(savedInstanceState != null) {
            movies = ((Results) savedInstanceState.getSerializable(ID_MOVIES)).getMovies();
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (movies == null || movies.size() == 0) {
            manager.invokeRetro();
            notificationView.setVisibility(View.VISIBLE);
        } else {
            GridAdapter adapter = new GridAdapter(getActivity(), movies);
            gridView.setAdapter(adapter);
            notificationView.setVisibility(View.GONE);
        }

    }

    private void startDetailActivity(Movie movie) {
        Intent i = new Intent();
        i.setClass(getActivity(), DetailActivity.class);
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, movie);
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ID_MOVIES, ((GridAdapter)
                ((GridView) getView().findViewById(R.id.gridView)).getAdapter()).getMovies());
    }

}
