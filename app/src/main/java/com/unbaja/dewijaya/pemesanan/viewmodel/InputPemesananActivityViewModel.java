package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.database.entity.Faktur;
import com.unbaja.dewijaya.pemesanan.database.entity.PemesananProduk;

import java.util.List;

/**
 * Created by sigit on 29/08/2018.
 */

public class InputPemesananActivityViewModel extends AndroidViewModel {

    private PersediaanRepository mRepository;

    public InputPemesananActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }

    public void simpanPemesanan(Faktur faktur, List<PemesananProduk> list, int total) {
        mRepository.simpanPemesanan(faktur, list, total);
    }
}
