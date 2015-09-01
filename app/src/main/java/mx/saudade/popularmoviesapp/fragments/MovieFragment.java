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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mx.saudade.popularmoviesapp.R;
import mx.saudade.popularmoviesapp.activities.DetailActivity;
import mx.saudade.popularmoviesapp.adapters.MovieAdapter;
import mx.saudade.popularmoviesapp.data.AppLoaderManager;
import mx.saudade.popularmoviesapp.models.Manager;
import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Results;
import mx.saudade.popularmoviesapp.models.Review;
import mx.saudade.popularmoviesapp.models.Video;

public class MovieFragment extends Fragment{

    private static final String TAG = MovieFragment.class.getSimpleName();

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

        manager = new Manager(getActivity());

        if(savedInstanceState != null) {
            movies = ((Results<Movie>) savedInstanceState.getSerializable(ID_MOVIES)).getResults();
        }

        test();
        return rootView;
    }

    public void test() {
        AppLoaderManager manager = new AppLoaderManager(this.getActivity());

        //delete
        manager.deleteAllMovie("123");
        manager.deleteAllMovie("456");

        //insert movie
        Movie movie = new Movie();
        movie.setId(123);
        movie.setTitle("title");
        movie.setOverview("overview");
        movie.setReleaseDate("2015-01-23");
        movie.setPosterPath("path");
        movie.setPopularity((float) 12.2);
        movie.setVoteAverage(9);

        Log.v(TAG, "id inserción: " + manager.insertMovie(movie));
        Movie movieDB =  manager.getMovie("123");
        Log.v(TAG, movieDB == null ? "no se obtuvo una pelicula" : movieDB.toString());

        //insert movies
        List<Movie> movies = new ArrayList<Movie>();
        movie.setId(456);
        movies.add(movie);
        Log.v(TAG, "inserted movies: " + manager.insertMovies(movies));
        List<Movie> moviesDB = manager.getMovies();
        Log.v(TAG, moviesDB == null ? "no se obtuvieron peliculas" : moviesDB.toString());

        //insert reviews
        List<Review> reviews = new ArrayList<Review>();
        Review review = new Review();
        review.setContent("content");
        review.setAuthor("author");
        review.setUrl("url");
        reviews.add(review);
        Review review2 = new Review();
        review2.setContent("content2");
        review2.setAuthor("author2");
        review2.setUrl("url2");
        reviews.add(review2);

        Log.v(TAG, "inserted reviews " + manager.insertReviews("123", reviews));
        List<Review> reviewsDB = manager.getReviews("123");

        Log.v(TAG, reviewsDB == null ? "no se obtuvieron revisiones" : reviewsDB.size() + " " + reviewsDB.toString());

        //insert videos
        List<Video> videos = new ArrayList<Video>();
        Video video = new Video();
        video.setKey("key");
        video.setName("name");
        video.setSite("site");
        videos.add(video);

        Log.v(TAG, "inserted videos " + manager.insertVideos("123", videos));
        List<Video> videosDB = manager.getVideos("123");
        Log.v(TAG, videosDB == null ? "no se obtuvieron vídeos" : videosDB.size() + " " + videosDB.toString());


    }

    @Override
    public void onStart() {
        super.onStart();
        if (movies == null || movies.size() == 0) {
            manager.invokeRetro(gridView, notificationView);
            notificationView.setVisibility(View.VISIBLE);
        } else {
            MovieAdapter adapter = new MovieAdapter(getActivity());
            adapter.setResults(movies);
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
        //FIXME truena aqui la aplicación
        outState.putSerializable(ID_MOVIES, ((MovieAdapter)
                ((GridView) getView().findViewById(R.id.gridView)).getAdapter()).getResults());
    }

}
