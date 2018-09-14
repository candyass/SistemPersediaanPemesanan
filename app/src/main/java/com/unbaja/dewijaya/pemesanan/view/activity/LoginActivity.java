package com.unbaja.dewijaya.pemesanan.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.unbaja.dewijaya.pemesanan.R;
import com.unbaja.dewijaya.pemesanan.database.entity.Outlet;
import com.unbaja.dewijaya.pemesanan.model.LoginEvent;
import com.unbaja.dewijaya.pemesanan.util.MyLogger;
import com.unbaja.dewijaya.pemesanan.view.dialog.PesanDialog;
import com.unbaja.dewijaya.pemesanan.view.fragment.DaftarLokasiFragment;
import com.unbaja.dewijaya.pemesanan.view.fragment.DaftarOutletFragment;
import com.unbaja.dewijaya.pemesanan.view.fragment.LoginFragment;
import com.unbaja.dewijaya.pemesanan.viewmodel.LoginActivityViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class LoginActivity extends AppCompatActivity {


    private LoginActivityViewModel mViewModel;
    private Picasso mPicaso;
    private Outlet mOutlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mOutlet = new Outlet();
        mPicaso = Picasso.with(getBaseContext());
        mViewModel = ViewModelProviders.of(this).get(LoginActivityViewModel.class);


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_login_container);
        if(fragment == null) {
            fragment = LoginFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.activity_login_container, fragment).commit();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        Fragment fragment = null;
        if (event != null) {
            switch (event.getStatusEvent()) {
                case LoginEvent.EVENT_DAFTAR_OUTLET:
                    fragment = DaftarOutletFragment.newInstance();
                    changeFragment(fragment);
                    break;
                case LoginEvent.EVENT_DAFTAR_LOKASI:
                    mOutlet.setNamaPemilik(event.getNamaPemilik());
                    mOutlet.setNamaOutlet(event.getNamaOutlet());
                    mOutlet.setEmail(event.getEmail());
                    mOutlet.setKatasandi(event.getKatasandi());
                    mOutlet.setNoTelepon(event.getNoTelepon());
                    fragment = DaftarLokasiFragment.newInstance();
                    changeFragment(fragment);
                    break;
                case LoginEvent.EVENT_LOGIN:
                    long idOutlet = 0;
                    if(event.isLogin()) {
                        String email = event.getEmail();
                        String katasandi = event.getKatasandi();
                        if(email.equalsIgnoreCase("admin") && katasandi.equalsIgnoreCase("admin")) {
                            Intent intent = PengirimanActivity.newIntent(getBaseContext());
                            startActivity(intent);
                            finish();
                            return;
                        }
                        idOutlet = mViewModel.getLoginOutletId(email,katasandi);
                        MyLogger.logPesan("idOutlet : " + idOutlet);
                    }else {
                        mOutlet.setNamaTempat(event.getNamaTempat());
                        mOutlet.setPatokan(event.getPatokan());
                        mOutlet.setLatitude(event.getLatitude());
                        mOutlet.setLongitude(event.getLongitude());
                        idOutlet = mViewModel.insertOutlet(mOutlet);
                        MyLogger.logPesan("idOutlet : " + idOutlet);
                        MyLogger.logPesan(mOutlet.toString());
                    }
                    if(idOutlet > 0) {
                        Intent intent = MainActivity.newIntent(getBaseContext(),idOutlet);
                        startActivity(intent);
                        finish();
                    }else {
                        DialogFragment dialog = PesanDialog.newInstance("Login Gagal","Email atau Katasandi Salah");
                        dialog.show(getSupportFragmentManager(), PesanDialog.DIALOG_TAG);
                    }
                    break;
            }
        }
    }


    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_login_container, fragment)
                .addToBackStack(null).commit();
    }

    public void setImageBitmap(Uri uri) {
        Runnable runnable = () -> {
            try {
                mViewModel.setImageBitmap(mPicaso.load(uri).get());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }

    public Bitmap getImageBitmap() {
        return mViewModel.getImageBitmap();
    }

    public Picasso getmPicaso() {
        return mPicaso;
    }


}
