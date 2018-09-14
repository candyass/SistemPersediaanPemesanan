package com.unbaja.dewijaya.pemesanan.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;

/**
 * Created by sigit on 01/08/2018.
 */

@Dao
public interface OutletDAO {


    @Insert
    public long insertOutlet(Outlet outlet);

    @Query("SELECT idOutlet FROM Outlet WHERE email =:email AND katasandi =:katasandi")
    public long getLoginOutletId(String email, String katasandi);

    @Query("SELECT * FROM Outlet WHERE idOutlet =:idOutlet")
    public LiveData<Outlet> getOutlet(long idOutlet);

    @Query("SELECT namaOutlet FROM Outlet WHERE idOutlet =:idOutlet")
    public LiveData<String> getNamaOutlet(long idOutlet);

}
