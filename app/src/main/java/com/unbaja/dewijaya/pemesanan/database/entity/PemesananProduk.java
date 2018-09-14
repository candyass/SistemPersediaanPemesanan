package com.unbaja.dewijaya.pemesanan.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;

/**
 * Created by sigit on 23/08/2018.
 */
@Entity(primaryKeys = {"fakturId","produkId"},
        foreignKeys = {
                @ForeignKey(parentColumns = "idFaktur", childColumns = "fakturId", entity = Faktur.class),
                @ForeignKey(parentColumns = "idProduk", childColumns = "produkId", entity = Produk.class)
        })
public class PemesananProduk {

    private long fakturId;
    private long produkId;
    private int kuantitas;

    @Ignore
    public PemesananProduk(long produkId, int kuantitas) {
        setProdukId(produkId);
        setKuantitas(kuantitas);
    }

    public PemesananProduk(long fakturId, long produkId, int kuantitas) {
        setFakturId(fakturId);
        setProdukId(produkId);
        setKuantitas(kuantitas);
    }


    public long getFakturId() {
        return fakturId;
    }

    public void setFakturId(long fakturId) {
        this.fakturId = fakturId;
    }

    public long getProdukId() {
        return produkId;
    }

    public void setProdukId(long produkId) {
        this.produkId = produkId;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
}
