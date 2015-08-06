package mx.saudade.popularmoviesapp.utils;

import android.app.Notification;
import android.content.Intent;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;

/**
 * Created by angelicamendezvega on 8/5/15.
 */
public class ActionUtils {

    private static final String TAG = ActionUtils.class.getSimpleName();

    public static void share(ShareActionProvider provider, String message) {

        if (message == null || provider == null) {
            return;
        } else {
            Log.e(TAG, "Error" + provider.toString() + " " + message);
        }

        Intent i = new Intent(Intent.ACTION_SEND);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, message);

        provider.setShareIntent(i);
    }
}
