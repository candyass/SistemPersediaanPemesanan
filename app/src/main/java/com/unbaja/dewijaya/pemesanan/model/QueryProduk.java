package com.unbaja.dewijaya.pemesanan.model;

/**
 * Created by sigit on 02/09/2018.
 */

public class QueryProduk {

    private String namaProduk;
    private int harga;
    private int kuantitas;

    public QueryProduk(String namaProduk, int harga, int kuantitas) {
        setNamaProduk(namaProduk);
        setHarga(harga);
        setKuantitas(kuantitas);
    }


    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;
    }
}
