package com.unbaja.dewijaya.pemesanan.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.unbaja.dewijaya.pemesanan.database.dao.FakturDAO;
import com.unbaja.dewijaya.pemesanan.database.dao.OutletDAO;
import com.unbaja.dewijaya.pemesanan.database.dao.ProdukDAO;
import com.unbaja.dewijaya.pemesanan.database.entity.BitmapConverter;
import com.unbaja.dewijaya.pemesanan.database.entity.DateConverter;
import com.unbaja.dewijaya.pemesanan.database.entity.Faktur;
import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;
import com.unbaja.dewijaya.pemesanan.database.entity.Pembayaran;
import com.unbaja.dewijaya.pemesanan.database.entity.PemesananProduk;
import com.unbaja.dewijaya.pemesanan.database.entity.Produk;

/**
 * Created by sigit on 31/07/2018.
 */
@Database(entities = {Outlet.class, Produk.class, Faktur.class, Pembayaran.class, PemesananProduk.class}, version = 1, exportSchema = false)
@TypeConverters({BitmapConverter.class, DateConverter.class})
public abstract class LocalDatabase extends RoomDatabase {

    public abstract ProdukDAO getProdukDAO();
    public abstract OutletDAO getOutletDAO();
    public abstract FakturDAO getFakturDAO();
}
