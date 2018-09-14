package com.unbaja.dewijaya.pemesanan.util;

import android.util.Log;

/**
 * Created by sigit on 22/08/2018.
 */

public class MyLogger {

    private static final String TAG = "kopi";

    public static void logPesan(String pesan) {
        Log.d(TAG, pesan);
    }
}
