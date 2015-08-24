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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Review;
import mx.saudade.popularmoviesapp.models.Video;
import mx.saudade.popularmoviesapp.utils.ActionUtils;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
        loadContent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
        ActionUtils.share(provider, getMovie().getShareMessage() + getTrailer() + ">>>");
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void loadContent() {
        Manager manager = new Manager(getActivity());
        manager.invokeReviews(getMovie().getId(), getListView(R.id.listView_reviews), getTextView(R.id.textView_loading));
        manager.invokeVideos(getMovie().getId(), getListView(R.id.listView_trailers), getTextView(R.id.textView_loading));
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

        getListView(R.id.listView_reviews).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = (Review) parent.getAdapter().getItem(position);
                ActionUtils.viewContent(getActivity(), review.getUrl());
            }
        });

        getListView(R.id.listView_trailers).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Video video = (Video) parent.getAdapter().getItem(position);
                ActionUtils.viewContent(getActivity(), video.getUrl());
            }
        });
    }

    private Movie getMovie() {
        return (Movie) getActivity().getIntent().getSerializableExtra(Intent.EXTRA_SHORTCUT_NAME);
    }

    private String getTrailer() {
        Log.v(TAG, "GET TRAILER");
        if(getListView(R.id.listView_trailers).getAdapter() == null
                || getListView(R.id.listView_trailers).getAdapter().getItem(0) == null) {
            return StringUtils.EMPTY;
        }

        Video video = (Video) getListView(R.id.listView_trailers).getItemAtPosition(0);
        Log.v(TAG, "VIDEO " + video);
        return video.getUrl();
    }

    private TextView getTextView(int id) {
        return (TextView) getView().findViewById(id);
    }

    private ImageView getImage(int id) {
        return (ImageView) getView().findViewById(id);
    }

    private ListView getListView(int id) {
        return (ListView) getView().findViewById(id);
    }

}
