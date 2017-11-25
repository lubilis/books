package app.test.com.testapp.utils;

import android.content.Context;
import android.os.Build;

/**
 * Utility class that provides some useful methods to use deprecated methods
 * according Android os version
 *
 * @author omar.brugna
 */
@SuppressWarnings("unused")
public abstract class VersionUtils {

    private static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean hasMarshMallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    static boolean hasNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    @SuppressWarnings("deprecation")
    public static int getColor(Context context, int id) {
        if (hasMarshMallow())
            return context.getResources().getColor(id, context.getTheme());
        return context.getResources().getColor(id);
    }
}
