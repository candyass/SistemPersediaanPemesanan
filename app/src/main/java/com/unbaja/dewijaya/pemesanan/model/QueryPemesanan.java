package com.unbaja.dewijaya.pemesanan.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by sigit on 29/08/2018.
 */

public class QueryPemesanan {

    private long idOutlet;
    private long idFaktur;
    private Bitmap foto;
    private String namaOutlet;
    private Date tanggalPemesanan;
    private int jumlahPembayaran;
    private int totalPemesanan;
    private double latitude;
    private double longitude;
    private String patokan;

    public QueryPemesanan(long idOutlet, long idFaktur, Bitmap foto, String namaOutlet, Date tanggalPemesanan,
                          int jumlahPembayaran, int totalPemesanan, double latitude, double longitude, String patokan) {
        setIdOutlet(idOutlet);
        setIdFaktur(idFaktur);
        setFoto(foto);
        setNamaOutlet(namaOutlet);
        setTanggalPemesanan(tanggalPemesanan);
        setJumlahPembayaran(jumlahPembayaran);
        setTotalPemesanan(totalPemesanan);
        setLatitude(latitude);
        setLongitude(longitude);
        setPatokan(patokan);
    }


    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public Date getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    public void setTanggalPemesanan(Date tanggalPemesanan) {
        this.tanggalPemesanan = tanggalPemesanan;
    }

    public int getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public void setJumlahPembayaran(int jumlahPembayaran) {
        this.jumlahPembayaran = jumlahPembayaran;
    }

    public int getTotalPemesanan() {
        return totalPemesanan;
    }

    public void setTotalPemesanan(int totalPemesanan) {
        this.totalPemesanan = totalPemesanan;
    }

    public long getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(long idOutlet) {
        this.idOutlet = idOutlet;
    }

    public long getIdFaktur() {
        return idFaktur;
    }

    public void setIdFaktur(long idFaktur) {
        this.idFaktur = idFaktur;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPatokan() {
        return patokan;
    }

    public void setPatokan(String patokan) {
        this.patokan = patokan;
    }
}
