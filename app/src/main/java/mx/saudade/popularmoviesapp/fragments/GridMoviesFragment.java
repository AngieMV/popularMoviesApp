package mx.saudade.popularmoviesapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.activities.DetailActivity;
import mx.saudade.popularmoviesapp.adapters.GridAdapter;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;

public class GridMoviesFragment extends Fragment{

    private static final String TAG = GridMoviesFragment.class.getSimpleName();

    private Manager manager;

    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = (Movie) parent.getAdapter().getItem(position);
                startDetailActivity(movie);
            }
        });

        manager = new Manager(getActivity(), gridView);

        return rootView;
    }

    @Override
    public void onStart() {

        super.onStart();
        manager.invokeRetro();
    }

    private void startDetailActivity(Movie movie) {
        Log.v(TAG, "start detail activity. Movie: " + movie.toString());

        Intent i = new Intent();
        i.setClass(getActivity(), DetailActivity.class);
        i.putExtra(Intent.EXTRA_SHORTCUT_NAME, movie);
        startActivity(i);

    }

}
