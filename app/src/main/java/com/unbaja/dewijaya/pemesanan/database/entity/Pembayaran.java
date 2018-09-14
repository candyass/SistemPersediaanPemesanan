package com.unbaja.dewijaya.pemesanan.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sigit on 23/08/2018.
 */

@Entity(foreignKeys = @ForeignKey(parentColumns = "idFaktur", childColumns = "fakturId", entity = Faktur.class))
public class Pembayaran {

    @PrimaryKey(autoGenerate = true)
    private long idPembayaran;
    private long fakturId;
    private int jumlahPembayaran;
    private int kodePembayaran;

    @Ignore
    public Pembayaran(long fakturId, int jumlahPembayaran, int kodePembayaran) {
        setFakturId(fakturId);
        setJumlahPembayaran(jumlahPembayaran);
        setKodePembayaran(kodePembayaran);
    }

    public Pembayaran(long idPembayaran, long fakturId, int jumlahPembayaran, int kodePembayaran) {
        setIdPembayaran(idPembayaran);
        setFakturId(fakturId);
        setJumlahPembayaran(jumlahPembayaran);
        setKodePembayaran(kodePembayaran);
    }


    public long getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(long idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public long getFakturId() {
        return fakturId;
    }

    public void setFakturId(long fakturId) {
        this.fakturId = fakturId;
    }

    public int getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public void setJumlahPembayaran(int jumlahPembayaran) {
        this.jumlahPembayaran = jumlahPembayaran;
    }

    public int getKodePembayaran() {
        return kodePembayaran;
    }

    public void setKodePembayaran(int kodePembayaran) {
        this.kodePembayaran = kodePembayaran;
    }
}
