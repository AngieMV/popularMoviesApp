package mx.saudade.popularmoviesapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by angelicamendezvega on 8/26/15.
 */
public class AppContract {

    public static final String CONTENT_AUTHORITY = "mx.saudade.popularmoviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final String PATH_REVIEW = "review";

    public static final String PATH_VIDEO = "video";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String COLUMN_POPULARITY = "popularity";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final int INDEX_COLUMN_ID = 0;

        public static final int INDEX_COLUMN_TITLE = 1;

        public static final int INDEX_COLUMN_OVERVIEW = 2;

        public static final int INDEX_COLUMN_RELEASE_DATE = 3;

        public static final int INDEX_COLUMN_POSTER_PATH = 4;

        public static final int INDEX_COLUMN_POPULARITY = 5;

        public static final int INDEX_COLUMN_VOTE_AVERAGE = 6;

        public static final String[] COMPLETE_PROJECTION = {
                MovieEntry._ID
                , MovieEntry.COLUMN_TITLE
                , MovieEntry.COLUMN_OVERVIEW
                , MovieEntry.COLUMN_RELEASE_DATE
                , MovieEntry.COLUMN_POSTER_PATH
                , MovieEntry.COLUMN_POPULARITY
                , MovieEntry.COLUMN_VOTE_AVERAGE
        };

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String TABLE_NAME = "review";

        public static final String COLUMN_ID_MOVIE = "id_movie";

        public static final String COLUMN_REVIEW = "review";

        public static final String COLUMN_AUTHOR = "author";

        public static final String COLUMN_URL = "url";

        public static final int INDEX_COLUMN_ID = 0;

        public static final int INDEX_COLUMN_ID_MOVIE = 1;

        public static final int INDEX_COLUMN_REVIEW = 2;

        public static final int INDEX_COLUMN_AUTHOR = 3;

        public static final int INDEX_COLUMN_URL = 4;

        public static final String[] COMPLETE_PROJECTION = {
                ReviewEntry._ID
                , ReviewEntry.COLUMN_ID_MOVIE
                , ReviewEntry.COLUMN_REVIEW
                , ReviewEntry.COLUMN_AUTHOR
                , ReviewEntry.COLUMN_URL
        };

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class VideoEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEO).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEO;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEO;

        public static final String TABLE_NAME = "video";

        public static final String COLUMN_ID_MOVIE = "id_movie";

        public static final String COLUMN_KEY = "key";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_SITE = "site";

        public static final int INDEX_COLUMN_ID = 0;

        public static final int INDEX_COLUMN_ID_MOVIE = 1;

        public static final int INDEX_COLUMN_KEY = 2;

        public static final int INDEX_COLUMN_NAME = 3;

        public static final int INDEX_COLUMN_SITE = 4;

        public static final String[] COMPLETE_PROJECTION = {
                VideoEntry._ID
                , VideoEntry.COLUMN_ID_MOVIE
                , VideoEntry.COLUMN_KEY
                , VideoEntry.COLUMN_NAME
                , VideoEntry.COLUMN_SITE
        };

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
