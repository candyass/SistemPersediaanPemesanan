package com.unbaja.dewijaya.pemesanan.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.unbaja.dewijaya.pemesanan.database.entity.Produk;

import java.util.List;

/**
 * Created by sigit on 31/07/2018.
 */

@Dao
public interface ProdukDAO {

    @Insert
    public void insertProduk(List<Produk> produks);

    @Query("SELECT * FROM Produk")
    public LiveData<List<Produk>> getAllProduk();
}
