package com.unbaja.dewijaya.pemesanan.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by sigit on 23/08/2018.
 */
@Entity(foreignKeys = @ForeignKey(parentColumns = "idOutlet",childColumns = "outletId",entity = Outlet.class))
public class Faktur {

    @PrimaryKey(autoGenerate = true)
    private long idFaktur;
    private long outletId;
    private Date tanggalPemesanan;

    @Ignore
    public Faktur(long outletId, Date tanggalPemesanan) {
        setOutletId(outletId);
        setTanggalPemesanan(tanggalPemesanan);
    }

    public Faktur(long idFaktur, long outletId, Date tanggalPemesanan) {
        setIdFaktur(idFaktur);
        setOutletId(outletId);
        setTanggalPemesanan(tanggalPemesanan);
    }


    public long getIdFaktur() {
        return idFaktur;
    }

    public void setIdFaktur(long idFaktur) {
        this.idFaktur = idFaktur;
    }

    public long getOutletId() {
        return outletId;
    }

    public void setOutletId(long outletId) {
        this.outletId = outletId;
    }

    public Date getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(Date tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }
}
