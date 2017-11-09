package com.wingjay.autolifecycle.library;

import android.util.Log;

/**
 * ALog
 *
 * @author wingjay
 * @date 2017/11/09
 */
public class ALog {
    private static final String TAG = "AutoLifecycle";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }
}
