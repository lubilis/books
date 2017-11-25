package app.test.com.testapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utility class that provides some useful methods to get device information
 *
 * @author omar.brugna
 */
public abstract class DeviceUtils {

    /**
     * Check if Network is available
     *
     * @param context an instance of {@link Context}
     * @return true if connection is available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean networkAvailable = activeNetwork != null && activeNetwork.isConnected();
        if (!networkAvailable)
            Logger.w("Network is not available");
        return networkAvailable;
    }
}