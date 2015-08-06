package mx.saudade.popularmoviesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Movie;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayInfo();
    }

    public void displayInfo() {
        if (getActivity().getIntent() == null || !getActivity().getIntent().hasExtra(Intent.EXTRA_SHORTCUT_NAME)){
            return;
        }

        Movie movie = (Movie) getActivity().getIntent().getSerializableExtra(Intent.EXTRA_SHORTCUT_NAME);

        getTextView(R.id.detail_title).setText(movie.getOriginalTitle());

        Picasso.with(getActivity()).load(movie.getPosterPosterPath())
                .placeholder(R.drawable.placeholder_movie_icon).into(getImage(R.id.detail_image));

        getTextView(R.id.detail_release_date).setText("Release date:" + movie.getReleaseDate());
        getTextView(R.id.detail_rating).setText(movie.getVoteAverage() + " / 10");
        getTextView(R.id.detail_sinopsis).setText(movie.getOverview());
    }

    private TextView getTextView(int id) {
        return (TextView) getView().findViewById(id);
    }

    private ImageView getImage(int id) {
        return (ImageView) getView().findViewById(id);
    }

}
