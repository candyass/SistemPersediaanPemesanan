package com.unbaja.dewijaya.pemesanan.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.unbaja.dewijaya.pemesanan.database.entity.Faktur;
import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;
import com.unbaja.dewijaya.pemesanan.database.entity.Pembayaran;
import com.unbaja.dewijaya.pemesanan.database.entity.PemesananProduk;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;
import com.unbaja.dewijaya.pemesanan.model.QueryProduk;

import java.util.List;

/**
 * Created by sigit on 27/08/2018.
 */

@Dao
public abstract class FakturDAO {

    @Insert
    public abstract long insertFaktur(Faktur faktur);

    @Insert
    public abstract void insertPembayaran(Pembayaran pembayaran);

    @Insert
    public abstract void insertPemesananProduk(List<PemesananProduk> list);

    @Transaction
    public void simpanPemesanan(Faktur faktur, List<PemesananProduk> list, int total) {
        long idFaktur = insertFaktur(faktur);
        for(PemesananProduk p : list) {
            p.setFakturId(idFaktur);
        }
        Pembayaran pembayaran = new Pembayaran(idFaktur,total, 123);
        insertPemesananProduk(list);
        insertPembayaran(pembayaran);
    }

    @Query("SELECT idOutlet, idFaktur, foto, namaOutlet, tanggalPemesanan, jumlahPembayaran, t.totalPemesanan, latitude, longitude, patokan " +
    "FROM Outlet JOIN Faktur ON " + "Outlet.idOutlet = Faktur.outletId " +
    "JOIN (SELECT fakturId, SUM(kuantitas) as totalPemesanan FROM PemesananProduk GROUP BY fakturId) as t " +
    "ON Faktur.idFaktur = t.fakturId " + "JOIN Pembayaran ON t.fakturId = Pembayaran.fakturId AND idOutlet =:outletId")
    public abstract LiveData<List<QueryPemesanan>> getListPemesanan(long outletId);

    @Query("SELECT idOutlet, idFaktur, foto, namaOutlet, tanggalPemesanan, jumlahPembayaran, t.totalPemesanan, latitude, longitude, patokan " +
            "FROM Faktur LEFT OUTER JOIN Outlet ON Faktur.outletId = Outlet.idOutlet " +
            "JOIN (SELECT fakturId, SUM(kuantitas) as totalPemesanan FROM PemesananProduk GROUP BY fakturId) as t " +
            "ON Faktur.idFaktur = t.fakturId JOIN Pembayaran ON t.fakturId = Pembayaran.fakturId")
    public abstract LiveData<List<QueryPemesanan>> getListPemesanan();

    @Query("SELECT namaProduk, harga, kuantitas FROM Produk JOIN " +
            "PemesananProduk ON Produk.idProduk = PemesananProduk.produkId WHERE fakturId =:idFaktur")
    public abstract LiveData<List<QueryProduk>> getListPemesananProduk(long idFaktur);

}
