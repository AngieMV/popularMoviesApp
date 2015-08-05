package mx.saudade.popularmoviesapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mx.saudade.popularmoviesapp.callbacks.MoviesCallback;
import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.interfaces.MoviesInterface;
import retrofit.RestAdapter;

public class GridMoviesFragment extends Fragment {

    private static final String TAG = GridMoviesFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        invokeRetro();
        return rootView;
    }

    private void invokeRetro() {

        Log.v(TAG, "invokeRetro");

        String baseUrl = "http://api.themoviedb.org";

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(baseUrl).build();

        MoviesInterface moviesInterface = restAdapter.create(MoviesInterface.class);

        moviesInterface.getMovies("popularity.desc", getActivity().getString(R.string.api_key), "1", new MoviesCallback());

    }
}
