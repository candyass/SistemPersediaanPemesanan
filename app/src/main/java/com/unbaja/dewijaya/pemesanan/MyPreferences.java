package com.unbaja.dewijaya.pemesanan;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sigit on 20/08/2018.
 */

public class MyPreferences {

    private static final String MY_PREF = "com.unbaja.dewijaya.pemesanan.preferences";
    private static final String KEY_BOOLEAN = "com.unbaja.dewijaya.pemesanan.preferences.key.boolean";

    private static MyPreferences singleton;

    private SharedPreferences mPreferences;

    public static MyPreferences getInstance(Context context) {
        if(singleton == null) {
            singleton = new MyPreferences(context);
        }
        return singleton;
    }

    private MyPreferences(Context context) {
        mPreferences = context.getSharedPreferences(MY_PREF, Context.MODE_PRIVATE);
    }

    public void saveBoolean(boolean value) {
        mPreferences.edit().putBoolean(KEY_BOOLEAN, value).commit();
    }

    public boolean getBoolean() {
        return mPreferences.getBoolean(KEY_BOOLEAN, false);
    }

}
