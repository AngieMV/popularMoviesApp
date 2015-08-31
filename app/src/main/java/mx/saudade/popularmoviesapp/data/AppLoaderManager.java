package mx.saudade.popularmoviesapp.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import mx.saudade.popularmoviesapp.models.Movie;

/**
 * Created by angelicamendezvega on 8/31/15.
 */
public class AppLoaderManager implements LoaderManager.LoaderCallbacks<Cursor>  {

    //insertar movie, reviews, videos

    //consultar movies, reviews, videos

    //eliminar movie, reviews, videos

    public int isMovieInDB(Context context, Movie movie) {

        int id = 0;

        Cursor movieCursor = context.getContentResolver().query(
                AppContract.MovieEntry.CONTENT_URI,
                new String[]{AppContract.MovieEntry._ID},
                AppContract.MovieEntry._ID + "= ?",
                new String[]{movie.get_Id()},
                null);

        if (movieCursor.moveToFirst()) {
            int movieIdIndex = movieCursor.getColumnIndex(AppContract.MovieEntry._ID);
            id = movieCursor.getInt(movieIdIndex);
        }

        movieCursor.close();

        return id;
    }

    public long insertMovie(Context context, Movie movie) {

        int id = isMovieInDB(context, movie);
        if (id != 0) {
            return id;
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
        id = (int) ContentUris.parseId(insertedUri);

        return id;
    }




    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
