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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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
import mx.saudade.popularmoviesapp.views.ContentListView;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private AppLoaderManager appLoaderManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appLoaderManager = new AppLoaderManager(getActivity());
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
        if(appLoaderManager.getMovie(getMovie().get_Id()) == null) {
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
        Manager manager = new Manager(getActivity(), appLoaderManager);
        manager.getReviews(getMovie(), getContentListView(R.id.reviewsContentListView));
        manager.getVideos(getMovie(), getContentListView(R.id.trailersContentListView));
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

        getContentListView(R.id.reviewsContentListView).setAdapter(new ReviewAdapter(getActivity()));
        getContentListView(R.id.reviewsContentListView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = (Review) parent.getAdapter().getItem(position);
                ActionUtils.viewContent(getActivity(), review.getUrl());
            }
        });

        getContentListView(R.id.trailersContentListView).setAdapter(new VideoAdapter(getActivity()));
        getContentListView(R.id.trailersContentListView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        if(getContentListView(R.id.reviewsContentListView).getAdapter() == null
                || ((ReviewAdapter) getContentListView(R.id.reviewsContentListView).getAdapter()).getResults() == null) {
            return null;
        }
        List<Review> reviews = ((ReviewAdapter) getContentListView(R.id.reviewsContentListView).getAdapter()).getResults().getResults();
        Log.v(TAG, reviews.size() + " " + reviews.toString());
        return reviews;
    }

    private List<Video> getVideos() {
        if(getContentListView(R.id.trailersContentListView).getAdapter() == null
                || ((VideoAdapter) getContentListView(R.id.trailersContentListView).getAdapter()).getResults() == null) {
            return null;
        }
        List<Video> videos = ((VideoAdapter) getContentListView(R.id.trailersContentListView).getAdapter()).getResults().getResults();
        Log.v(TAG, videos.size() + " " + videos.toString());
        return  videos;
    }

    private void changeStateAsFavorite(MenuItem item) {
        String message = "Info incomplete to save as favorite";
        if (getMovie() == null || getVideos() == null || getReviews() == null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            item.setIcon(R.drawable.favorite_white_off);
        }

        if (appLoaderManager.getMovie(getMovie().get_Id()) == null) {
            appLoaderManager.insertAllMovieInfo(getMovie(), getReviews(), getVideos());
            message = "Movie saved as favorite";
            item.setIcon(R.drawable.favorite_white_on);
        } else {
            appLoaderManager.deleteAllMovieInfo(getMovie().get_Id());
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

    private ContentListView getContentListView(int id) {
        return (ContentListView) getView().findViewById(id);
    }

}
