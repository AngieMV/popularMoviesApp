package mx.saudade.popularmoviesapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by angelicamendezvega on 8/26/15.
 */
public class AppProvider extends ContentProvider {

    static final int MOVIE = 101;
    static final int REVIEW = 102;
    static final int VIDEO  = 103;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private AppDataBaseHelper dataBaseHelper;

    @Override
    public boolean onCreate() {
        dataBaseHelper = new AppDataBaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);
        switch(match) {
            case MOVIE:
                return AppContract.MovieEntry.CONTENT_TYPE;
            case REVIEW:
                return AppContract.ReviewEntry.CONTENT_TYPE;
            case VIDEO:
                return AppContract.VideoEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor result;
        final int match = uriMatcher.match(uri);
        switch(match) {
            case MOVIE: {
                result = dataBaseHelper.getReadableDatabase().query(AppContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case REVIEW: {
                result = dataBaseHelper.getReadableDatabase().query(AppContract.ReviewEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            case VIDEO: {
                result = dataBaseHelper.getReadableDatabase().query(AppContract.VideoEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri result;
        switch(match) {
            case MOVIE: {
                long id = db.insert(AppContract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    result = AppContract.MovieEntry.buildLocationUri(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case REVIEW: {
                long id = db.insert(AppContract.ReviewEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    result = AppContract.ReviewEntry.buildLocationUri(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case VIDEO: {
                long id = db.insert(AppContract.VideoEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    result = AppContract.VideoEntry.buildLocationUri(id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        switch(match) {
            case MOVIE:
                return setTransaction(db, AppContract.MovieEntry.TABLE_NAME, uri, values);
            case REVIEW:
                return setTransaction(db, AppContract.ReviewEntry.TABLE_NAME, uri, values);
            case VIDEO:
                return setTransaction(db, AppContract.VideoEntry.TABLE_NAME, uri, values);
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private int setTransaction(SQLiteDatabase db, String tableName, Uri uri, ContentValues[] values) {
        db.beginTransaction();
        int count = 0;
        try {
            for (ContentValues value : values) {
                long id = db.insert(tableName, null, value);
                if (id != -1) {
                    count++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsDeleted;
        if (selection == null) selection = "1";
        switch(match) {
            case MOVIE:
                rowsDeleted = db.delete(AppContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case REVIEW:
                rowsDeleted = db.delete(AppContract.ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case VIDEO:
                rowsDeleted = db.delete(AppContract.VideoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowsUpdated = 0;
        switch(match) {
            case MOVIE:
                rowsUpdated = db.update(AppContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case REVIEW:
                rowsUpdated = db.update(AppContract.ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case VIDEO:
                rowsUpdated = db.update(AppContract.VideoEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = AppContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, AppContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, AppContract.PATH_REVIEW, REVIEW);
        matcher.addURI(authority, AppContract.PATH_VIDEO, VIDEO);

        return matcher;
    }

}
