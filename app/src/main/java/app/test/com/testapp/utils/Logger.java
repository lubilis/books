package app.test.com.testapp.utils;

import android.util.Log;

import app.test.com.testapp.BuildConfig;

/**
 * Simple class to log with a specific tag
 *
 * @author omar.brugna
 */

public abstract class Logger {

    public static final String DIVIDER = " -> ";
    private static final String TAG = "TestApp";

    public static void v(String text) {
        v(TAG, text);
    }

    public static void d(String text) {
        d(TAG, text);
    }

    public static void i(String text) {
        i(TAG, text);
    }

    static void w(String text) {
        w(TAG, text);
    }

    public static void e(String text) {
        e(TAG, text);
    }

    private static void v(String tag, String text) {
        if (BuildConfig.DEBUG)
            Log.v(tag, text);
    }

    private static void d(String tag, String text) {
        if (BuildConfig.DEBUG)
            Log.d(tag, text);
    }

    private static void i(String tag, String text) {
        if (BuildConfig.DEBUG)
            Log.i(tag, text);
    }

    private static void w(String tag, String text) {
        if (BuildConfig.DEBUG)
            Log.w(tag, text);
    }

    private static void e(String tag, String text) {
        if (BuildConfig.DEBUG)
            Log.e(tag, text);
    }
}
