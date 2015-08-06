package mx.saudade.popularmoviesapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.utils.ActionUtils;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        displayInfo();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
        ActionUtils.share(provider, getMovie().getShareMessage());

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void displayInfo() {
        if (getActivity().getIntent() == null || !getActivity().getIntent().hasExtra(Intent.EXTRA_SHORTCUT_NAME)){
            return;
        }

        getTextView(R.id.detail_title).setText(getMovie().getOriginalTitle());

        Picasso.with(getActivity()).load(getMovie().getPosterPosterPath())
                .placeholder(R.drawable.placeholder_movie_icon).into(getImage(R.id.detail_image));

        getTextView(R.id.detail_release_date).setText(getMovie().getReleaseMessage());
        getTextView(R.id.detail_rating).setText(getMovie().getVoteAverageMessage());
        getTextView(R.id.detail_sinopsis).setText(getMovie().getOverview());
    }

    private Movie getMovie() {
        return (Movie) getActivity().getIntent().getSerializableExtra(Intent.EXTRA_SHORTCUT_NAME);
    }

    private TextView getTextView(int id) {
        return (TextView) getView().findViewById(id);
    }

    private ImageView getImage(int id) {
        return (ImageView) getView().findViewById(id);
    }

}
