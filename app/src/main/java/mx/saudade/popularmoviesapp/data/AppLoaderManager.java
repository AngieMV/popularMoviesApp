package mx.saudade.popularmoviesapp.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import mx.saudade.popularmoviesapp.models.Movie;
import mx.saudade.popularmoviesapp.models.Review;
import mx.saudade.popularmoviesapp.models.Video;

/**
 * Created by angelicamendezvega on 8/31/15.
 */
public class AppLoaderManager {

    private static final String TAG = AppLoaderManager.class.getSimpleName();

    private Context context;

    public AppLoaderManager(Context context){
        this.context = context;
    }

    public Movie getMovie(String id) {
        if(StringUtils.isEmpty(id)) {
            return null;
        }
        Movie movie = null;
        Cursor cursor = context.getContentResolver().query(
                AppContract.MovieEntry.CONTENT_URI,
                AppContract.MovieEntry.COMPLETE_PROJECTION,
                AppContract.MovieEntry._ID + "= ?",
                new String[]{id},
                null);
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            movie = new Movie();
            movie.setId(cursor.getInt(AppContract.MovieEntry.INDEX_COLUMN_ID));
            movie.setTitle(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_TITLE));
            movie.setOverview(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_OVERVIEW));
            movie.setReleaseDate(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_RELEASE_DATE));
            movie.setPosterPath(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_POSTER_PATH));
            movie.setPopularity(cursor.getFloat(AppContract.MovieEntry.INDEX_COLUMN_POPULARITY));
            movie.setVoteAverage(cursor.getFloat(AppContract.MovieEntry.INDEX_COLUMN_VOTE_AVERAGE));
        }
        cursor.close();
        return movie;
    }

    public List<Movie> getMovies() {

        List<Movie> movies = null;
        Cursor cursor = context.getContentResolver().query(
                AppContract.MovieEntry.CONTENT_URI,
                AppContract.MovieEntry.COMPLETE_PROJECTION,
                null,
                null,
                null
        );
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            movies = new ArrayList<Movie>();
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(AppContract.MovieEntry.INDEX_COLUMN_ID));
                movie.setTitle(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_TITLE));
                movie.setOverview(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_OVERVIEW));
                movie.setReleaseDate(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_RELEASE_DATE));
                movie.setPosterPath(cursor.getString(AppContract.MovieEntry.INDEX_COLUMN_POSTER_PATH));
                movie.setPopularity(cursor.getFloat(AppContract.MovieEntry.INDEX_COLUMN_POPULARITY));
                movie.setVoteAverage(cursor.getFloat(AppContract.MovieEntry.INDEX_COLUMN_VOTE_AVERAGE));
                movies.add(movie);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }

    public long insertMovie(Movie movie) {
        if (movie == null) {
            return 0;
        }
        ContentValues values = new ContentValues();
        values.put(AppContract.MovieEntry._ID, movie.getId());
        values.put(AppContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(AppContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(AppContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        values.put(AppContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(AppContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
        values.put(AppContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

        Uri insertedUri = context.getContentResolver().insert(AppContract.MovieEntry.CONTENT_URI, values);
        return ContentUris.parseId(insertedUri);
    }

    public long insertMovies(List<Movie> movies) {
        if (movies == null || movies.size() == 0) {
            return 0;
        }
        ContentValues[] moviesArray = new ContentValues[movies.size()];
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            moviesArray[i] = new ContentValues();
            moviesArray[i].put(AppContract.MovieEntry._ID, movie.getId());
            moviesArray[i].put(AppContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
            moviesArray[i].put(AppContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            moviesArray[i].put(AppContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            moviesArray[i].put(AppContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            moviesArray[i].put(AppContract.MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
            moviesArray[i].put(AppContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        }
        return context.getContentResolver().bulkInsert(AppContract.MovieEntry.CONTENT_URI, moviesArray);
    }

    public int deleteMovie(String movieId) {
        return context.getContentResolver().delete(AppContract.MovieEntry.CONTENT_URI
                , AppContract.MovieEntry._ID + " = ?"
                , new String[]{movieId});
    }

    public List<Review> getReviews(String movieId) {
        if (StringUtils.isEmpty(movieId)) {
            return null;
        }
        List<Review> reviews = null;
        Cursor cursor = context.getContentResolver().query(
                AppContract.ReviewEntry.CONTENT_URI,
                AppContract.ReviewEntry.COMPLETE_PROJECTION,
                AppContract.ReviewEntry.COLUMN_ID_MOVIE + " = ?",
                new String[]{movieId},
                null
        );
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            reviews = new ArrayList<Review>();
            do {
                Review review = new Review();
                review.setContent(cursor.getString(AppContract.ReviewEntry.INDEX_COLUMN_REVIEW));
                review.setAuthor(cursor.getString(AppContract.ReviewEntry.INDEX_COLUMN_AUTHOR));
                review.setUrl(cursor.getString(AppContract.ReviewEntry.INDEX_COLUMN_URL));
                reviews.add(review);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return reviews;
    }

    public long insertReviews(String movieId, List<Review> reviews) {
        if (StringUtils.isEmpty(movieId) || reviews == null || reviews.size() == 0) {
            return 0;
        }
        ContentValues[] reviewsArray = new ContentValues[reviews.size()];
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            reviewsArray[i] = new ContentValues();
            reviewsArray[i].put(AppContract.ReviewEntry.COLUMN_ID_MOVIE, movieId);
            reviewsArray[i].put(AppContract.ReviewEntry.COLUMN_REVIEW, review.getContent());
            reviewsArray[i].put(AppContract.ReviewEntry.COLUMN_AUTHOR, review.getAuthor());
            reviewsArray[i].put(AppContract.ReviewEntry.COLUMN_URL, review.getUrl());
        }
        return context.getContentResolver().bulkInsert(AppContract.ReviewEntry.CONTENT_URI, reviewsArray);
    }

    public int deleteReviews(String movieId) {
        return context.getContentResolver().delete(AppContract.ReviewEntry.CONTENT_URI
                , AppContract.ReviewEntry.COLUMN_ID_MOVIE + " = ?"
                , new String[]{movieId});
    }

    public List<Video> getVideos(String movieId) {
        if (StringUtils.isEmpty(movieId)) {
            return null;
        }
        List<Video> videos = null;
        Cursor cursor = context.getContentResolver().query(
                AppContract.VideoEntry.CONTENT_URI,
                AppContract.VideoEntry.COMPLETE_PROJECTION,
                AppContract.VideoEntry.COLUMN_ID_MOVIE + " = ?",
                new String[]{movieId},
                null
        );
        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            videos = new ArrayList<Video>();
            do {
                Video video = new Video();
                video.setKey(cursor.getString(AppContract.VideoEntry.INDEX_COLUMN_KEY));
                video.setName(cursor.getString(AppContract.VideoEntry.INDEX_COLUMN_NAME));
                video.setSite(cursor.getString(AppContract.VideoEntry.INDEX_COLUMN_SITE));
                videos.add(video);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return videos;
    }

    public long insertVideos(String movieId, List<Video> videos) {
        if (StringUtils.isEmpty(movieId) || videos == null || videos.size() == 0) {
            return 0;
        }
        ContentValues[] videosArray = new ContentValues[videos.size()];
        for (int i = 0; i < videos.size(); i++) {
            Video video = videos.get(i);
            videosArray[i] = new ContentValues();
            videosArray[i].put(AppContract.VideoEntry.COLUMN_ID_MOVIE, movieId);
            videosArray[i].put(AppContract.VideoEntry.COLUMN_KEY, video.getKey());
            videosArray[i].put(AppContract.VideoEntry.COLUMN_NAME, video.getName());
            videosArray[i].put(AppContract.VideoEntry.COLUMN_SITE, video.getSite());
        }
        return context.getContentResolver().bulkInsert(AppContract.VideoEntry.CONTENT_URI, videosArray);
    }

    public int deleteVideos(String movieId) {
        return context.getContentResolver().delete(AppContract.VideoEntry.CONTENT_URI
                , AppContract.VideoEntry.COLUMN_ID_MOVIE + " = ?"
                , new String[]{movieId});
    }

    public void deleteAllMovie(String movieId) {
        int videosDeleted = deleteVideos(movieId);
        int reviewsDeleted = deleteReviews(movieId);
        int moviesDeleted = deleteMovie(movieId);
        Log.v(TAG, "Movies deleted: " + moviesDeleted + " reviews deleted: " + reviewsDeleted + " videos deleted: " + videosDeleted);
    }

}
