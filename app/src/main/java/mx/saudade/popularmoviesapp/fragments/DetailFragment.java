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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.adapters.ReviewAdapter;
import mx.saudade.popularmoviesapp.adapters.VideoAdapter;
import mx.saudade.popularmoviesapp.data.AppLoaderManager;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Review;
import mx.saudade.popularmoviesapp.models.Video;
import mx.saudade.popularmoviesapp.utils.ActionUtils;
import mx.saudade.popularmoviesapp.utils.ListViewUtil;
import mx.saudade.popularmoviesapp.utils.NestedListView;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private AppLoaderManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new AppLoaderManager(getActivity());
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
        ActionUtils.share(provider, getMovie().getShareMessage() + ">>>");

        MenuItem favoriteItem = menu.findItem(R.id.action_favorite);

        if(manager.getMovie(getMovie().get_Id()) == null) {
            favoriteItem.setIcon(R.drawable.favorite_white_off);
        } else {
            favoriteItem.setIcon(R.drawable.favorite_white_on);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            changeStateAsFavorite(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadContent() {
        Manager manager = new Manager(getActivity());
        manager.invokeReviews(getMovie().getId(), getNestedListView(R.id.listView_reviews), getTextView(R.id.textView_loading));
        manager.invokeVideos(getMovie().getId(), getNestedListView(R.id.listView_trailers), getTextView(R.id.textView_loading));
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

        getNestedListView(R.id.listView_reviews).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = (Review) parent.getAdapter().getItem(position);
                ActionUtils.viewContent(getActivity(), review.getUrl());
            }
        });

        getNestedListView(R.id.listView_trailers).setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private List<Review> getReviews() {
        if(getNestedListView(R.id.listView_reviews).getAdapter() == null
                || ((ReviewAdapter) getNestedListView(R.id.listView_reviews).getAdapter()).getResults() == null) {
            return null;
        }
        List<Review> reviews = ((ReviewAdapter) getNestedListView(R.id.listView_reviews).getAdapter()).getResults().getResults();
        Log.v(TAG, reviews.size() + " " + reviews.toString());
        return reviews;
    }

    private List<Video> getVideos() {
        if(getNestedListView(R.id.listView_trailers).getAdapter() == null
                || ((VideoAdapter) getNestedListView(R.id.listView_trailers).getAdapter()).getResults() == null) {
            return null;
        }
        List<Video> videos = ((VideoAdapter) getNestedListView(R.id.listView_trailers).getAdapter()).getResults().getResults();
        Log.v(TAG, videos.size() + " " + videos.toString());
        return  videos;
    }

    private void changeStateAsFavorite(MenuItem item) {
        String message = "Info incomplete to save as favorite";
        if (getMovie() == null || getVideos() == null || getReviews() == null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.favorite_white_off);
        }

        if (manager.getMovie(getMovie().get_Id()) == null) {
            manager.insertAllMovieInfo(getMovie(), getReviews(), getVideos());
            message = "Movie saved as favorite";
            item.setIcon(R.drawable.favorite_white_on);
        } else {
            manager.deleteAllMovieInfo(getMovie().get_Id());
            message = "Movie removed from favorites";
            item.setIcon(R.drawable.favorite_white_off);
        }
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private String getTrailer() {
        Log.v(TAG, "GET TRAILER");
        Video video = getVideos().get(0);
        Log.v(TAG, "VIDEO " + video);
        return video == null? null: video.getUrl();
    }

    private TextView getTextView(int id) {
        return (TextView) getView().findViewById(id);
    }

    private ImageView getImage(int id) {
        return (ImageView) getView().findViewById(id);
    }

    private NestedListView getNestedListView(int id) {
        return (NestedListView) getView().findViewById(id);
    }

}
