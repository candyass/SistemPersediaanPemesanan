package com.unbaja.dewijaya.pemesanan.model;

import android.graphics.Bitmap;

import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;

/**
 * Created by sigit on 16/08/2018.
 */

public class LoginEvent {

    public static final int EVENT_DAFTAR_OUTLET = 100;
    public static final int EVENT_DAFTAR_LOKASI = 101;
    public static final int EVENT_LOGIN = 102;

    private boolean isLogin;
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


    public static LoginEvent createEventDaftarOutlet() {
        LoginEvent event = new LoginEvent(EVENT_DAFTAR_OUTLET);
        return event;
    }

    public static LoginEvent createEventDaftarLokasi() {
        LoginEvent event = new LoginEvent(EVENT_DAFTAR_LOKASI);
        return event;
    }

    public static LoginEvent createEventLogin() {
        LoginEvent event = new LoginEvent(EVENT_LOGIN);
        return event;
    }


    private int statusEvent;

    private LoginEvent(int statusEvent) {
        setStatusEvent(statusEvent);
    }

    protected void setStatusEvent(int statusEvent) {
        this.statusEvent = statusEvent;
    }

    public int getStatusEvent() {
        return statusEvent;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getNamaOutlet() {
        return namaOutlet;
    }

    public void setNamaOutlet(String namaOutlet) {
        this.namaOutlet = namaOutlet;
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

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
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

    public String getPatokan() {
        return patokan;
    }

    public void setPatokan(String patokan) {
        this.patokan = patokan;
    }
}
