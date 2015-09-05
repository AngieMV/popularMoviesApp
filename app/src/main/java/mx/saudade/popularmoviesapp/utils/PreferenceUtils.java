package mx.saudade.popularmoviesapp.utils;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import mx.saudade.popularmoviesapp.R;

/**
 * Created by angie on 9/5/15.
 */
public class PreferenceUtils {

    private static final String TAG = PreferenceUtils.class.getSimpleName();

    private PreferenceUtils() {

    }

    public static boolean getBoolean(Context context, int keyId) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(keyId)
                , false);
    }

    public static String getString(Context context, int keyId, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(keyId), context.getString(defaultValue));
    }

    public static void setBoolean(Context context, int keyId, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(context.getString(keyId), value).commit();
    }

    public static void setString(Context context, int keyId, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(
                context.getString(keyId), value).commit();
    }

    public static void setPrevPreferenceOrder(Context context) {
        String order = getString(context, R.string.pref_sortby_key, R.string.pref_sortby_default_value);
        setString(context, R.string.pref_sortby_previous_key, order);
    }

    public static void setPrevPreferenceFavorite(Context context) {
        boolean prev_fav = getBoolean(context, R.string.pref_fav_key);
        setBoolean(context, R.string.pref_fav_previous_key, prev_fav);
    }

    public static boolean isNewFavoriteConfig(Context context) {
        boolean newFav = getBoolean(context, R.string.pref_fav_key);
        boolean prevFav = getBoolean(context, R.string.pref_fav_previous_key);
        return newFav != prevFav;
    }

    public static boolean isNewOrderConfig(Context context) {
        String newOrder = getString(context, R.string.pref_sortby_key, R.string.pref_sortby_default_value);
        String prevOrder = getString(context, R.string.pref_sortby_previous_key, R.string.pref_sortby_default_value);
        return newOrder != prevOrder;
    }

}
