package com.unbaja.dewijaya.pemesanan.model;

/**
 * Created by sigit on 24/08/2018.
 */

public class ProdukEvent {

    private long idProduk;
    private String namaProduk;
    private int harga;
    private int jumlahPemesanan;


    public ProdukEvent(long idProduk, String namaProduk, int harga) {
        setIdProduk(idProduk);
        setNamaProduk(namaProduk);
        setHarga(harga);
        jumlahPemesanan = 0;
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

    public int getJumlahPemesanan() {
        return jumlahPemesanan;
    }

    public void setJumlahPemesanan(int jumlahPemesanan) {
        this.jumlahPemesanan = jumlahPemesanan;
    }

    public long getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(long idProduk) {
        this.idProduk = idProduk;
    }
}
