package com.unbaja.dewijaya.pemesanan.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sigit on 31/07/2018.
 */

@Entity
public class Produk {

    @PrimaryKey(autoGenerate = true)
    private long idProduk;
    private String namaProduk;
    private int gambar;
    private String spesifikasi;
    private int harga;

    @Ignore
    public Produk(String namaProduk, int gambar,int harga, String spesifikasi) {
        setNamaProduk(namaProduk);
        setGambar(gambar);
        setHarga(harga);
        setSpesifikasi(spesifikasi);
    }

    public Produk(long idProduk, String namaProduk, int gambar, String spesifikasi, int harga) {
        setIdProduk(idProduk);
        setNamaProduk(namaProduk);
        setGambar(gambar);
        setSpesifikasi(spesifikasi);
        setHarga(harga);
    }


    public long getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(long idProduk) {
        this.idProduk = idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public String getSpesifikasi() {
        return spesifikasi;
    }

    public void setSpesifikasi(String spesifikasi) {
        this.spesifikasi = spesifikasi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
