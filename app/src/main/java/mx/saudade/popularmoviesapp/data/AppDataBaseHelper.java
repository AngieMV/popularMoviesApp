package mx.saudade.popularmoviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import mx.saudade.popularmoviesapp.data.AppContract.*;

/**
 * Created by angelicamendezvega on 8/26/15.
 */
public class AppDataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "movies.db";

    public AppDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_TITLE + " VARCHAR(100), " +
                MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
                MovieEntry.COLUMN_RELEASE_DATE + " VARCHAR(10), " +
                MovieEntry.COLUMN_POSTER_PATH + " VARCHAR(50), " +
                MovieEntry.COLUMN_POPULARITY + " DOUBLE, " +
                MovieEntry.COLUMN_VOTE_AVERAGE + " DOUBLE " +
            ");";

        final String SQL_CREATE_REVIEW_TABLE =
            "CREATE TABLE " + ReviewEntry.TABLE_NAME + " (" +
                ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ReviewEntry.COLUMN_ID_MOVIE + " INTEGER, " +
                ReviewEntry.COLUMN_REVIEW + " TEXT, " +
                ReviewEntry.COLUMN_AUTHOR + " VARCHAR(100), " +
                ReviewEntry.COLUMN_URL + " VARCHAR(255), " +
                "FOREIGN KEY (" + ReviewEntry.COLUMN_ID_MOVIE + ") REFERENCES "
                    + MovieEntry.TABLE_NAME + " ( "+ MovieEntry._ID +")" +
            ");";

        final String SQL_CREATE_VIDEO_TABLE =
            "CREATE TABLE " + VideoEntry.TABLE_NAME + " (" +
                VideoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VideoEntry.COLUMN_ID_MOVIE + " INTEGER, " +
                VideoEntry.COLUMN_KEY + " VARCHAR(50), " +
                VideoEntry.COLUMN_NAME + " VARCHAR(50), "+
                VideoEntry.COLUMN_SITE + " VARCHAR(50), " +
                "FOREIGN KEY (" + VideoEntry.COLUMN_ID_MOVIE + ") REFERENCES "
                    + MovieEntry.TABLE_NAME + " ( "+ MovieEntry._ID +")" +
            ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_REVIEW_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_VIDEO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        final String SQL_DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
        final String SQL_DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS " + ReviewEntry.TABLE_NAME;
        final String SQL_DROP_VIDEO_TABLE = "DROP TABLE IF EXISTS " + VideoEntry.TABLE_NAME;

        sqLiteDatabase.execSQL(SQL_DROP_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_REVIEW_TABLE);
        sqLiteDatabase.execSQL(SQL_DROP_VIDEO_TABLE);

        this.onCreate(sqLiteDatabase);
    }

}
