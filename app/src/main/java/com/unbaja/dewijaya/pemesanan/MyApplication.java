package com.unbaja.dewijaya.pemesanan;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.unbaja.dewijaya.pemesanan.database.LocalDatabase;

/**
 * Created by sigit on 20/08/2018.
 */

public class MyApplication extends Application {

    private LocalDatabase mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = Room.databaseBuilder(this, LocalDatabase.class, "PersediaanDB").build();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mDatabase = null;
    }

    public LocalDatabase getDatabase() {
        return mDatabase;
    }
}
