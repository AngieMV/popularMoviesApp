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
import mx.saudade.popularmoviesapp.views.ContentListView;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    private AppLoaderManager appLoaderManager;

    private VideoAdapter videoAdapter;

    private ReviewAdapter reviewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appLoaderManager = new AppLoaderManager(getActivity());
        videoAdapter = new VideoAdapter(getActivity(), 2);
        reviewAdapter = new ReviewAdapter(getActivity(), 3);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        setHasOptionsMenu(true);
        Log.v(TAG, "XXX onCreateView");
        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        getContentListView(R.id.trailersContentListView).restoreState(savedInstanceState, videoAdapter);
        getContentListView(R.id.reviewsContentListView).restoreState(savedInstanceState, reviewAdapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getContentListView(R.id.trailersContentListView).saveState(outState);
        getContentListView(R.id.reviewsContentListView).saveState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v(TAG, "XXX onStart");
        displayInfo();
        loadContent();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        final ShareActionProvider provider = (ShareActionProvider) MenuItemCompat.getActionProvider(menu.findItem(R.id.action_share));
        ActionUtils.share(provider, getMovie().getShareMessage(getTrailer()));
        provider.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent) {
                ActionUtils.share(shareActionProvider, getMovie().getShareMessage(getTrailer()));
                return false;
            }
        });

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
        List<Video> videos = getContentListView(R.id.trailersContentListView).getResults();
        List<Review> reviews = getContentListView(R.id.reviewsContentListView).getResults();
        if (videos.size() == 0) {
            Log.v(TAG, "XXX calling getVideos");
            manager.getVideos(getMovie(), getContentListView(R.id.trailersContentListView));
        }
        if (reviews.size() == 0) {
            Log.v(TAG, "XXX calling getReviews");
            manager.getReviews(getMovie(), getContentListView(R.id.reviewsContentListView));
        }
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

        getContentListView(R.id.reviewsContentListView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = (Review) parent.getAdapter().getItem(position);
                ActionUtils.viewContent(getActivity(), review.getUrl());
            }
        });

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
        List<Review> reviews = getContentListView(R.id.reviewsContentListView).getResults();
        Log.v(TAG, reviews.size() + " " + reviews.toString());
        return reviews;
    }

    private List<Video> getVideos() {
        List<Video> videos = getContentListView(R.id.trailersContentListView).getResults();
        Log.v(TAG, videos.size() + " " + videos.toString());
        return  videos;
    }

    private void changeStateAsFavorite(MenuItem item) {
        String message = StringUtils.EMPTY;
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
        Video video = getVideos().size() > 0 ? getVideos().get(0) : null;
        Log.v(TAG, "VIDEO " + video);
        return video == null? StringUtils.EMPTY : video.getUrl();
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
