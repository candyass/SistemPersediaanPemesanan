package com.unbaja.dewijaya.pemesanan.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

/**
 * Created by sigit on 31/07/2018.
 */
@Entity
public class Outlet {

    @PrimaryKey(autoGenerate = true)
    private long idOutlet;
    private String namaOutlet;
    private String namaPemilik;
    private String email;
    private String noTelepon;
    private String katasandi;
    private Bitmap foto;
    private String namaTempat;
    private double latitude;
    private double longitude;
    private String patokan;

    @Ignore
    public Outlet() {

    }

    @Ignore
    public Outlet(String email, String katasandi, String namaPemilik) {
        setEmail(email);
        setKatasandi(katasandi);
        setNamaPemilik(namaPemilik);
    }

    @Ignore
    public Outlet(String email, String katasandi) {
        this.setEmail(email);
        this.setKatasandi(katasandi);
    }

    public Outlet(long idOutlet,String namaOutlet, String namaPemilik, String email, String katasandi,String noTelepon, Bitmap foto,
                  String namaTempat, double latitude, double longitude, String patokan) {
        setIdOutlet(idOutlet);
        setNamaOutlet(namaOutlet);
        setNamaPemilik(namaPemilik);
        setEmail(email);
        setNoTelepon(noTelepon);
        setKatasandi(katasandi);
        setFoto(foto);
        setNamaTempat(namaTempat);
        setLatitude(latitude);
        setLongitude(longitude);
        setPatokan(patokan);
    }


    public long getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(long idOutlet) {
        this.idOutlet = idOutlet;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKatasandi() {
        return katasandi;
    }

    public void setKatasandi(String katasandi) {
        this.katasandi = katasandi;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public String getNamaTempat() {
        return namaTempat;
    }

    public void setNamaTempat(String namaTempat) {
        this.namaTempat = namaTempat;
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

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    @Override
    public String toString() {
        return "Nama Pemilik : " + namaPemilik + "\nNama Outlet : " + namaOutlet + "\n" +
                "Email : " + email + "\n" + "Katasandi : " + katasandi;
    }

    public String getPatokan() {
        return patokan;
    }

    public void setPatokan(String patokan) {
        this.patokan = patokan;
    }
}
