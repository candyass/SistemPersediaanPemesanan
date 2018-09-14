package com.unbaja.dewijaya.pemesanan.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.unbaja.dewijaya.pemesanan.PersediaanRepository;
import com.unbaja.dewijaya.pemesanan.model.QueryPemesanan;
import com.unbaja.dewijaya.pemesanan.model.QueryProduk;

import java.util.List;

/**
 * Created by sigit on 03/09/2018.
 */

public class DetailPemesananActivityViewModel extends AndroidViewModel {

    private PersediaanRepository mRepository;

    public DetailPemesananActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = PersediaanRepository.getInstance(application);
    }


    public LiveData<List<QueryProduk>> getAllProduk(long fakturId) {
        return mRepository.getListPemesananProduk(fakturId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mRepository = null;
    }
}
